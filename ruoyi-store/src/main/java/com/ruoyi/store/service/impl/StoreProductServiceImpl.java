package com.ruoyi.store.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.store.mapper.StoreProductMapper;
import com.ruoyi.store.domain.StoreProduct;
import com.ruoyi.store.service.IStoreProductService;

/**
 * 商品Service业务层处理
 *
 * @author duqy
 * @date 2026-05-26
 */
@Service
public class StoreProductServiceImpl implements IStoreProductService
{
    @Autowired
    private StoreProductMapper storeProductMapper;

    /**
     * 查询商品
     *
     * @param productId 商品主键
     * @return 商品
     */
    @Override
    public StoreProduct selectStoreProductByProductId(Long productId)
    {
        return storeProductMapper.selectStoreProductByProductId(productId);
    }

    /**
     * 查询商品列表
     *
     * @param storeProduct 商品
     * @return 商品
     */
    @Override
    public List<StoreProduct> selectStoreProductList(StoreProduct storeProduct)
    {
        return storeProductMapper.selectStoreProductList(storeProduct);
    }

    /**
     * 新增商品
     *
     * @param storeProduct 商品
     * @return 结果
     */
    @Override
    public int insertStoreProduct(StoreProduct storeProduct)
    {
        storeProduct.setCreateTime(DateUtils.getNowDate());
        return storeProductMapper.insertStoreProduct(storeProduct);
    }

    /**
     * 修改商品
     *
     * @param storeProduct 商品
     * @return 结果
     */
    @Override
    public int updateStoreProduct(StoreProduct storeProduct)
    {
        storeProduct.setUpdateTime(DateUtils.getNowDate());
        return storeProductMapper.updateStoreProduct(storeProduct);
    }

    /**
     * 批量删除商品
     *
     * @param productIds 需要删除的商品主键
     * @return 结果
     */
    @Override
    public int deleteStoreProductByProductIds(Long[] productIds)
    {
        return storeProductMapper.deleteStoreProductByProductIds(productIds);
    }

    /**
     * 删除商品信息
     *
     * @param productId 商品主键
     * @return 结果
     */
    @Override
    public int deleteStoreProductByProductId(Long productId)
    {
        return storeProductMapper.deleteStoreProductByProductId(productId);
    }

    /**
     * 根据条码查询商品
     */
    @Override
    public StoreProduct selectStoreProductByBarcode(String barcode)
    {
        return storeProductMapper.selectStoreProductByBarcode(barcode);
    }
}
