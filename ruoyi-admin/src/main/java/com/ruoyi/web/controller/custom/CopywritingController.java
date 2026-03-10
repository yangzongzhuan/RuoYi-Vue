package com.ruoyi.web.controller.custom;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
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
import com.ruoyi.system.domain.Copywriting;
import com.ruoyi.system.service.ICopywritingService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 文案管理Controller
 *
 * @author ruoyi
 */
@RestController
@RequestMapping("/custom/copywriting")
public class CopywritingController extends BaseController
{
    @Autowired
    private ICopywritingService copywritingService;

    /**
     * 查询文案列表
     */
    @PreAuthorize("@ss.hasPermi('custom:copywriting:list')")
    @GetMapping("/list")
    public TableDataInfo list(Copywriting copywriting)
    {
        startPage();
        List<Copywriting> list = copywritingService.selectCopywritingList(copywriting);
        return getDataTable(list);
    }

    /**
     * 导出文案列表
     */
    @PreAuthorize("@ss.hasPermi('custom:copywriting:export')")
    @Log(title = "文案管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Copywriting copywriting)
    {
        List<Copywriting> list = copywritingService.selectCopywritingList(copywriting);
        ExcelUtil<Copywriting> util = new ExcelUtil<Copywriting>(Copywriting.class);
        util.exportExcel(response, list, "文案数据");
    }

    /**
     * 获取文案详细信息
     */
    @PreAuthorize("@ss.hasPermi('custom:copywriting:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(copywritingService.selectCopywritingById(id));
    }

    /**
     * 新增文案
     */
    @PreAuthorize("@ss.hasPermi('custom:copywriting:add')")
    @Log(title = "文案管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Copywriting copywriting)
    {
        return toAjax(copywritingService.insertCopywriting(copywriting));
    }

    /**
     * 修改文案
     */
    @PreAuthorize("@ss.hasPermi('custom:copywriting:edit')")
    @Log(title = "文案管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Copywriting copywriting)
    {
        return toAjax(copywritingService.updateCopywriting(copywriting));
    }

    /**
     * 删除文案
     */
    @PreAuthorize("@ss.hasPermi('custom:copywriting:remove')")
    @Log(title = "文案管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(copywritingService.deleteCopywritingByIds(ids));
    }
}
