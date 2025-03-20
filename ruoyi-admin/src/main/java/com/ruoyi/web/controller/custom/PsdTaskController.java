package com.ruoyi.web.controller.custom;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;

import cn.hutool.core.lang.UUID;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.alibaba.ttl.TransmittableThreadLocal;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
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
                JSONObject config = taskCopy.getConfig();
                List<String> nameList = psdMapper.selectAccountByName(psdTask.getAccountName());
                System.err.println("历史名字：" + nameList);

                JSONArray historyArray = new JSONArray(nameList);
                config.put("historyName", historyArray);

                // 生成配置字符串
                String answer = CozeRequestJsonUtils.test_chat_completions(String.valueOf(config));

                // 将 answer 转换为 JSONObject 对象
                // 使用 Jackson 解析 JSON 字符串
                ObjectMapper mapper = new ObjectMapper();
                JsonNode root = mapper.readTree(answer);
                answer = answer.replaceAll("\\\\", "\\\\\\\\");

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
                        .replaceFirst(configPattern, "var CONFIG = " + answer + ";")
                        .replaceFirst(timePattern, "$1\"" + foldersName + "\";");

                // 调试输出
                System.out.println("替换后的 JSX:\n" + modifiedJsx);

                // 调用 Photoshop
                ActiveXComponent ps = new ActiveXComponent("Photoshop.Application");
                Dispatch.invoke(ps, "DoJavaScript", Dispatch.Method, new Object[]{modifiedJsx}, new int[1]);
                // 更新状态为成功
                taskCopy.setStatus("0");
                psdTaskService.updatePsdTask(taskCopy);

                List<String> sampleTextList = new ArrayList<>();

                // 获取 imageConfigs 数组节点
                JsonNode imageConfigs = root.get("imageConfigs");
                if (imageConfigs != null && imageConfigs.isArray()) {
                    for (JsonNode imageConfig : imageConfigs) {
                        // 获取 textLayerConfigs 对象节点
                        JsonNode textLayerConfigs = imageConfig.get("textLayerConfigs");
                        if (textLayerConfigs != null && textLayerConfigs.isObject()) {
                            // 遍历 textLayerConfigs 的每个字段
                            Iterator<Map.Entry<String, JsonNode>> fields = textLayerConfigs.fields();
                            while (fields.hasNext()) {
                                Map.Entry<String, JsonNode> entry = fields.next();
                                JsonNode textLayer = entry.getValue();
                                String name = textLayer.get("name").asText();
                                // 判断 name 中是否包含 "名字"
                                if (name.contains("名字")) {
                                    String sampleText = textLayer.get("sampleText").asText();
                                    sampleTextList.add(sampleText);
                                }
                            }
                        }
                    }
                }

                // 输出结果
                System.err.println("包含 '名字' 的 sampleText 列表: " + sampleTextList);
                if (!sampleTextList.isEmpty()) {
                    psdMapper.insertAccountByName(taskCopy.getAccountName(), sampleTextList);
                }

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
    public AjaxResult getCoze(@RequestBody PsdTask psdTask) throws JsonProcessingException {
        JSONObject config = psdTask.getConfig();
        String accountName = config.getJSONObject("baseConfig").getString("accountName");
        List<String> nameList = psdMapper.selectAccountByName(accountName);

        JSONArray historyArray = new JSONArray(nameList);
        config.put("historyName", historyArray);
        System.err.println("历史名字： " + nameList);
        String answer = CozeRequestJsonUtils.test_chat_completions(String.valueOf(config));
        return AjaxResult.success(answer);
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
