package com.ruoyi.store.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.store.domain.StoreSupplier;
import com.ruoyi.store.mapper.StoreSupplierMapper;
import com.ruoyi.store.service.IStoreSupplierService;

/**
 * 供应商Service业务层处理
 *
 * @author duqy
 * @date 2026-05-26
 */
@Service
public class StoreSupplierServiceImpl implements IStoreSupplierService
{
    @Autowired
    private StoreSupplierMapper storeSupplierMapper;

    @Override
    public StoreSupplier selectStoreSupplierBySupplierId(Long supplierId)
    {
        return storeSupplierMapper.selectStoreSupplierBySupplierId(supplierId);
    }

    @Override
    public List<StoreSupplier> selectStoreSupplierList(StoreSupplier storeSupplier)
    {
        return storeSupplierMapper.selectStoreSupplierList(storeSupplier);
    }

    @Override
    public int insertStoreSupplier(StoreSupplier storeSupplier)
    {
        storeSupplier.setDelFlag("0");
        return storeSupplierMapper.insertStoreSupplier(storeSupplier);
    }

    @Override
    public int updateStoreSupplier(StoreSupplier storeSupplier)
    {
        return storeSupplierMapper.updateStoreSupplier(storeSupplier);
    }

    @Override
    public int deleteStoreSupplierBySupplierIds(Long[] supplierIds)
    {
        return storeSupplierMapper.deleteStoreSupplierBySupplierIds(supplierIds);
    }

    @Override
    public int deleteStoreSupplierBySupplierId(Long supplierId)
    {
        return storeSupplierMapper.deleteStoreSupplierBySupplierId(supplierId);
    }
}
