package com.ruoyi.web.controller.system;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.system.domain.HisDept;
import com.ruoyi.system.domain.SysPost;
import com.ruoyi.system.service.IHisDeptService;
import com.ruoyi.system.service.impl.HisDeptServeImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author xi
 * @create 2023/9/26 23:05
 */
@RestController
@RequestMapping("/his/dept")
public class HisDeptController extends BaseController {
    @Autowired
    private IHisDeptService deptService;
    /**
     * 获取科室列表
     */
    @PreAuthorize("@ss.hasPermi('his:dept:list')")
    @GetMapping("/list")
    public TableDataInfo list(HisDept dept)
    {
        startPage();
        List<HisDept> list = deptService.selectPostList(dept);
        return getDataTable(list);
    }
}
