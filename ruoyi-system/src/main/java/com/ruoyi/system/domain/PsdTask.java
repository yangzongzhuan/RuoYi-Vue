package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

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
    private String username;

    /** 模板名称 */
    @Excel(name = "模板名称")
    private String templateName;

    /** 图片生产数量，[1,2,3] 表示图一1张，图二两张 */
    @Excel(name = "图片生产数量，[1,2,3] 表示图一1张，图二两张")
    private String imageCount;

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

    public void setUsername(String username) 
    {
        this.username = username;
    }

    public String getUsername() 
    {
        return username;
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

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("taskName", getTaskName())
            .append("username", getUsername())
            .append("templateName", getTemplateName())
            .append("imageCount", getImageCount())
            .toString();
    }
}
