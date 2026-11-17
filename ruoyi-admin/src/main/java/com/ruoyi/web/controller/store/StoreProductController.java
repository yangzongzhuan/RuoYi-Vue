package com.ruoyi.web.controller.store;

import java.util.List;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.store.domain.StoreProduct;
import com.ruoyi.store.service.IStoreProductService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 商品Controller
 *
 * @author duqy
 * @date 2026-05-26
 */
@RestController
@RequestMapping("/store/product")
public class StoreProductController extends BaseController
{
    @Autowired
    private IStoreProductService storeProductService;

    /**
     * 查询商品列表
     */
    @PreAuthorize("@ss.hasPermi('store:product:list')")
    @GetMapping("/list")
    public TableDataInfo list(StoreProduct storeProduct)
    {
        startPage();
        List<StoreProduct> list = storeProductService.selectStoreProductList(storeProduct);
        return getDataTable(list);
    }

    /**
     * 导出商品列表
     */
    @PreAuthorize("@ss.hasPermi('store:product:export')")
    @Log(title = "商品", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, StoreProduct storeProduct)
    {
        List<StoreProduct> list = storeProductService.selectStoreProductList(storeProduct);
        ExcelUtil<StoreProduct> util = new ExcelUtil<StoreProduct>(StoreProduct.class);
        util.exportExcel(response, list, "商品数据");
    }

    /**
     * 获取商品详细信息
     */
    @PreAuthorize("@ss.hasPermi('store:product:query')")
    @GetMapping(value = "/{productId}")
    public AjaxResult getInfo(@PathVariable("productId") Long productId)
    {
        return success(storeProductService.selectStoreProductByProductId(productId));
    }

    /**
     * 新增商品
     */
    @PreAuthorize("@ss.hasPermi('store:product:add')")
    @Log(title = "商品", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody StoreProduct storeProduct)
    {
        return toAjax(storeProductService.insertStoreProduct(storeProduct));
    }

    /**
     * 修改商品
     */
    @PreAuthorize("@ss.hasPermi('store:product:edit')")
    @Log(title = "商品", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody StoreProduct storeProduct)
    {
        return toAjax(storeProductService.updateStoreProduct(storeProduct));
    }

    /**
     * 删除商品
     */
    @PreAuthorize("@ss.hasPermi('store:product:remove')")
    @Log(title = "商品", businessType = BusinessType.DELETE)
    @DeleteMapping("/{productIds}")
    public AjaxResult remove(@PathVariable Long[] productIds)
    {
        return toAjax(storeProductService.deleteStoreProductByProductIds(productIds));
    }
}
