package com.ruoyi.store.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.store.domain.StoreMember;
import com.ruoyi.store.domain.StoreOrder;
import com.ruoyi.store.domain.StoreOrderItem;
import com.ruoyi.store.domain.StoreProduct;
import com.ruoyi.store.domain.StoreStockRecord;
import com.ruoyi.store.mapper.StoreMemberMapper;
import com.ruoyi.store.mapper.StoreOrderMapper;
import com.ruoyi.store.mapper.StoreOrderItemMapper;
import com.ruoyi.store.mapper.StoreProductMapper;
import com.ruoyi.store.mapper.StoreStockRecordMapper;
import com.ruoyi.store.service.IStoreMemberService;
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

    @Autowired
    private StoreProductMapper storeProductMapper;

    @Autowired
    private StoreStockRecordMapper storeStockRecordMapper;

    @Autowired
    private StoreMemberMapper storeMemberMapper;

    @Autowired
    private IStoreMemberService storeMemberService;

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
        // 自动生成订单号
        if (storeOrder.getOrderNo() == null || storeOrder.getOrderNo().isEmpty())
        {
            storeOrder.setOrderNo(generateOrderNo());
        }
        // 默认销售订单、已支付
        if (storeOrder.getOrderType() == null)
        {
            storeOrder.setOrderType("1");
        }
        if (storeOrder.getOrderStatus() == null)
        {
            storeOrder.setOrderStatus("1");
        }

        int rows = storeOrderMapper.insertStoreOrder(storeOrder);
        insertStoreOrderItem(storeOrder);

        // 扣减库存并生成库存流水
        deductStock(storeOrder);

        // 更新会员信息
        updateMemberInfo(storeOrder);

        return rows;
    }

    /**
     * 生成订单号 DD + yyyyMMdd + 4位序号
     */
    private String generateOrderNo()
    {
        String dateStr = new SimpleDateFormat("yyyyMMdd").format(new Date());
        // 使用当前时间戳后4位作为序号
        String seq = String.valueOf(System.currentTimeMillis()).substring(9);
        return "DD" + dateStr + seq;
    }

    /**
     * 扣减库存并生成销售出库流水
     */
    private void deductStock(StoreOrder storeOrder)
    {
        List<StoreOrderItem> items = storeOrder.getItems();
        if (items == null || items.isEmpty())
        {
            return;
        }
        String username = SecurityUtils.getUsername();
        for (StoreOrderItem item : items)
        {
            StoreProduct product = storeProductMapper.selectStoreProductByProductId(item.getProductId());
            if (product == null)
            {
                continue;
            }
            int beforeStock = product.getStockQuantity() != null ? product.getStockQuantity() : 0;
            int afterStock = beforeStock - item.getQuantity();

            // 更新商品库存
            product.setStockQuantity(afterStock);
            storeProductMapper.updateStoreProduct(product);

            // 生成库存流水
            StoreStockRecord record = new StoreStockRecord();
            record.setProductId(item.getProductId());
            record.setRecordType("2"); // 出库
            record.setQuantity(item.getQuantity());
            record.setBeforeStock(beforeStock);
            record.setAfterStock(afterStock);
            record.setRecordNo(storeOrder.getOrderNo());
            record.setOperateType("2"); // 销售出库
            record.setCreateBy(username);
            record.setCreateTime(new Date());
            record.setRemark("POS销售出库");
            storeStockRecordMapper.insertStoreStockRecord(record);
        }
    }

    /**
     * 更新会员信息：余额、积分、累计消费、订单数、最后消费时间
     */
    private void updateMemberInfo(StoreOrder storeOrder)
    {
        Long memberId = storeOrder.getMemberId();
        if (memberId == null)
        {
            return;
        }

        StoreMember member = storeMemberMapper.selectStoreMemberByMemberId(memberId);
        if (member == null)
        {
            return;
        }

        BigDecimal payAmount = storeOrder.getPayAmount() != null ? storeOrder.getPayAmount() : BigDecimal.ZERO;

        // 余额支付时扣减余额
        if ("4".equals(storeOrder.getPayType()) && payAmount.compareTo(BigDecimal.ZERO) > 0)
        {
            storeMemberService.deductBalance(memberId, payAmount);
        }

        // 累加积分（1元=1积分）
        long points = payAmount.longValue();
        if (points > 0)
        {
            storeMemberService.addPoints(memberId, points);
        }

        // 更新累计消费和订单数
        BigDecimal totalAmount = member.getTotalAmount() != null ? member.getTotalAmount() : BigDecimal.ZERO;
        long totalOrders = member.getTotalOrders() != null ? member.getTotalOrders() : 0;
        member.setTotalAmount(totalAmount.add(payAmount));
        member.setTotalOrders(totalOrders + 1);
        member.setLastTime(new Date());
        storeMemberMapper.updateStoreMember(member);
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
