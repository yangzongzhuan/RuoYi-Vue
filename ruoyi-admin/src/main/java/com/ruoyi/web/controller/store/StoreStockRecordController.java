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
import com.ruoyi.store.domain.StoreStockRecord;
import com.ruoyi.store.service.IStoreStockRecordService;

/**
 * 库存流水Controller
 *
 * @author duqy
 * @date 2026-05-26
 */
@RestController
@RequestMapping("/store/stockRecord")
public class StoreStockRecordController extends BaseController
{
    @Autowired
    private IStoreStockRecordService storeStockRecordService;

    /**
     * 查询库存流水列表
     */
    @PreAuthorize("@ss.hasPermi('store:stockRecord:list')")
    @GetMapping("/list")
    public TableDataInfo list(StoreStockRecord storeStockRecord)
    {
        startPage();
        List<StoreStockRecord> list = storeStockRecordService.selectStoreStockRecordList(storeStockRecord);
        return getDataTable(list);
    }

    /**
     * 导出库存流水列表
     */
    @PreAuthorize("@ss.hasPermi('store:stockRecord:export')")
    @Log(title = "库存流水", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, StoreStockRecord storeStockRecord)
    {
        List<StoreStockRecord> list = storeStockRecordService.selectStoreStockRecordList(storeStockRecord);
        ExcelUtil<StoreStockRecord> util = new ExcelUtil<>(StoreStockRecord.class);
        util.exportExcel(response, list, "库存流水数据");
    }

    /**
     * 获取库存流水详细信息
     */
    @PreAuthorize("@ss.hasPermi('store:stockRecord:query')")
    @GetMapping(value = "/{recordId}")
    public AjaxResult getInfo(@PathVariable("recordId") Long recordId)
    {
        return success(storeStockRecordService.selectStoreStockRecordByRecordId(recordId));
    }

    /**
     * 新增库存流水
     */
    @PreAuthorize("@ss.hasPermi('store:stockRecord:add')")
    @Log(title = "库存流水", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody StoreStockRecord storeStockRecord)
    {
        return toAjax(storeStockRecordService.insertStoreStockRecord(storeStockRecord));
    }

    /**
     * 修改库存流水
     */
    @PreAuthorize("@ss.hasPermi('store:stockRecord:edit')")
    @Log(title = "库存流水", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody StoreStockRecord storeStockRecord)
    {
        return toAjax(storeStockRecordService.updateStoreStockRecord(storeStockRecord));
    }

    /**
     * 删除库存流水
     */
    @PreAuthorize("@ss.hasPermi('store:stockRecord:remove')")
    @Log(title = "库存流水", businessType = BusinessType.DELETE)
    @DeleteMapping("/{recordIds}")
    public AjaxResult remove(@PathVariable Long[] recordIds)
    {
        return toAjax(storeStockRecordService.deleteStoreStockRecordByRecordIds(recordIds));
    }
}
