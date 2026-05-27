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
import com.ruoyi.store.domain.StoreMember;
import com.ruoyi.store.service.IStoreMemberService;

/**
 * 会员Controller
 *
 * @author duqy
 * @date 2026-05-26
 */
@RestController
@RequestMapping("/store/member")
public class StoreMemberController extends BaseController
{
    @Autowired
    private IStoreMemberService storeMemberService;

    /**
     * 查询会员列表
     */
    @PreAuthorize("@ss.hasPermi('store:member:list')")
    @GetMapping("/list")
    public TableDataInfo list(StoreMember storeMember)
    {
        startPage();
        List<StoreMember> list = storeMemberService.selectStoreMemberList(storeMember);
        return getDataTable(list);
    }

    /**
     * 导出会员列表
     */
    @PreAuthorize("@ss.hasPermi('store:member:export')")
    @Log(title = "会员", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, StoreMember storeMember)
    {
        List<StoreMember> list = storeMemberService.selectStoreMemberList(storeMember);
        ExcelUtil<StoreMember> util = new ExcelUtil<>(StoreMember.class);
        util.exportExcel(response, list, "会员数据");
    }

    /**
     * 获取会员详细信息
     */
    @PreAuthorize("@ss.hasPermi('store:member:query')")
    @GetMapping(value = "/{memberId}")
    public AjaxResult getInfo(@PathVariable("memberId") Long memberId)
    {
        return success(storeMemberService.selectStoreMemberByMemberId(memberId));
    }

    /**
     * 新增会员
     */
    @PreAuthorize("@ss.hasPermi('store:member:add')")
    @Log(title = "会员", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody StoreMember storeMember)
    {
        return toAjax(storeMemberService.insertStoreMember(storeMember));
    }

    /**
     * 修改会员
     */
    @PreAuthorize("@ss.hasPermi('store:member:edit')")
    @Log(title = "会员", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody StoreMember storeMember)
    {
        return toAjax(storeMemberService.updateStoreMember(storeMember));
    }

    /**
     * 删除会员
     */
    @PreAuthorize("@ss.hasPermi('store:member:remove')")
    @Log(title = "会员", businessType = BusinessType.DELETE)
    @DeleteMapping("/{memberIds}")
    public AjaxResult remove(@PathVariable Long[] memberIds)
    {
        return toAjax(storeMemberService.deleteStoreMemberByMemberIds(memberIds));
    }
}
