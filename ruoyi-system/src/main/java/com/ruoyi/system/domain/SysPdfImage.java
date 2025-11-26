package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import java.util.Date;

/**
 * PDF图片表 sys_pdf_image
 * 
 * @author ruoyi
 */
public class SysPdfImage
{
    private static final long serialVersionUID = 1L;

    /** 图片ID */
    private Long imageId;

    /** 关联的PDF文件ID */
    private Long pdfId;

    /** 页码 */
    private Integer pageNum;

    /** 图片存储路径 */
    private String imagePath;

    /** 图片大小(字节) */
    private Long imageSize;

    /** 创建时间 */
    private Date createTime;

    public Long getImageId()
    {
        return imageId;
    }

    public void setImageId(Long imageId)
    {
        this.imageId = imageId;
    }

    public Long getPdfId()
    {
        return pdfId;
    }

    public void setPdfId(Long pdfId)
    {
        this.pdfId = pdfId;
    }

    public Integer getPageNum()
    {
        return pageNum;
    }

    public void setPageNum(Integer pageNum)
    {
        this.pageNum = pageNum;
    }

    public String getImagePath()
    {
        return imagePath;
    }

    public void setImagePath(String imagePath)
    {
        this.imagePath = imagePath;
    }

    public Long getImageSize()
    {
        return imageSize;
    }

    public void setImageSize(Long imageSize)
    {
        this.imageSize = imageSize;
    }

    public Date getCreateTime()
    {
        return createTime;
    }

    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("imageId", getImageId())
            .append("pdfId", getPdfId())
            .append("pageNum", getPageNum())
            .append("imagePath", getImagePath())
            .append("imageSize", getImageSize())
            .append("createTime", getCreateTime())
            .toString();
    }
}