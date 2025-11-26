package com.ruoyi.web.controller.system;

import java.util.Date;
import java.util.List;
import java.util.Map;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.system.domain.SysPdfFile;
import com.ruoyi.system.domain.SysPdfImage;
import com.ruoyi.system.mapper.SysPdfImageMapper;
import com.ruoyi.system.service.ISysPdfFileService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * PDF文件Controller
 *
 * @author ruoyi
 */
@Controller
@RequestMapping("/system/pdf")
public class SysPdfFileController extends BaseController
{
    private String prefix = "system/pdf";

    @Autowired
    private ISysPdfFileService pdfFileService;

    @Autowired
    private SysPdfImageMapper pdfImageMapper;

    @PreAuthorize("@ss.hasPermi('system:pdf:view')")
    @GetMapping()
    public String pdf()
    {
        return prefix + "/index";
    }

    /**
     * 查询PDF文件列表
     */
    @PreAuthorize("@ss.hasPermi('system:pdf:list')")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SysPdfFile pdfFile)
    {
        startPage();
        List<SysPdfFile> list = pdfFileService.selectPdfFileList(pdfFile);
        return getDataTable(list);
    }

    /**
     * 导出PDF文件列表
     */
    @PreAuthorize("@ss.hasPermi('system:pdf:export')")
    @Log(title = "PDF文件", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(SysPdfFile pdfFile)
    {
        List<SysPdfFile> list = pdfFileService.selectPdfFileList(pdfFile);
        ExcelUtil<SysPdfFile> util = new ExcelUtil<SysPdfFile>(SysPdfFile.class);
        return util.exportExcel(list, "PDF文件数据");
    }

    /**
     * 新增PDF文件
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存PDF文件
     */
    @PreAuthorize("@ss.hasPermi('system:pdf:add')")
    @Log(title = "PDF文件", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(@RequestBody SysPdfFile pdfFile)
    {
        // 设置默认状态为待处理
        if (pdfFile.getStatus() == null) {
            pdfFile.setStatus("0");
        }
        
        // 验证PDF文件路径
        if (pdfFile.getPdfPath() == null || pdfFile.getPdfPath().trim().isEmpty()) {
            return AjaxResult.error("PDF文件路径不能为空");
        }
        
        return toAjax(pdfFileService.insertPdfFile(pdfFile));
    }

    /**
     * 修改PDF文件
     */
    @PreAuthorize("@ss.hasPermi('system:pdf:edit')")
    @GetMapping("/edit/{pdfId}")
    public String edit(@PathVariable("pdfId") Long pdfId, ModelMap mmap)
    {
        SysPdfFile pdfFile = pdfFileService.selectPdfFileById(pdfId);
        mmap.put("pdfFile", pdfFile);
        return prefix + "/edit";
    }

    /**
     * 修改保存PDF文件
     */
    @PreAuthorize("@ss.hasPermi('system:pdf:edit')")
    @Log(title = "PDF文件", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(@RequestBody SysPdfFile pdfFile)
    {
        // 验证必要字段
        if (pdfFile.getPdfId() == null) {
            return AjaxResult.error("PDF文件ID不能为空");
        }
        if (pdfFile.getPdfPath() == null || pdfFile.getPdfPath().trim().isEmpty()) {
            return AjaxResult.error("PDF文件路径不能为空");
        }
        
        return toAjax(pdfFileService.updatePdfFile(pdfFile));
    }

    /**
     * 删除PDF文件
     */
    @PreAuthorize("@ss.hasPermi('system:pdf:remove')")
    @Log(title = "PDF文件", businessType = BusinessType.DELETE)
    @DeleteMapping("/{pdfIds}")
    public AjaxResult remove(@PathVariable Long[] pdfIds)
    {
        return toAjax(pdfFileService.deletePdfFileByIds(pdfIds));
    }

    /**
     * 查看PDF转换后的图片
     */
    @PreAuthorize("@ss.hasPermi('system:pdf:viewImage')")
    @GetMapping("/viewImages/{pdfId}")
    public String viewImages(@PathVariable("pdfId") Long pdfId, ModelMap mmap)
    {
        SysPdfFile pdfFile = pdfFileService.selectPdfFileById(pdfId);
        List<SysPdfImage> images = pdfImageMapper.selectPdfImagesByPdfId(pdfId);
        mmap.put("pdfFile", pdfFile);
        mmap.put("images", images);
        return prefix + "/viewImages";
    }

    /**
     * 重新处理PDF文件
     */
    @PreAuthorize("@ss.hasPermi('system:pdf:edit')")
    @Log(title = "PDF文件", businessType = BusinessType.UPDATE)
    @PostMapping("/reprocess/{pdfId}")
    @ResponseBody
    public AjaxResult reprocess(@PathVariable("pdfId") Long pdfId)
    {
        boolean result = pdfFileService.processPdfToImages(pdfId);
        return result ? AjaxResult.success("PDF处理任务已开始") : AjaxResult.error("PDF处理失败");
    }
}
