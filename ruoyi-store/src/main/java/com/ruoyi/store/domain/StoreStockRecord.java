package com.ruoyi.store.domain;

import java.math.BigDecimal;
import jakarta.validation.constraints.NotNull;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 库存流水表 store_stock_record
 *
 * @author duqy
 * @date 2026-05-26
 */
public class StoreStockRecord extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 记录ID */
    @Excel(name = "记录ID", cellType = Excel.ColumnType.NUMERIC)
    private Long recordId;

    /** 商品ID */
    @Excel(name = "商品ID", cellType = Excel.ColumnType.NUMERIC)
    private Long productId;

    /** 商品名称 */
    @Excel(name = "商品名称")
    private String productName;

    /** 类型（1入库 2出库 3盘点） */
    @Excel(name = "类型", readConverterExp = "1=入库,2=出库,3=盘点")
    private String recordType;

    /** 数量 */
    @Excel(name = "数量", cellType = Excel.ColumnType.NUMERIC)
    private Integer quantity;

    /** 变动前库存 */
    @Excel(name = "变动前库存", cellType = Excel.ColumnType.NUMERIC)
    private Integer beforeStock;

    /** 变动后库存 */
    @Excel(name = "变动后库存", cellType = Excel.ColumnType.NUMERIC)
    private Integer afterStock;

    /** 供应商ID */
    @Excel(name = "供应商ID", cellType = Excel.ColumnType.NUMERIC)
    private Long supplierId;

    /** 供应商名称 */
    @Excel(name = "供应商名称")
    private String supplierName;

    /** 单据编号 */
    @Excel(name = "单据编号")
    private String recordNo;

    /** 操作类型（1采购入库 2销售出库 3盘点盈亏 4退货入库 5其他） */
    @Excel(name = "操作类型", readConverterExp = "1=采购入库,2=销售出库,3=盘点盈亏,4=退货入库,5=其他")
    private String operateType;

    public Long getRecordId()
    {
        return recordId;
    }

    public void setRecordId(Long recordId)
    {
        this.recordId = recordId;
    }

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

    @NotNull(message = "类型不能为空")
    public String getRecordType()
    {
        return recordType;
    }

    public void setRecordType(String recordType)
    {
        this.recordType = recordType;
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

    public Integer getBeforeStock()
    {
        return beforeStock;
    }

    public void setBeforeStock(Integer beforeStock)
    {
        this.beforeStock = beforeStock;
    }

    public Integer getAfterStock()
    {
        return afterStock;
    }

    public void setAfterStock(Integer afterStock)
    {
        this.afterStock = afterStock;
    }

    public Long getSupplierId()
    {
        return supplierId;
    }

    public void setSupplierId(Long supplierId)
    {
        this.supplierId = supplierId;
    }

    public String getSupplierName()
    {
        return supplierName;
    }

    public void setSupplierName(String supplierName)
    {
        this.supplierName = supplierName;
    }

    public String getRecordNo()
    {
        return recordNo;
    }

    public void setRecordNo(String recordNo)
    {
        this.recordNo = recordNo;
    }

    public String getOperateType()
    {
        return operateType;
    }

    public void setOperateType(String operateType)
    {
        this.operateType = operateType;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("recordId", getRecordId())
            .append("productId", getProductId())
            .append("productName", getProductName())
            .append("recordType", getRecordType())
            .append("quantity", getQuantity())
            .append("beforeStock", getBeforeStock())
            .append("afterStock", getAfterStock())
            .append("supplierId", getSupplierId())
            .append("supplierName", getSupplierName())
            .append("recordNo", getRecordNo())
            .append("operateType", getOperateType())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("remark", getRemark())
            .toString();
    }
}
