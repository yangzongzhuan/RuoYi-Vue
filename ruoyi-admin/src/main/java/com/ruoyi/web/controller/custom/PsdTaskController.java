package com.ruoyi.web.controller.custom;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

import cn.hutool.core.lang.UUID;
import com.alibaba.fastjson2.JSONObject;
import com.alibaba.ttl.TransmittableThreadLocal;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.ruoyi.system.coze.CozeRequestJsonUtils;
import com.ruoyi.system.domain.PsdTask;
import com.ruoyi.system.mapper.PSDMapper;
import com.ruoyi.system.service.IPsdTaskService;
import com.ruoyi.system.service.impl.DeepSeekService;
import org.apache.commons.lang3.StringEscapeUtils;
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

    // 创建一个 TransmittableThreadLocal 对象
    private static ThreadLocal<PsdTask> myThreadLocal = new TransmittableThreadLocal<>();

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
        // 先保存任务到数据库
        psdTask.setCreateDate(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        psdTask.setUuid(String.valueOf(UUID.randomUUID()));
        psdTask.setStatus("2");
        psdTaskService.insertPsdTask(psdTask);

        myThreadLocal.set(psdTask);

        new Thread(() -> {
            PsdTask taskCopy = myThreadLocal.get();
            try {
                // 读取 JSX 模板
                String basePath = System.getProperty("user.dir");
                String jsxTemplatePath = basePath + File.separator + "jsx" + File.separator + "generate.jsx";
                String jsxTemplate = new String(Files.readAllBytes(Paths.get(jsxTemplatePath)), StandardCharsets.UTF_8);

                // 生成配置字符串
                String configStr = CozeRequestJsonUtils.test_chat_completions(String.valueOf(taskCopy.getConfig()))
                        .replaceAll("\\\\", "\\\\\\\\");

                // 替换 JSX 模板
                LocalDateTime time = taskCopy.getcreateDate();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH-mm-ss");
                String formattedDate = time.format(formatter); // 输出示例：25-03-19
                // 安全转义
                String safeDate = StringEscapeUtils.escapeEcmaScript(formattedDate);
                String foldersName = taskCopy.getTemplateName() + "_" + taskCopy.getAccountName() + "_" + safeDate;

                // 精准替换
                String configPattern = "var CONFIG = .*?;";
                String timePattern = "(var\\s+foldersName\\s*=\\s*)[^;]*;";

                String modifiedJsx = jsxTemplate
                        .replaceFirst(configPattern, "var CONFIG = " + configStr + ";")
                        .replaceFirst(timePattern, "$1\"" + foldersName + "\";");

                // 调试输出
                System.out.println("替换后的 JSX:\n" + modifiedJsx);

                // 调用 Photoshop
                ActiveXComponent ps = new ActiveXComponent("Photoshop.Application");
                Dispatch.invoke(ps, "DoJavaScript", Dispatch.Method, new Object[]{modifiedJsx}, new int[1]);
                // 更新状态为成功
                taskCopy.setStatus("0");
                psdTaskService.updatePsdTask(taskCopy);

            } catch (IOException e) {
                System.out.println("JSX 读取失败：" + e.getMessage());
            }finally {
                // 任务失败，更新状态
                if (taskCopy.getStatus().equals("2")) {
                    taskCopy.setStatus("1");
                    psdTaskService.updatePsdTask(taskCopy);
                }
                // 清理线程本地变量
                myThreadLocal.remove();
            }
        }).start();

        return toAjax(1);
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
