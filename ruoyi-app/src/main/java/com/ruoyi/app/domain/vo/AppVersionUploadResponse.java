package com.ruoyi.app.domain.vo;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * APP 端 APK/IPA/HAP 上传响应
 *
 * @author ruoyi
 */
public class AppVersionUploadResponse implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 访问 URL */
    private String url;

    /** 相对路径(用于内部存储) */
    private String fileName;

    /** 原始文件名 */
    private String originalName;

    /** 包大小(MB,BigDecimal 2 位小数) */
    private BigDecimal size;

    /** MD5(32 位小写) */
    private String md5;

    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }

    public String getFileName() { return fileName; }
    public void setFileName(String fileName) { this.fileName = fileName; }

    public String getOriginalName() { return originalName; }
    public void setOriginalName(String originalName) { this.originalName = originalName; }

    public BigDecimal getSize() { return size; }
    public void setSize(BigDecimal size) { this.size = size; }

    public String getMd5() { return md5; }
    public void setMd5(String md5) { this.md5 = md5; }
}
