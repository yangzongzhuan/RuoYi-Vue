package com.ruoyi.store.service;

import java.util.List;
import com.ruoyi.store.domain.StoreSupplier;

/**
 * 供应商Service接口
 *
 * @author duqy
 * @date 2026-05-26
 */
public interface IStoreSupplierService
{
    /**
     * 查询供应商
     *
     * @param supplierId 供应商ID
     * @return 供应商
     */
    public StoreSupplier selectStoreSupplierBySupplierId(Long supplierId);

    /**
     * 查询供应商列表
     *
     * @param storeSupplier 供应商
     * @return 供应商集合
     */
    public List<StoreSupplier> selectStoreSupplierList(StoreSupplier storeSupplier);

    /**
     * 新增供应商
     *
     * @param storeSupplier 供应商
     * @return 结果
     */
    public int insertStoreSupplier(StoreSupplier storeSupplier);

    /**
     * 修改供应商
     *
     * @param storeSupplier 供应商
     * @return 结果
     */
    public int updateStoreSupplier(StoreSupplier storeSupplier);

    /**
     * 批量删除供应商
     *
     * @param supplierIds 需要删除的供应商ID
     * @return 结果
     */
    public int deleteStoreSupplierBySupplierIds(Long[] supplierIds);

    /**
     * 删除供应商信息
     *
     * @param supplierId 供应商ID
     * @return 结果
     */
    public int deleteStoreSupplierBySupplierId(Long supplierId);
}
