package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.SysPdfFile;

/**
 * PDF文件 数据层
 * 
 * @author ruoyi
 */
public interface SysPdfFileMapper
{
    /**
     * 查询PDF文件信息
     * 
     * @param pdfId PDF文件ID
     * @return PDF文件信息
     */
    public SysPdfFile selectPdfFileById(Long pdfId);

    /**
     * 查询PDF文件列表
     * 
     * @param pdfFile PDF文件信息
     * @return PDF文件集合
     */
    public List<SysPdfFile> selectPdfFileList(SysPdfFile pdfFile);

    /**
     * 新增PDF文件
     * 
     * @param pdfFile PDF文件信息
     * @return 结果
     */
    public int insertPdfFile(SysPdfFile pdfFile);

    /**
     * 修改PDF文件
     * 
     * @param pdfFile PDF文件信息
     * @return 结果
     */
    public int updatePdfFile(SysPdfFile pdfFile);

    /**
     * 删除PDF文件
     * 
     * @param pdfId PDF文件ID
     * @return 结果
     */
    public int deletePdfFileById(Long pdfId);

    /**
     * 批量删除PDF文件
     * 
     * @param pdfIds 需要删除的PDF文件ID
     * @return 结果
     */
    public int deletePdfFileByIds(Long[] pdfIds);
}