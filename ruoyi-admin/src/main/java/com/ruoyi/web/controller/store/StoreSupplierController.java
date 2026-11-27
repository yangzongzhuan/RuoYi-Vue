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
import com.ruoyi.store.domain.StoreSupplier;
import com.ruoyi.store.service.IStoreSupplierService;

/**
 * 供应商Controller
 *
 * @author duqy
 * @date 2026-05-26
 */
@RestController
@RequestMapping("/store/supplier")
public class StoreSupplierController extends BaseController
{
    @Autowired
    private IStoreSupplierService storeSupplierService;

    /**
     * 查询供应商列表
     */
    @PreAuthorize("@ss.hasPermi('store:supplier:list')")
    @GetMapping("/list")
    public TableDataInfo list(StoreSupplier storeSupplier)
    {
        startPage();
        List<StoreSupplier> list = storeSupplierService.selectStoreSupplierList(storeSupplier);
        return getDataTable(list);
    }

    /**
     * 导出供应商列表
     */
    @PreAuthorize("@ss.hasPermi('store:supplier:export')")
    @Log(title = "供应商", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, StoreSupplier storeSupplier)
    {
        List<StoreSupplier> list = storeSupplierService.selectStoreSupplierList(storeSupplier);
        ExcelUtil<StoreSupplier> util = new ExcelUtil<>(StoreSupplier.class);
        util.exportExcel(response, list, "供应商数据");
    }

    /**
     * 获取供应商详细信息
     */
    @PreAuthorize("@ss.hasPermi('store:supplier:query')")
    @GetMapping(value = "/{supplierId}")
    public AjaxResult getInfo(@PathVariable("supplierId") Long supplierId)
    {
        return success(storeSupplierService.selectStoreSupplierBySupplierId(supplierId));
    }

    /**
     * 新增供应商
     */
    @PreAuthorize("@ss.hasPermi('store:supplier:add')")
    @Log(title = "供应商", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody StoreSupplier storeSupplier)
    {
        return toAjax(storeSupplierService.insertStoreSupplier(storeSupplier));
    }

    /**
     * 修改供应商
     */
    @PreAuthorize("@ss.hasPermi('store:supplier:edit')")
    @Log(title = "供应商", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody StoreSupplier storeSupplier)
    {
        return toAjax(storeSupplierService.updateStoreSupplier(storeSupplier));
    }

    /**
     * 删除供应商
     */
    @PreAuthorize("@ss.hasPermi('store:supplier:remove')")
    @Log(title = "供应商", businessType = BusinessType.DELETE)
    @DeleteMapping("/{supplierIds}")
    public AjaxResult remove(@PathVariable Long[] supplierIds)
    {
        return toAjax(storeSupplierService.deleteStoreSupplierBySupplierIds(supplierIds));
    }
}
