package com.ruoyi.store.domain;

import java.math.BigDecimal;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 会员等级表 store_member_level
 *
 * @author duqy
 * @date 2026-05-26
 */
public class StoreMemberLevel extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 等级ID */
    @Excel(name = "等级ID", cellType = Excel.ColumnType.NUMERIC)
    private Long levelId;

    /** 等级名称 */
    @Excel(name = "等级名称")
    private String levelName;

    /** 最小积分 */
    @Excel(name = "最小积分", cellType = Excel.ColumnType.NUMERIC)
    private Long minPoints;

    /** 折扣率 */
    @Excel(name = "折扣率", cellType = Excel.ColumnType.NUMERIC)
    private BigDecimal discount;

    /** 状态（0正常 1停用） */
    @Excel(name = "状态", readConverterExp = "0=正常,1=停用")
    private String status;

    public Long getLevelId()
    {
        return levelId;
    }

    public void setLevelId(Long levelId)
    {
        this.levelId = levelId;
    }

    @NotBlank(message = "等级名称不能为空")
    public String getLevelName()
    {
        return levelName;
    }

    public void setLevelName(String levelName)
    {
        this.levelName = levelName;
    }

    @NotNull(message = "最小积分不能为空")
    public Long getMinPoints()
    {
        return minPoints;
    }

    public void setMinPoints(Long minPoints)
    {
        this.minPoints = minPoints;
    }

    @NotNull(message = "折扣率不能为空")
    public BigDecimal getDiscount()
    {
        return discount;
    }

    public void setDiscount(BigDecimal discount)
    {
        this.discount = discount;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("levelId", getLevelId())
            .append("levelName", getLevelName())
            .append("minPoints", getMinPoints())
            .append("discount", getDiscount())
            .append("status", getStatus())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
