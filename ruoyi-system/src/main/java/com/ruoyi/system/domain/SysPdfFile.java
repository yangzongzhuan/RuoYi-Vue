package com.ruoyi.system.domain;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.domain.BaseEntity;
import com.ruoyi.common.xss.Xss;

/**
 * PDF文件表 sys_pdf_file
 * 
 * @author ruoyi
 */
public class SysPdfFile extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** PDF文件ID */
    private Long pdfId;

    /** PDF标题 */
    private String pdfTitle;

    /** PDF描述 */
    private String pdfDescription;

    /** PDF文件存储路径 */
    private String pdfPath;

    /** 原始文件名 */
    private String originalFilename;

    /** PDF页数 */
    private Integer pageCount;

    /** 处理状态（0待处理 1处理中 2处理完成 3处理失败） */
    private String status;
    
    /** PDF文件大小（字节） */
    private Long pdfSize;
    
    /** 开始时间 */
    private String beginTime;
    
    /** 结束时间 */
    private String endTime;

    public Long getPdfId()
    {
        return pdfId;
    }

    public void setPdfId(Long pdfId)
    {
        this.pdfId = pdfId;
    }

    @Xss(message = "PDF标题不能包含脚本字符")
    @NotBlank(message = "PDF标题不能为空")
    @Size(min = 0, max = 100, message = "PDF标题不能超过100个字符")
    public String getPdfTitle()
    {
        return pdfTitle;
    }
    
    public void setPdfTitle(String pdfTitle)
    {
        this.pdfTitle = pdfTitle;
    }

    @Xss(message = "PDF描述不能包含脚本字符")
    @Size(min = 0, max = 500, message = "PDF描述不能超过500个字符")
    public String getPdfDescription()
    {
        return pdfDescription;
    }

    public void setPdfDescription(String pdfDescription)
    {
        this.pdfDescription = pdfDescription;
    }

    public String getPdfPath()
    {
        return pdfPath;
    }

    public void setPdfPath(String pdfPath)
    {
        this.pdfPath = pdfPath;
    }

    public String getOriginalFilename()
    {
        return originalFilename;
    }

    public void setOriginalFilename(String originalFilename)
    {
        this.originalFilename = originalFilename;
    }

    public Integer getPageCount()
    {
        return pageCount;
    }

    public void setPageCount(Integer pageCount)
    {
        this.pageCount = pageCount;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }
    
    public Long getPdfSize()
    {   
        return pdfSize;
    }
    
    public void setPdfSize(Long pdfSize)
    {   
        this.pdfSize = pdfSize;
    }
    
    public String getBeginTime()
    {   
        return beginTime;
    }
    
    public void setBeginTime(String beginTime)
    {   
        this.beginTime = beginTime;
    }
    
    public String getEndTime()
    {   
        return endTime;
    }
    
    public void setEndTime(String endTime)
    {   
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("pdfId", getPdfId())
            .append("pdfTitle", getPdfTitle())
            .append("pdfDescription", getPdfDescription())
            .append("pdfPath", getPdfPath())
            .append("originalFilename", getOriginalFilename())
            .append("pageCount", getPageCount())
            .append("status", getStatus())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}