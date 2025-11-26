package com.ruoyi.system.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.utils.file.FileUtils;
import com.ruoyi.system.domain.SysPdfFile;
import com.ruoyi.system.domain.SysPdfImage;
import com.ruoyi.system.mapper.SysPdfFileMapper;
import com.ruoyi.system.mapper.SysPdfImageMapper;
import com.ruoyi.system.service.ISysPdfFileService;

/**
 * PDF文件 服务层实现
 * 
 * @author ruoyi
 */
@Service
public class SysPdfFileServiceImpl implements ISysPdfFileService
{
    private static final Logger log = LoggerFactory.getLogger(SysPdfFileServiceImpl.class);

    @Autowired
    private SysPdfFileMapper pdfFileMapper;

    @Autowired
    private SysPdfImageMapper pdfImageMapper;

    /**
     * 查询PDF文件
     * 
     * @param pdfId PDF文件ID
     * @return PDF文件
     */
    @Override
    public SysPdfFile selectPdfFileById(Long pdfId)
    {
        return pdfFileMapper.selectPdfFileById(pdfId);
    }

    /**
     * 查询PDF文件列表
     * 
     * @param pdfFile PDF文件
     * @return PDF文件
     */
    @Override
    public List<SysPdfFile> selectPdfFileList(SysPdfFile pdfFile)
    {
        return pdfFileMapper.selectPdfFileList(pdfFile);
    }

    /**
     * 新增PDF文件
     * 
     * @param pdfFile PDF文件
     * @return 结果
     */
    @Transactional
    @Override
    public int insertPdfFile(SysPdfFile pdfFile)
    {
        // 设置初始状态为待处理
        if (pdfFile.getStatus() == null) {
            pdfFile.setStatus("0");
        }
        
        // 确保创建时间和创建人信息
        if (pdfFile.getCreateTime() == null) {
            pdfFile.setCreateTime(new Date());
        }
        
        int rows = pdfFileMapper.insertPdfFile(pdfFile);
        
        // 异步处理PDF转图片
        try {
            new Thread(() -> {
                processPdfToImages(pdfFile.getPdfId());
            }).start();
        } catch (Exception e) {
            log.error("启动PDF处理线程失败", e);
        }
        
        return rows;
    }

    /**
     * 修改PDF文件
     * 
     * @param pdfFile PDF文件
     * @return 结果
     */
    @Transactional
    @Override
    public int updatePdfFile(SysPdfFile pdfFile)
    {
        return pdfFileMapper.updatePdfFile(pdfFile);
    }

    /**
     * 删除PDF文件对象
     * 
     * @param pdfId PDF文件ID
     * @return 结果
     */
    @Transactional
    @Override
    public int deletePdfFileById(Long pdfId)
    {
        // 先删除关联的图片记录
        pdfImageMapper.deletePdfImagesByPdfId(pdfId);
        // 删除PDF文件记录
        return pdfFileMapper.deletePdfFileById(pdfId);
    }

    /**
     * 批量删除PDF文件
     * 
     * @param pdfIds 需要删除的PDF文件ID
     * @return 结果
     */
    @Transactional
    @Override
    public int deletePdfFileByIds(Long[] pdfIds)
    {
        for (Long pdfId : pdfIds)
        {
            // 删除关联的图片记录
            pdfImageMapper.deletePdfImagesByPdfId(pdfId);
        }
        // 删除PDF文件记录
        return pdfFileMapper.deletePdfFileByIds(pdfIds);
    }

    /**
     * 处理PDF文件，转换为图片
     * 
     * @param pdfId PDF文件ID
     * @return 处理结果
     */
    @Transactional
    @Override
    public boolean processPdfToImages(Long pdfId)
    {
        SysPdfFile pdfFile = pdfFileMapper.selectPdfFileById(pdfId);
        if (pdfFile == null) {
            log.error("PDF文件不存在: {}", pdfId);
            return false;
        }

        // 更新状态为处理中
        SysPdfFile updateFile = new SysPdfFile();
        updateFile.setPdfId(pdfId);
        updateFile.setStatus("1");
        pdfFileMapper.updatePdfFile(updateFile);

        String pdfFilePath = RuoYiConfig.getProfile() + pdfFile.getPdfPath();
        File pdfFileObj = new File(pdfFilePath);

        if (!pdfFileObj.exists() || !pdfFileObj.isFile()) {
            log.error("PDF文件不存在或不是有效文件: {}", pdfFilePath);
            updateFile.setStatus("3"); // 处理失败
            pdfFileMapper.updatePdfFile(updateFile);
            return false;
        }

        PDDocument document = null;
        try {
            // 创建图片存储目录
            String imageDir = RuoYiConfig.getProfile() + "/pdf_images/" + pdfId + "/";
            File dir = new File(imageDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            // 加载PDF文档
            document = PDDocument.load(pdfFileObj);
            PDFRenderer renderer = new PDFRenderer(document);
            int pageCount = document.getNumberOfPages();

            // 存储所有图片信息
            List<SysPdfImage> imageList = new ArrayList<>();

            // 遍历每页并转换为图片
            for (int i = 0; i < pageCount; i++) {
                // 渲染页面为图片 (300 DPI)
                BufferedImage image = renderer.renderImageWithDPI(i, 300);
                
                // 生成图片文件名
                String imageFileName = "page_" + (i + 1) + ".png";
                String imagePath = imageDir + imageFileName;
                String relativePath = "/pdf_images/" + pdfId + "/" + imageFileName;
                
                // 保存图片
                ImageIO.write(image, "png", new File(imagePath));
                
                // 创建图片记录
                SysPdfImage pdfImage = new SysPdfImage();
                pdfImage.setPdfId(pdfId);
                pdfImage.setPageNum(i + 1);
                pdfImage.setImagePath(relativePath);
                pdfImage.setImageSize(new File(imagePath).length());
                pdfImage.setCreateTime(new Date());
                imageList.add(pdfImage);
            }

            // 批量插入图片记录
            if (!imageList.isEmpty()) {
                pdfImageMapper.batchInsertPdfImages(imageList);
            }

            // 更新PDF文件信息
            updateFile.setPageCount(pageCount);
            updateFile.setStatus("2"); // 处理完成
            pdfFileMapper.updatePdfFile(updateFile);

            log.info("PDF文件转换成功: {}, 共{}页", pdfId, pageCount);
            return true;
        } catch (Exception e) {
            log.error("PDF文件转换失败: {}", pdfId, e);
            updateFile.setStatus("3"); // 处理失败
            pdfFileMapper.updatePdfFile(updateFile);
            return false;
        } finally {
            if (document != null) {
                try {
                    document.close();
                } catch (IOException e) {
                    log.error("关闭PDF文档失败", e);
                }
            }
        }
    }
}