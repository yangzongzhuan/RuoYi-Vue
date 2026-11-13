package com.ruoyi.store.service;

import java.util.List;
import com.ruoyi.store.domain.StoreProduct;

/**
 * 商品Service接口
 *
 * @author duqy
 * @date 2026-05-26
 */
public interface IStoreProductService
{
    /**
     * 查询商品
     *
     * @param productId 商品主键
     * @return 商品
     */
    public StoreProduct selectStoreProductByProductId(Long productId);

    /**
     * 查询商品列表
     *
     * @param storeProduct 商品
     * @return 商品集合
     */
    public List<StoreProduct> selectStoreProductList(StoreProduct storeProduct);

    /**
     * 新增商品
     *
     * @param storeProduct 商品
     * @return 结果
     */
    public int insertStoreProduct(StoreProduct storeProduct);

    /**
     * 修改商品
     *
     * @param storeProduct 商品
     * @return 结果
     */
    public int updateStoreProduct(StoreProduct storeProduct);

    /**
     * 批量删除商品
     *
     * @param productIds 需要删除的商品主键集合
     * @return 结果
     */
    public int deleteStoreProductByProductIds(Long[] productIds);

    /**
     * 删除商品信息
     *
     * @param productId 商品主键
     * @return 结果
     */
    public int deleteStoreProductByProductId(Long productId);

    /**
     * 根据条码查询商品
     *
     * @param barcode 条码
     * @return 商品
     */
    public StoreProduct selectStoreProductByBarcode(String barcode);
}
