package com.ruoyi.store.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.store.mapper.StoreCategoryMapper;
import com.ruoyi.store.domain.StoreCategory;
import com.ruoyi.store.service.IStoreCategoryService;

/**
 * 商品分类Service业务层处理
 * 
 * @author duqy
 * @date 2026-05-26
 */
@Service
public class StoreCategoryServiceImpl implements IStoreCategoryService 
{
    @Autowired
    private StoreCategoryMapper storeCategoryMapper;

    /**
     * 查询商品分类
     * 
     * @param categoryId 商品分类主键
     * @return 商品分类
     */
    @Override
    public StoreCategory selectStoreCategoryByCategoryId(Long categoryId)
    {
        return storeCategoryMapper.selectStoreCategoryByCategoryId(categoryId);
    }

    /**
     * 查询商品分类列表
     * 
     * @param storeCategory 商品分类
     * @return 商品分类
     */
    @Override
    public List<StoreCategory> selectStoreCategoryList(StoreCategory storeCategory)
    {
        return storeCategoryMapper.selectStoreCategoryList(storeCategory);
    }

    /**
     * 新增商品分类
     * 
     * @param storeCategory 商品分类
     * @return 结果
     */
    @Override
    public int insertStoreCategory(StoreCategory storeCategory)
    {
        storeCategory.setCreateTime(DateUtils.getNowDate());
        return storeCategoryMapper.insertStoreCategory(storeCategory);
    }

    /**
     * 修改商品分类
     * 
     * @param storeCategory 商品分类
     * @return 结果
     */
    @Override
    public int updateStoreCategory(StoreCategory storeCategory)
    {
        storeCategory.setUpdateTime(DateUtils.getNowDate());
        return storeCategoryMapper.updateStoreCategory(storeCategory);
    }

    /**
     * 批量删除商品分类
     * 
     * @param categoryIds 需要删除的商品分类主键
     * @return 结果
     */
    @Override
    public int deleteStoreCategoryByCategoryIds(Long[] categoryIds)
    {
        return storeCategoryMapper.deleteStoreCategoryByCategoryIds(categoryIds);
    }

    /**
     * 删除商品分类信息
     * 
     * @param categoryId 商品分类主键
     * @return 结果
     */
    @Override
    public int deleteStoreCategoryByCategoryId(Long categoryId)
    {
        return storeCategoryMapper.deleteStoreCategoryByCategoryId(categoryId);
    }
}
