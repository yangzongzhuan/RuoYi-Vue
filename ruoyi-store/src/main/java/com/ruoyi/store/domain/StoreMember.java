package com.ruoyi.store.domain;

import java.math.BigDecimal;
import java.util.Date;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 会员表 store_member
 *
 * @author duqy
 * @date 2026-05-26
 */
public class StoreMember extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 会员ID */
    @Excel(name = "会员ID", cellType = Excel.ColumnType.NUMERIC)
    private Long memberId;

    /** 会员姓名 */
    @Excel(name = "会员姓名")
    private String memberName;

    /** 手机号 */
    @Excel(name = "手机号")
    private String phone;

    /** 性别（0男 1女 2未知） */
    @Excel(name = "性别", readConverterExp = "0=男,1=女,2=未知")
    private String gender;

    /** 生日 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "生日", width = 30, dateFormat = "yyyy-MM-dd")
    private Date birthday;

    /** 会员等级ID */
    @Excel(name = "会员等级ID", cellType = Excel.ColumnType.NUMERIC)
    private Long levelId;

    /** 会员等级名称 */
    @Excel(name = "会员等级")
    private String levelName;

    /** 余额 */
    @Excel(name = "余额", cellType = Excel.ColumnType.NUMERIC)
    private BigDecimal balance;

    /** 积分 */
    @Excel(name = "积分", cellType = Excel.ColumnType.NUMERIC)
    private Long points;

    /** 累计消费金额 */
    @Excel(name = "累计消费金额", cellType = Excel.ColumnType.NUMERIC)
    private BigDecimal totalAmount;

    /** 累计订单数 */
    @Excel(name = "累计订单数", cellType = Excel.ColumnType.NUMERIC)
    private Long totalOrders;

    /** 最后消费时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "最后消费时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date lastTime;

    /** 状态（0正常 1停用） */
    @Excel(name = "状态", readConverterExp = "0=正常,1=停用")
    private String status;

    public Long getMemberId()
    {
        return memberId;
    }

    public void setMemberId(Long memberId)
    {
        this.memberId = memberId;
    }

    public String getMemberName()
    {
        return memberName;
    }

    public void setMemberName(String memberName)
    {
        this.memberName = memberName;
    }

    @NotBlank(message = "手机号不能为空")
    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "手机号格式不正确")
    public String getPhone()
    {
        return phone;
    }

    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    public String getGender()
    {
        return gender;
    }

    public void setGender(String gender)
    {
        this.gender = gender;
    }

    public Date getBirthday()
    {
        return birthday;
    }

    public void setBirthday(Date birthday)
    {
        this.birthday = birthday;
    }

    public Long getLevelId()
    {
        return levelId;
    }

    public void setLevelId(Long levelId)
    {
        this.levelId = levelId;
    }

    public String getLevelName()
    {
        return levelName;
    }

    public void setLevelName(String levelName)
    {
        this.levelName = levelName;
    }

    public BigDecimal getBalance()
    {
        return balance;
    }

    public void setBalance(BigDecimal balance)
    {
        this.balance = balance;
    }

    public Long getPoints()
    {
        return points;
    }

    public void setPoints(Long points)
    {
        this.points = points;
    }

    public BigDecimal getTotalAmount()
    {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount)
    {
        this.totalAmount = totalAmount;
    }

    public Long getTotalOrders()
    {
        return totalOrders;
    }

    public void setTotalOrders(Long totalOrders)
    {
        this.totalOrders = totalOrders;
    }

    public Date getLastTime()
    {
        return lastTime;
    }

    public void setLastTime(Date lastTime)
    {
        this.lastTime = lastTime;
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
            .append("memberId", getMemberId())
            .append("memberName", getMemberName())
            .append("phone", getPhone())
            .append("gender", getGender())
            .append("birthday", getBirthday())
            .append("levelId", getLevelId())
            .append("levelName", getLevelName())
            .append("balance", getBalance())
            .append("points", getPoints())
            .append("totalAmount", getTotalAmount())
            .append("totalOrders", getTotalOrders())
            .append("lastTime", getLastTime())
            .append("status", getStatus())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
