package com.ruoyi.store.domain;

import java.math.BigDecimal;
import java.util.Date;
import jakarta.validation.constraints.NotNull;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;

/**
 * 订单明细表 store_order_item
 *
 * @author duqy
 * @date 2026-05-26
 */
public class StoreOrderItem
{
    private static final long serialVersionUID = 1L;

    /** 明细ID */
    @Excel(name = "明细ID", cellType = Excel.ColumnType.NUMERIC)
    private Long itemId;

    /** 订单ID */
    @Excel(name = "订单ID", cellType = Excel.ColumnType.NUMERIC)
    private Long orderId;

    /** 商品ID */
    @Excel(name = "商品ID", cellType = Excel.ColumnType.NUMERIC)
    private Long productId;

    /** 商品名称 */
    @Excel(name = "商品名称")
    private String productName;

    /** 规格 */
    @Excel(name = "规格")
    private String specification;

    /** 单位 */
    @Excel(name = "单位")
    private String unit;

    /** 销售单价 */
    @Excel(name = "销售单价", cellType = Excel.ColumnType.NUMERIC)
    private BigDecimal salePrice;

    /** 数量 */
    @Excel(name = "数量", cellType = Excel.ColumnType.NUMERIC)
    private Integer quantity;

    /** 小计金额 */
    @Excel(name = "小计金额", cellType = Excel.ColumnType.NUMERIC)
    private BigDecimal subtotal;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    public Long getItemId()
    {
        return itemId;
    }

    public void setItemId(Long itemId)
    {
        this.itemId = itemId;
    }

    public Long getOrderId()
    {
        return orderId;
    }

    public void setOrderId(Long orderId)
    {
        this.orderId = orderId;
    }

    @NotNull(message = "商品ID不能为空")
    public Long getProductId()
    {
        return productId;
    }

    public void setProductId(Long productId)
    {
        this.productId = productId;
    }

    public String getProductName()
    {
        return productName;
    }

    public void setProductName(String productName)
    {
        this.productName = productName;
    }

    public String getSpecification()
    {
        return specification;
    }

    public void setSpecification(String specification)
    {
        this.specification = specification;
    }

    public String getUnit()
    {
        return unit;
    }

    public void setUnit(String unit)
    {
        this.unit = unit;
    }

    @NotNull(message = "销售单价不能为空")
    public BigDecimal getSalePrice()
    {
        return salePrice;
    }

    public void setSalePrice(BigDecimal salePrice)
    {
        this.salePrice = salePrice;
    }

    @NotNull(message = "数量不能为空")
    public Integer getQuantity()
    {
        return quantity;
    }

    public void setQuantity(Integer quantity)
    {
        this.quantity = quantity;
    }

    public BigDecimal getSubtotal()
    {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal)
    {
        this.subtotal = subtotal;
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
            .append("itemId", getItemId())
            .append("orderId", getOrderId())
            .append("productId", getProductId())
            .append("productName", getProductName())
            .append("specification", getSpecification())
            .append("unit", getUnit())
            .append("salePrice", getSalePrice())
            .append("quantity", getQuantity())
            .append("subtotal", getSubtotal())
            .append("createTime", getCreateTime())
            .toString();
    }
}
