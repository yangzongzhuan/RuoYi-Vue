package com.ruoyi.store.mapper;

import java.util.List;
import java.util.Map;
import com.ruoyi.store.domain.StoreOrder;

/**
 * 订单Mapper接口
 *
 * @author duqy
 * @date 2026-05-26
 */
public interface StoreOrderMapper
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
     * 删除订单
     *
     * @param orderId 订单ID
     * @return 结果
     */
    public int deleteStoreOrderByOrderId(Long orderId);

    /**
     * 批量删除订单
     *
     * @param orderIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteStoreOrderByOrderIds(Long[] orderIds);

    /**
     * 今日订单统计
     */
    public Map<String, Object> selectTodayOrderStats();

    /**
     * 本月订单统计
     */
    public Map<String, Object> selectMonthOrderStats();

    /**
     * 近7天订单趋势
     */
    public List<Map<String, Object>> selectLast7DaysOrderStats();

    /**
     * 支付方式统计
     */
    public List<Map<String, Object>> selectPayTypeStats();

    /**
     * 商品销售排行TOP10
     */
    public List<Map<String, Object>> selectProductSalesRank();
}
