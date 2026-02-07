package com.ruoyi.system.domain;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
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
@Data
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

    private LocalDateTime createDate;

    private String realPath;

    private String createBy;

    private String gzhName;

    private String gzhStatus;

    private String dyStatus; //抖音发布状态

    private String dyPushTime; //抖音发布时间
}
