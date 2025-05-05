package com.ruoyi.system.domain;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Getter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

import java.time.LocalDateTime;

/**
 * 【请填写功能名称】对象 psd_task
 *
 * @author ruoyi
 * @date 2025-03-12
 */
public class PsdTask extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 任务名称 */
    @Excel(name = "任务名称")
    private String taskName;

    /** 作图账号名称 */
    @Excel(name = "作图账号名称")
    private String accountName;

    /** 模板名称 */
    @Excel(name = "模板名称")
    private String templateName;

    /** 图片生产数量，[1,2,3] 表示图一1张，图二两张 */
    @Excel(name = "图片生产数量，[1,2,3] 表示图一1张，图二两张")
    private String imageCount;

    private String config;

    @TableField(exist = false)
    private String jsonInfo;

    private String status;
    private String uuid;

    @Getter
    private LocalDateTime createDate;

    private String realPath;

    private String createBy;

    private String gzhmc;

    private String gzhStatus;

    public PsdTask() {
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }

    public void setTaskName(String taskName)
    {
        this.taskName = taskName;
    }

    public String getTaskName()
    {
        return taskName;
    }

    public void setAccountName(String accountName)
    {
        this.accountName = accountName;
    }

    public String getAccountName()
    {
        return accountName;
    }

    public void setTemplateName(String templateName)
    {
        this.templateName = templateName;
    }

    public String getTemplateName()
    {
        return templateName;
    }

    public void setImageCount(String imageCount)
    {
        this.imageCount = imageCount;
    }

    public String getImageCount()
    {
        return imageCount;
    }

    public String getConfig() {
        return config;
    }

    public void setConfig(String config) {
        this.config = config;
    }

    public LocalDateTime getcreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public String getJsonInfo() {
        return jsonInfo;
    }

    public void setJsonInfo(String jsonInfo) {
        this.jsonInfo = jsonInfo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getRealPath() {
        return realPath;
    }

    public void setRealPath(String realPath) {
        this.realPath = realPath;
    }

    public String getGzhmc() {
        return gzhmc;
    }

    public void setGzhmc(String gzhmc) {
        this.gzhmc = gzhmc;
    }

    public void setGzhStatus(String gzhStatus) {
        this.gzhStatus = gzhStatus;
    }

    public String getGzhStatus() {
        return gzhStatus;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("taskName", getTaskName())
            .append("accountName", getAccountName())
            .append("templateName", getTemplateName())
            .append("imageCount", getImageCount())
            .append("createDate", getcreateDate())
            .append("jsonInfo", getJsonInfo())
            .append("status", getStatus())
            .toString();
    }

    @Override
    public String getCreateBy() {
        return createBy;
    }

    @Override
    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }
}
