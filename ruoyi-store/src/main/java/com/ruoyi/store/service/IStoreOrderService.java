package com.ruoyi.store.service;

import java.util.List;
import com.ruoyi.store.domain.StoreOrder;

/**
 * 订单Service接口
 *
 * @author duqy
 * @date 2026-05-26
 */
public interface IStoreOrderService
{
    /**
     * 查询订单
     *
     * @param orderId 订单ID
     * @return 订单
     */
    public StoreOrder selectStoreOrderByOrderId(Long orderId);

    /**
     * 查询订单列表
     *
     * @param storeOrder 订单
     * @return 订单集合
     */
    public List<StoreOrder> selectStoreOrderList(StoreOrder storeOrder);

    /**
     * 新增订单
     *
     * @param storeOrder 订单
     * @return 结果
     */
    public int insertStoreOrder(StoreOrder storeOrder);

    /**
     * 修改订单
     *
     * @param storeOrder 订单
     * @return 结果
     */
    public int updateStoreOrder(StoreOrder storeOrder);

    /**
     * 批量删除订单
     *
     * @param orderIds 需要删除的订单ID
     * @return 结果
     */
    public int deleteStoreOrderByOrderIds(Long[] orderIds);

    /**
     * 删除订单信息
     *
     * @param orderId 订单ID
     * @return 结果
     */
    public int deleteStoreOrderByOrderId(Long orderId);
}
