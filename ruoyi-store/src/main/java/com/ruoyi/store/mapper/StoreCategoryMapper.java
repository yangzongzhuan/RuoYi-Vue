package com.ruoyi.store.mapper;

import java.util.List;
import com.ruoyi.store.domain.StoreCategory;

/**
 * 商品分类Mapper接口
 * 
 * @author duqy
 * @date 2026-05-26
 */
public interface StoreCategoryMapper 
{
    /**
     * 查询商品分类
     * 
     * @param categoryId 商品分类主键
     * @return 商品分类
     */
    public StoreCategory selectStoreCategoryByCategoryId(Long categoryId);

    /**
     * 查询商品分类列表
     * 
     * @param storeCategory 商品分类
     * @return 商品分类集合
     */
    public List<StoreCategory> selectStoreCategoryList(StoreCategory storeCategory);

    /**
     * 新增商品分类
     * 
     * @param storeCategory 商品分类
     * @return 结果
     */
    public int insertStoreCategory(StoreCategory storeCategory);

    /**
     * 修改商品分类
     * 
     * @param storeCategory 商品分类
     * @return 结果
     */
    public int updateStoreCategory(StoreCategory storeCategory);

    /**
     * 删除商品分类
     * 
     * @param categoryId 商品分类主键
     * @return 结果
     */
    public int deleteStoreCategoryByCategoryId(Long categoryId);

    /**
     * 批量删除商品分类
     * 
     * @param categoryIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteStoreCategoryByCategoryIds(Long[] categoryIds);
}
