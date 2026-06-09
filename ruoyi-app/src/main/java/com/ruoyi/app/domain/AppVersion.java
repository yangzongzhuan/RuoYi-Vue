package com.ruoyi.app.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * APP版本管理 对象 sys_app_version
 *
 * @author ruoyi
 */
public class AppVersion extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    private Long id;

    /** 应用标识 */
    @Excel(name = "应用ID")
    private String appId;

    /** 应用名称 */
    @Excel(name = "应用名称")
    private String appName;

    /** 平台(ios/android/harmony) */
    @Excel(name = "平台")
    private String platform;

    /** 版本号(语义化) */
    @Excel(name = "版本号")
    private String version;

    /** 版本Code(整数比较) */
    @Excel(name = "版本Code")
    private Integer versionCode;

    /** 更新类型(1强制 2可选 3静默) */
    @Excel(name = "更新类型")
    private String updateType;

    /** 下载/安装包地址 */
    private String downloadUrl;

    /** 更新日志 */
    private String updateLog;

    /** 包大小(MB) */
    @Excel(name = "包大小(MB)")
    private BigDecimal packageSize;

    /** 安装包MD5 */
    private String md5;

    /** 最低支持版本 */
    private String minSupportVersion;

    /** 下载次数 */
    private Integer downloadCount;

    /** 状态(0正常 1停用) */
    @Excel(name = "状态", readConverterExp = "0=正常,1=停用")
    private String status;

    /** 发布时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "发布时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date publishTime;

    public void setId(Long id) { this.id = id; }
    public Long getId() { return id; }

    public void setAppId(String appId) { this.appId = appId; }
    public String getAppId() { return appId; }

    public void setAppName(String appName) { this.appName = appName; }
    public String getAppName() { return appName; }

    public void setPlatform(String platform) { this.platform = platform; }
    public String getPlatform() { return platform; }

    public void setVersion(String version) { this.version = version; }
    public String getVersion() { return version; }

    public void setVersionCode(Integer versionCode) { this.versionCode = versionCode; }
    public Integer getVersionCode() { return versionCode; }

    public void setUpdateType(String updateType) { this.updateType = updateType; }
    public String getUpdateType() { return updateType; }

    public void setDownloadUrl(String downloadUrl) { this.downloadUrl = downloadUrl; }
    public String getDownloadUrl() { return downloadUrl; }

    public void setUpdateLog(String updateLog) { this.updateLog = updateLog; }
    public String getUpdateLog() { return updateLog; }

    public void setPackageSize(BigDecimal packageSize) { this.packageSize = packageSize; }
    public BigDecimal getPackageSize() { return packageSize; }

    public void setMd5(String md5) { this.md5 = md5; }
    public String getMd5() { return md5; }

    public void setMinSupportVersion(String minSupportVersion) { this.minSupportVersion = minSupportVersion; }
    public String getMinSupportVersion() { return minSupportVersion; }

    public void setStatus(String status) { this.status = status; }
    public String getStatus() { return status; }

    public void setPublishTime(Date publishTime) { this.publishTime = publishTime; }
    public Date getPublishTime() { return publishTime; }

    public void setDownloadCount(Integer downloadCount) { this.downloadCount = downloadCount; }
    public Integer getDownloadCount() { return downloadCount; }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("appId", getAppId())
                .append("appName", getAppName())
                .append("platform", getPlatform())
                .append("version", getVersion())
                .append("versionCode", getVersionCode())
                .append("updateType", getUpdateType())
                .append("downloadUrl", getDownloadUrl())
                .append("updateLog", getUpdateLog())
                .append("packageSize", getPackageSize())
                .append("md5", getMd5())
                .append("minSupportVersion", getMinSupportVersion())
                .append("status", getStatus())
                .append("publishTime", getPublishTime())
                .append("downloadCount", getDownloadCount())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .append("remark", getRemark())
                .toString();
    }
}
