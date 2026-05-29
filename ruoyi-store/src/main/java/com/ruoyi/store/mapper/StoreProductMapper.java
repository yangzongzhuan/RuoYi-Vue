package com.ruoyi.store.mapper;

import java.util.List;
import java.util.Map;
import com.ruoyi.store.domain.StoreProduct;

/**
 * 商品Mapper接口
 *
 * @author duqy
 * @date 2026-05-26
 */
public interface StoreProductMapper
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
     * 删除商品
     *
     * @param productId 商品主键
     * @return 结果
     */
    public int deleteStoreProductByProductId(Long productId);

    /**
     * 批量删除商品
     *
     * @param productIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteStoreProductByProductIds(Long[] productIds);

    /**
     * 商品总数
     */
    public Long selectProductCount();

    /**
     * 低库存商品数
     */
    public Long selectLowStockCount();

    /**
     * 库存总值
     */
    public java.math.BigDecimal selectTotalStockValue();

    /**
     * 低库存商品列表
     */
    public List<Map<String, Object>> selectLowStockList();

    /**
     * 根据条码查询商品
     */
    public StoreProduct selectStoreProductByBarcode(String barcode);
}
