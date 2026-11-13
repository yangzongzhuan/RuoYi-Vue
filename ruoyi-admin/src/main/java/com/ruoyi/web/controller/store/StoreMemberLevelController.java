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
import com.ruoyi.store.domain.StoreMemberLevel;
import com.ruoyi.store.service.IStoreMemberLevelService;

/**
 * 会员等级Controller
 *
 * @author duqy
 * @date 2026-05-26
 */
@RestController
@RequestMapping("/store/memberLevel")
public class StoreMemberLevelController extends BaseController
{
    @Autowired
    private IStoreMemberLevelService storeMemberLevelService;

    /**
     * 查询会员等级列表
     */
    @PreAuthorize("@ss.hasPermi('store:memberLevel:list')")
    @GetMapping("/list")
    public TableDataInfo list(StoreMemberLevel storeMemberLevel)
    {
        startPage();
        List<StoreMemberLevel> list = storeMemberLevelService.selectStoreMemberLevelList(storeMemberLevel);
        return getDataTable(list);
    }

    /**
     * 导出会员等级列表
     */
    @PreAuthorize("@ss.hasPermi('store:memberLevel:export')")
    @Log(title = "会员等级", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, StoreMemberLevel storeMemberLevel)
    {
        List<StoreMemberLevel> list = storeMemberLevelService.selectStoreMemberLevelList(storeMemberLevel);
        ExcelUtil<StoreMemberLevel> util = new ExcelUtil<>(StoreMemberLevel.class);
        util.exportExcel(response, list, "会员等级数据");
    }

    /**
     * 获取会员等级详细信息
     */
    @PreAuthorize("@ss.hasPermi('store:memberLevel:query')")
    @GetMapping(value = "/{levelId}")
    public AjaxResult getInfo(@PathVariable("levelId") Long levelId)
    {
        return success(storeMemberLevelService.selectStoreMemberLevelByLevelId(levelId));
    }

    /**
     * 新增会员等级
     */
    @PreAuthorize("@ss.hasPermi('store:memberLevel:add')")
    @Log(title = "会员等级", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody StoreMemberLevel storeMemberLevel)
    {
        return toAjax(storeMemberLevelService.insertStoreMemberLevel(storeMemberLevel));
    }

    /**
     * 修改会员等级
     */
    @PreAuthorize("@ss.hasPermi('store:memberLevel:edit')")
    @Log(title = "会员等级", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody StoreMemberLevel storeMemberLevel)
    {
        return toAjax(storeMemberLevelService.updateStoreMemberLevel(storeMemberLevel));
    }

    /**
     * 删除会员等级
     */
    @PreAuthorize("@ss.hasPermi('store:memberLevel:remove')")
    @Log(title = "会员等级", businessType = BusinessType.DELETE)
    @DeleteMapping("/{levelIds}")
    public AjaxResult remove(@PathVariable Long[] levelIds)
    {
        return toAjax(storeMemberLevelService.deleteStoreMemberLevelByLevelIds(levelIds));
    }
}
