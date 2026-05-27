package com.ruoyi.store.domain;

import java.math.BigDecimal;
import java.util.List;
import jakarta.validation.constraints.NotBlank;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 订单表 store_order
 *
 * @author duqy
 * @date 2026-05-26
 */
public class StoreOrder extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 订单ID */
    @Excel(name = "订单ID", cellType = Excel.ColumnType.NUMERIC)
    private Long orderId;

    /** 订单编号 */
    @Excel(name = "订单编号")
    private String orderNo;

    /** 会员ID */
    @Excel(name = "会员ID", cellType = Excel.ColumnType.NUMERIC)
    private Long memberId;

    /** 会员名称 */
    @Excel(name = "会员名称")
    private String memberName;

    /** 订单类型（1销售 2退货） */
    @Excel(name = "订单类型", readConverterExp = "1=销售,2=退货")
    private String orderType;

    /** 订单状态（0待支付 1已支付 2已取消 3已退款） */
    @Excel(name = "订单状态", readConverterExp = "0=待支付,1=已支付,2=已取消,3=已退款")
    private String orderStatus;

    /** 支付方式（1现金 2微信 3支付宝 4余额 5刷卡） */
    @Excel(name = "支付方式", readConverterExp = "1=现金,2=微信,3=支付宝,4=余额,5=刷卡")
    private String payType;

    /** 订单总金额 */
    @Excel(name = "订单总金额", cellType = Excel.ColumnType.NUMERIC)
    private BigDecimal totalAmount;

    /** 优惠金额 */
    @Excel(name = "优惠金额", cellType = Excel.ColumnType.NUMERIC)
    private BigDecimal discountAmount;

    /** 实付金额 */
    @Excel(name = "实付金额", cellType = Excel.ColumnType.NUMERIC)
    private BigDecimal payAmount;

    /** 使用积分 */
    @Excel(name = "使用积分", cellType = Excel.ColumnType.NUMERIC)
    private Long pointsUsed;

    /** 订单明细 */
    private List<StoreOrderItem> items;

    public Long getOrderId()
    {
        return orderId;
    }

    public void setOrderId(Long orderId)
    {
        this.orderId = orderId;
    }

    @NotBlank(message = "订单编号不能为空")
    public String getOrderNo()
    {
        return orderNo;
    }

    public void setOrderNo(String orderNo)
    {
        this.orderNo = orderNo;
    }

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

    public String getOrderType()
    {
        return orderType;
    }

    public void setOrderType(String orderType)
    {
        this.orderType = orderType;
    }

    public String getOrderStatus()
    {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus)
    {
        this.orderStatus = orderStatus;
    }

    public String getPayType()
    {
        return payType;
    }

    public void setPayType(String payType)
    {
        this.payType = payType;
    }

    public BigDecimal getTotalAmount()
    {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount)
    {
        this.totalAmount = totalAmount;
    }

    public BigDecimal getDiscountAmount()
    {
        return discountAmount;
    }

    public void setDiscountAmount(BigDecimal discountAmount)
    {
        this.discountAmount = discountAmount;
    }

    public BigDecimal getPayAmount()
    {
        return payAmount;
    }

    public void setPayAmount(BigDecimal payAmount)
    {
        this.payAmount = payAmount;
    }

    public Long getPointsUsed()
    {
        return pointsUsed;
    }

    public void setPointsUsed(Long pointsUsed)
    {
        this.pointsUsed = pointsUsed;
    }

    public List<StoreOrderItem> getItems()
    {
        return items;
    }

    public void setItems(List<StoreOrderItem> items)
    {
        this.items = items;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("orderId", getOrderId())
            .append("orderNo", getOrderNo())
            .append("memberId", getMemberId())
            .append("memberName", getMemberName())
            .append("orderType", getOrderType())
            .append("orderStatus", getOrderStatus())
            .append("payType", getPayType())
            .append("totalAmount", getTotalAmount())
            .append("discountAmount", getDiscountAmount())
            .append("payAmount", getPayAmount())
            .append("pointsUsed", getPointsUsed())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
