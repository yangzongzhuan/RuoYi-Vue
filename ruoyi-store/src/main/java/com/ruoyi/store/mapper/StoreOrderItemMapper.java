package com.ruoyi.store.mapper;

import java.util.List;
import com.ruoyi.store.domain.StoreOrderItem;

/**
 * 订单明细Mapper接口
 *
 * @author duqy
 * @date 2026-05-26
 */
public interface StoreOrderItemMapper
{
    /**
     * 查询订单明细列表
     *
     * @param orderId 订单ID
     * @return 订单明细集合
     */
    public List<StoreOrderItem> selectStoreOrderItemByOrderId(Long orderId);

    /**
     * 新增订单明细
     *
     * @param storeOrderItem 订单明细
     * @return 结果
     */
    public int insertStoreOrderItem(StoreOrderItem storeOrderItem);

    /**
     * 批量新增订单明细
     *
     * @param storeOrderItemList 订单明细列表
     * @return 结果
     */
    public int batchStoreOrderItem(List<StoreOrderItem> storeOrderItemList);

    /**
     * 删除订单明细
     *
     * @param orderId 订单ID
     * @return 结果
     */
    public int deleteStoreOrderItemByOrderId(Long orderId);
}
