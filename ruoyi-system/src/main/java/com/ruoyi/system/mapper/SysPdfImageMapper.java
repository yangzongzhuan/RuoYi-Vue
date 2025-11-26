package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.SysPdfImage;

/**
 * PDF图片 数据层
 * 
 * @author ruoyi
 */
public interface SysPdfImageMapper
{
    /**
     * 查询PDF图片信息
     * 
     * @param imageId 图片ID
     * @return PDF图片信息
     */
    public SysPdfImage selectPdfImageById(Long imageId);

    /**
     * 根据PDF文件ID查询图片列表
     * 
     * @param pdfId PDF文件ID
     * @return 图片集合
     */
    public List<SysPdfImage> selectPdfImagesByPdfId(Long pdfId);

    /**
     * 新增PDF图片
     * 
     * @param pdfImage PDF图片信息
     * @return 结果
     */
    public int insertPdfImage(SysPdfImage pdfImage);

    /**
     * 批量新增PDF图片
     * 
     * @param pdfImages PDF图片信息列表
     * @return 结果
     */
    public int batchInsertPdfImages(List<SysPdfImage> pdfImages);

    /**
     * 修改PDF图片
     * 
     * @param pdfImage PDF图片信息
     * @return 结果
     */
    public int updatePdfImage(SysPdfImage pdfImage);

    /**
     * 删除PDF图片
     * 
     * @param imageId 图片ID
     * @return 结果
     */
    public int deletePdfImageById(Long imageId);

    /**
     * 根据PDF文件ID删除图片
     * 
     * @param pdfId PDF文件ID
     * @return 结果
     */
    public int deletePdfImagesByPdfId(Long pdfId);
}