package com.ruoyi.store.domain;

import java.math.BigDecimal;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 商品表 store_product
 *
 * @author ruoyi
 */
public class StoreProduct extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 商品ID */
    @Excel(name = "商品ID", cellType = Excel.ColumnType.NUMERIC)
    private Long productId;

    /** 商品编码 */
    @Excel(name = "商品编码")
    private String productCode;

    /** 商品名称 */
    @Excel(name = "商品名称")
    private String productName;

    /** 分类ID */
    @Excel(name = "分类ID", cellType = Excel.ColumnType.NUMERIC)
    private Long categoryId;

    /** 分类名称 */
    private String categoryName;

    /** 规格 */
    @Excel(name = "规格")
    private String specification;

    /** 单位 */
    @Excel(name = "单位")
    private String unit;

    /** 成本价 */
    @Excel(name = "成本价")
    private BigDecimal costPrice;

    /** 售价 */
    @Excel(name = "售价")
    private BigDecimal salePrice;

    /** 库存数量 */
    @Excel(name = "库存数量", cellType = Excel.ColumnType.NUMERIC)
    private Integer stockQuantity;

    /** 安全库存 */
    @Excel(name = "安全库存", cellType = Excel.ColumnType.NUMERIC)
    private Integer safetyStock;

    /** 商品图片 */
    private String productImage;

    /** 条码 */
    @Excel(name = "条码")
    private String barcode;

    /** 状态（0上架 1下架） */
    @Excel(name = "状态", readConverterExp = "0=上架,1=下架")
    private String status;

    /** 删除标志（0代表存在 2代表删除） */
    private String delFlag;

    public Long getProductId()
    {
        return productId;
    }

    public void setProductId(Long productId)
    {
        this.productId = productId;
    }

    @NotBlank(message = "商品编码不能为空")
    @Size(min = 0, max = 50, message = "商品编码不能超过50个字符")
    public String getProductCode()
    {
        return productCode;
    }

    public void setProductCode(String productCode)
    {
        this.productCode = productCode;
    }

    @NotBlank(message = "商品名称不能为空")
    @Size(min = 0, max = 100, message = "商品名称不能超过100个字符")
    public String getProductName()
    {
        return productName;
    }

    public void setProductName(String productName)
    {
        this.productName = productName;
    }

    public Long getCategoryId()
    {
        return categoryId;
    }

    public void setCategoryId(Long categoryId)
    {
        this.categoryId = categoryId;
    }

    public String getCategoryName()
    {
        return categoryName;
    }

    public void setCategoryName(String categoryName)
    {
        this.categoryName = categoryName;
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

    public BigDecimal getCostPrice()
    {
        return costPrice;
    }

    public void setCostPrice(BigDecimal costPrice)
    {
        this.costPrice = costPrice;
    }

    @NotNull(message = "售价不能为空")
    public BigDecimal getSalePrice()
    {
        return salePrice;
    }

    public void setSalePrice(BigDecimal salePrice)
    {
        this.salePrice = salePrice;
    }

    public Integer getStockQuantity()
    {
        return stockQuantity;
    }

    public void setStockQuantity(Integer stockQuantity)
    {
        this.stockQuantity = stockQuantity;
    }

    public Integer getSafetyStock()
    {
        return safetyStock;
    }

    public void setSafetyStock(Integer safetyStock)
    {
        this.safetyStock = safetyStock;
    }

    public String getProductImage()
    {
        return productImage;
    }

    public void setProductImage(String productImage)
    {
        this.productImage = productImage;
    }

    public String getBarcode()
    {
        return barcode;
    }

    public void setBarcode(String barcode)
    {
        this.barcode = barcode;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getDelFlag()
    {
        return delFlag;
    }

    public void setDelFlag(String delFlag)
    {
        this.delFlag = delFlag;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("productId", getProductId())
            .append("productCode", getProductCode())
            .append("productName", getProductName())
            .append("categoryId", getCategoryId())
            .append("specification", getSpecification())
            .append("unit", getUnit())
            .append("costPrice", getCostPrice())
            .append("salePrice", getSalePrice())
            .append("stockQuantity", getStockQuantity())
            .append("safetyStock", getSafetyStock())
            .append("productImage", getProductImage())
            .append("barcode", getBarcode())
            .append("status", getStatus())
            .append("delFlag", getDelFlag())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
