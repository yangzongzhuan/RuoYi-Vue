package com.ruoyi.app.domain.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * APP 端检查更新响应对象
 *
 * @author ruoyi
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AppVersionCheckResponse implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 是否有更新 */
    private Boolean hasUpdate;

    /** 是否强制升级 */
    private Boolean forceUpdate;

    /** 最新版本号 */
    private String latestVersion;

    /** 最新版本Code */
    private Integer latestVersionCode;

    /** 更新类型(1强制 2可选 3静默) */
    private String updateType;

    /** 下载地址 */
    private String downloadUrl;

    /** 更新日志 */
    private String updateLog;

    /** 包大小(MB) */
    private BigDecimal packageSize;

    /** 安装包MD5 */
    private String md5;

    /** 发布时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date publishTime;

    public Boolean getHasUpdate() { return hasUpdate; }
    public void setHasUpdate(Boolean hasUpdate) { this.hasUpdate = hasUpdate; }

    public Boolean getForceUpdate() { return forceUpdate; }
    public void setForceUpdate(Boolean forceUpdate) { this.forceUpdate = forceUpdate; }

    public String getLatestVersion() { return latestVersion; }
    public void setLatestVersion(String latestVersion) { this.latestVersion = latestVersion; }

    public Integer getLatestVersionCode() { return latestVersionCode; }
    public void setLatestVersionCode(Integer latestVersionCode) { this.latestVersionCode = latestVersionCode; }

    public String getUpdateType() { return updateType; }
    public void setUpdateType(String updateType) { this.updateType = updateType; }

    public String getDownloadUrl() { return downloadUrl; }
    public void setDownloadUrl(String downloadUrl) { this.downloadUrl = downloadUrl; }

    public String getUpdateLog() { return updateLog; }
    public void setUpdateLog(String updateLog) { this.updateLog = updateLog; }

    public BigDecimal getPackageSize() { return packageSize; }
    public void setPackageSize(BigDecimal packageSize) { this.packageSize = packageSize; }

    public String getMd5() { return md5; }
    public void setMd5(String md5) { this.md5 = md5; }

    public Date getPublishTime() { return publishTime; }
    public void setPublishTime(Date publishTime) { this.publishTime = publishTime; }
}
