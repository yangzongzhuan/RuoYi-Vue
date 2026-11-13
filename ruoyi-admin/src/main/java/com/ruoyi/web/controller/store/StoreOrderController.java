package com.ruoyi.web.controller.store;

import java.util.List;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.store.domain.StoreOrder;
import com.ruoyi.store.service.IStoreOrderService;

/**
 * 订单Controller
 *
 * @author duqy
 * @date 2026-05-26
 */
@RestController
@RequestMapping("/store/order")
public class StoreOrderController extends BaseController
{
    @Autowired
    private IStoreOrderService storeOrderService;

    /**
     * 查询订单列表
     */
    @PreAuthorize("@ss.hasPermi('store:order:list')")
    @GetMapping("/list")
    public TableDataInfo list(StoreOrder storeOrder)
    {
        startPage();
        List<StoreOrder> list = storeOrderService.selectStoreOrderList(storeOrder);
        return getDataTable(list);
    }

    /**
     * 导出订单列表
     */
    @PreAuthorize("@ss.hasPermi('store:order:export')")
    @Log(title = "订单", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, StoreOrder storeOrder)
    {
        List<StoreOrder> list = storeOrderService.selectStoreOrderList(storeOrder);
        ExcelUtil<StoreOrder> util = new ExcelUtil<>(StoreOrder.class);
        util.exportExcel(response, list, "订单数据");
    }

    /**
     * 获取订单详细信息
     */
    @PreAuthorize("@ss.hasPermi('store:order:query')")
    @GetMapping(value = "/{orderId}")
    public AjaxResult getInfo(@PathVariable("orderId") Long orderId)
    {
        return success(storeOrderService.selectStoreOrderByOrderId(orderId));
    }

    /**
     * 新增订单
     */
    @PreAuthorize("@ss.hasPermi('store:order:add')")
    @Log(title = "订单", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody StoreOrder storeOrder)
    {
        return toAjax(storeOrderService.insertStoreOrder(storeOrder));
    }

    /**
     * 修改订单
     */
    @PreAuthorize("@ss.hasPermi('store:order:edit')")
    @Log(title = "订单", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody StoreOrder storeOrder)
    {
        return toAjax(storeOrderService.updateStoreOrder(storeOrder));
    }

    /**
     * 删除订单
     */
    @PreAuthorize("@ss.hasPermi('store:order:remove')")
    @Log(title = "订单", businessType = BusinessType.DELETE)
    @DeleteMapping("/{orderIds}")
    public AjaxResult remove(@PathVariable Long[] orderIds)
    {
        return toAjax(storeOrderService.deleteStoreOrderByOrderIds(orderIds));
    }
}
