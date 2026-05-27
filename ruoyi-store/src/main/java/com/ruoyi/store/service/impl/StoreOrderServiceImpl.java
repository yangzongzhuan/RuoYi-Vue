package com.ruoyi.store.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ruoyi.store.domain.StoreOrder;
import com.ruoyi.store.domain.StoreOrderItem;
import com.ruoyi.store.mapper.StoreOrderMapper;
import com.ruoyi.store.mapper.StoreOrderItemMapper;
import com.ruoyi.store.service.IStoreOrderService;

/**
 * 订单Service业务层处理
 *
 * @author duqy
 * @date 2026-05-26
 */
@Service
public class StoreOrderServiceImpl implements IStoreOrderService
{
    @Autowired
    private StoreOrderMapper storeOrderMapper;

    @Autowired
    private StoreOrderItemMapper storeOrderItemMapper;

    @Override
    public StoreOrder selectStoreOrderByOrderId(Long orderId)
    {
        return storeOrderMapper.selectStoreOrderByOrderId(orderId);
    }

    @Override
    public List<StoreOrder> selectStoreOrderList(StoreOrder storeOrder)
    {
        return storeOrderMapper.selectStoreOrderList(storeOrder);
    }

    @Override
    @Transactional
    public int insertStoreOrder(StoreOrder storeOrder)
    {
        int rows = storeOrderMapper.insertStoreOrder(storeOrder);
        insertStoreOrderItem(storeOrder);
        return rows;
    }

    @Override
    @Transactional
    public int updateStoreOrder(StoreOrder storeOrder)
    {
        storeOrderItemMapper.deleteStoreOrderItemByOrderId(storeOrder.getOrderId());
        insertStoreOrderItem(storeOrder);
        return storeOrderMapper.updateStoreOrder(storeOrder);
    }

    @Override
    @Transactional
    public int deleteStoreOrderByOrderIds(Long[] orderIds)
    {
        for (Long orderId : orderIds)
        {
            storeOrderItemMapper.deleteStoreOrderItemByOrderId(orderId);
        }
        return storeOrderMapper.deleteStoreOrderByOrderIds(orderIds);
    }

    @Override
    @Transactional
    public int deleteStoreOrderByOrderId(Long orderId)
    {
        storeOrderItemMapper.deleteStoreOrderItemByOrderId(orderId);
        return storeOrderMapper.deleteStoreOrderByOrderId(orderId);
    }

    public void insertStoreOrderItem(StoreOrder storeOrder)
    {
        List<StoreOrderItem> items = storeOrder.getItems();
        if (items != null && !items.isEmpty())
        {
            for (StoreOrderItem item : items)
            {
                item.setOrderId(storeOrder.getOrderId());
                item.setCreateTime(new java.util.Date());
            }
            storeOrderItemMapper.batchStoreOrderItem(items);
        }
    }
}
