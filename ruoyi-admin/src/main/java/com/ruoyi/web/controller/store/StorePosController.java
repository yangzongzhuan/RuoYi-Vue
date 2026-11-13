package com.ruoyi.web.controller.store;

import java.math.BigDecimal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.store.domain.StoreMember;
import com.ruoyi.store.domain.StoreOrder;
import com.ruoyi.store.domain.StoreProduct;
import com.ruoyi.store.service.IStoreMemberService;
import com.ruoyi.store.service.IStoreOrderService;
import com.ruoyi.store.service.IStoreProductService;

/**
 * POS收银台Controller
 *
 * @author duqy
 * @date 2026-05-27
 */
@RestController
@RequestMapping("/store/pos")
public class StorePosController extends BaseController
{
    @Autowired
    private IStoreProductService storeProductService;

    @Autowired
    private IStoreMemberService storeMemberService;

    @Autowired
    private IStoreOrderService storeOrderService;

    /**
     * 根据条码查询商品
     */
    @PreAuthorize("@ss.hasPermi('store:order:add')")
    @GetMapping("/product/barcode/{barcode}")
    public AjaxResult getProductByBarcode(@PathVariable("barcode") String barcode)
    {
        StoreProduct product = storeProductService.selectStoreProductByBarcode(barcode);
        if (product == null)
        {
            return error("商品不存在或已下架");
        }
        return success(product);
    }

    /**
     * 根据手机号查询会员
     */
    @PreAuthorize("@ss.hasPermi('store:order:add')")
    @GetMapping("/member/phone/{phone}")
    public AjaxResult getMemberByPhone(@PathVariable("phone") String phone)
    {
        StoreMember member = storeMemberService.selectStoreMemberByPhone(phone);
        if (member == null)
        {
            return error("会员不存在");
        }
        return success(member);
    }

    /**
     * POS收银结算
     */
    @PreAuthorize("@ss.hasPermi('store:order:add')")
    @Log(title = "POS收银", businessType = BusinessType.INSERT)
    @PostMapping("/checkout")
    public AjaxResult checkout(@RequestBody StoreOrder storeOrder)
    {
        // 校验参数
        if (storeOrder.getItems() == null || storeOrder.getItems().isEmpty())
        {
            return error("购物车不能为空");
        }
        if (storeOrder.getPayType() == null || storeOrder.getPayType().isEmpty())
        {
            return error("请选择支付方式");
        }

        // 计算金额
        BigDecimal totalAmount = BigDecimal.ZERO;
        for (var item : storeOrder.getItems())
        {
            BigDecimal subtotal = item.getSalePrice().multiply(new BigDecimal(item.getQuantity()));
            item.setSubtotal(subtotal);
            totalAmount = totalAmount.add(subtotal);
        }
        storeOrder.setTotalAmount(totalAmount);

        BigDecimal discountAmount = storeOrder.getDiscountAmount() != null ? storeOrder.getDiscountAmount() : BigDecimal.ZERO;
        storeOrder.setPayAmount(totalAmount.subtract(discountAmount));

        // 余额支付校验
        if ("4".equals(storeOrder.getPayType()) && storeOrder.getMemberId() != null)
        {
            StoreMember member = storeMemberService.selectStoreMemberByMemberId(storeOrder.getMemberId());
            if (member == null)
            {
                return error("会员不存在");
            }
            if (member.getBalance().compareTo(storeOrder.getPayAmount()) < 0)
            {
                return error("会员余额不足，当前余额：" + member.getBalance());
            }
        }

        // 创建订单（会自动扣库存、更新会员等）
        int rows = storeOrderService.insertStoreOrder(storeOrder);
        if (rows > 0)
        {
            StoreOrder result = storeOrderService.selectStoreOrderByOrderId(storeOrder.getOrderId());
            return success(result);
        }
        return error("收银失败");
    }
}
