package com.ruoyi.web.controller.custom;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson2.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.ruoyi.system.coze.CozeRequestJsonUtils;
import com.ruoyi.system.domain.PsdTask;
import com.ruoyi.system.mapper.PSDMapper;
import com.ruoyi.system.service.IPsdTaskService;
import com.ruoyi.system.service.impl.DeepSeekService;
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
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;
import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Dispatch;

/**
 * psd任务Controller
 *
 * @author ruoyi
 * @date 2025-03-12
 */
@RestController
@RequestMapping("/psd/task")
public class PsdTaskController extends BaseController
{
    @Autowired
    private IPsdTaskService psdTaskService;

    @Autowired
    private PSDMapper psdMapper;

    @Autowired
    private DeepSeekService deepSeekService;

    /**
     * 查询psd任务列表
     */
    @PreAuthorize("@ss.hasPermi('psd:task:list')")
    @GetMapping("/list")
    public TableDataInfo list(PsdTask psdTask)
    {
        startPage();
        List<PsdTask> list = psdTaskService.selectPsdTaskList(psdTask);
        return getDataTable(list);
    }

    /**
     * 导出psd任务列表
     */
    @PreAuthorize("@ss.hasPermi('psd:task:export')")
    @Log(title = "psd任务", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PsdTask psdTask)
    {
        List<PsdTask> list = psdTaskService.selectPsdTaskList(psdTask);
        ExcelUtil<PsdTask> util = new ExcelUtil<PsdTask>(PsdTask.class);
        util.exportExcel(response, list, "psd任务数据");
    }

    /**
     * 获取psd任务详细信息
     */
    @PreAuthorize("@ss.hasPermi('psd:task:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(psdTaskService.selectPsdTaskById(id));
    }

    /**
     * 新增psd任务
     */
    @PreAuthorize("@ss.hasPermi('psd:task:add')")
    @Log(title = "psd任务", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PsdTask psdTask) throws JsonProcessingException {
        try {
            // 获取 JSX 模板路径（相对路径）
            String basePath = System.getProperty("user.dir");
            String jsxTemplatePath = basePath + File.separator + "jsx" + File.separator + "generate.jsx";
            // 读取 JSX 模板内容
            String jsxTemplate = new String(Files.readAllBytes(Paths.get(jsxTemplatePath)), StandardCharsets.UTF_8);

            JSONObject config = psdTask.getConfig();
            new Thread (() -> {
                String configStr = CozeRequestJsonUtils.test_chat_completions(String.valueOf(config)).replaceAll("\\\\", "\\\\\\\\");
                // 处理反斜杠
//            String configStr = psdTask.getJsonInfo().replaceAll("\\\\", "\\\\\\\\");

                // 替换 JSX 模板中的 CONFIG 部分
                String modifiedJsx = jsxTemplate.replaceFirst("var CONFIG = .*?;", "var CONFIG = " + configStr + ";");
                System.out.println(modifiedJsx);

                // 调试输出
                System.out.println("替换后的 JSX:\n" + modifiedJsx);

                // 调用 Photoshop
                ActiveXComponent ps = new ActiveXComponent("Photoshop.Application");
                Dispatch.invoke(ps, "DoJavaScript", Dispatch.Method, new Object[]{modifiedJsx}, new int[1]);
            }).start();

            // 保存任务
            psdTask.setCreateDate(LocalDateTime.now());
            return toAjax(psdTaskService.insertPsdTask(psdTask));
        } catch (IOException e) {
            throw new RuntimeException("JSX 文件处理失败: " + e.getMessage());
        }
    }

    @Log(title = "psd任务", businessType = BusinessType.INSERT)
    @PostMapping("/getCoze")
    public AjaxResult getCoze(@RequestBody PsdTask psdTask){
        JSONObject config = psdTask.getConfig();
        return AjaxResult.success(CozeRequestJsonUtils.test_chat_completions(String.valueOf(config)));
    }

    /**
     * 修改psd任务
     */
    @PreAuthorize("@ss.hasPermi('psd:task:edit')")
    @Log(title = "psd任务", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PsdTask psdTask)
    {
        return toAjax(psdTaskService.updatePsdTask(psdTask));
    }

    /**
     * 删除psd任务
     */
    @PreAuthorize("@ss.hasPermi('psd:task:remove')")
    @Log(title = "psd任务", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(psdTaskService.deletePsdTaskByIds(ids));
    }
}
