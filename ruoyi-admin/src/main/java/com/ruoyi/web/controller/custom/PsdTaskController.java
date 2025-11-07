package com.ruoyi.web.controller.custom;

import java.io.File;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletResponse;

import cn.hutool.core.lang.UUID;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.qiniu.common.QiniuException;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.system.coze.utils.CozeWorkflowClient;
import com.ruoyi.system.domain.PsdTask;
import com.ruoyi.system.mapper.PSDMapper;
import com.ruoyi.system.service.IPsdTaskService;
import com.ruoyi.web.controller.Queue.PhotoshopTaskQueue;
import com.ruoyi.web.controller.Queue.PushGZHTaskQueue;
import com.ruoyi.web.controller.Queue.TemPhotoshopJsxQuenu;
import com.ruoyi.web.controller.util.qiniuyun.QiNiuYunUtil;
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
    private PushGZHTaskQueue pushGZHTaskQueue;

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

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

    @GetMapping(value = "/byUuid/{uuid}")
    public AjaxResult getInfoByUuid(@PathVariable("uuid") String uuid)
    {
        return success(psdTaskService.selectPsdTaskByUuid(uuid));
    }

    /**
     * 新增psd任务
     */
    @PreAuthorize("@ss.hasPermi('psd:task:add')")
    @Log(title = "psd任务", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PsdTask psdTask) throws JsonProcessingException {
        ObjectNode config = (ObjectNode) OBJECT_MAPPER.readTree(psdTask.getConfig());
        validatePsdFile(config.path("baseConfig"));
        
        initTask(psdTask);
        psdTaskService.insertPsdTask(psdTask);
        PhotoshopTaskQueue.addTask(psdTask);

        return toAjax(1);
    }

    /**
     * 调用大模型生成内容
     */
    @Log(title = "psd任务", businessType = BusinessType.INSERT)
    @PostMapping("/getCoze")
    public AjaxResult getCoze(@RequestBody PsdTask psdTask) throws JsonProcessingException {
        ObjectNode config = (ObjectNode) OBJECT_MAPPER.readTree(psdTask.getConfig());
        String accountName = config.get("baseConfig").get("accountName").asText();
        
        List<String> nameList = psdMapper.selectAccountByName(accountName)
                .stream()
                .distinct()
                .collect(Collectors.toList());
        
        ArrayNode historyArray = OBJECT_MAPPER.createArrayNode();
        nameList.forEach(historyArray::add);
        config.set("historyName", historyArray);

        try {
            JsonNode jsonResponse = CozeWorkflowClient.executeWithRetry(config, psdMapper.getCheckInfo().getToken());
            return success(jsonResponse);
        } catch (Exception e) {
            throw new RuntimeException("调用大模型失败", e);
        }
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

    /**
     * 审核任务
     */
    @Log(title = "psd任务", businessType = BusinessType.UPDATE)
    @PostMapping("/checkTask")
    public AjaxResult checkTask(@RequestBody PsdTask psdTask)
    {
        psdTask.setStatus("2");
        psdTaskService.updatePsdTask(psdTask);
        TemPhotoshopJsxQuenu.addTask(psdTask);
        return toAjax(1);
    }

    /**
     * 手动创建psd任务（跳过大模型）
     */
    @PreAuthorize("@ss.hasPermi('psd:task:add')")
    @Log(title = "psd任务-手动创建", businessType = BusinessType.INSERT)
    @PostMapping("/manual")
    public AjaxResult addManual(@RequestBody PsdTask psdTask) throws JsonProcessingException {
        ObjectNode manualData = (ObjectNode) OBJECT_MAPPER.readTree(psdTask.getConfig());
        
        JsonNode baseConfigNode = manualData.path("baseConfig");
        if (baseConfigNode.isMissingNode()) {
            throw new RuntimeException("配置中缺少 baseConfig 字段");
        }
        
        validatePsdFile(baseConfigNode);

        JsonNode imageConfigsNode = manualData.path("imageConfigs");
        if (imageConfigsNode.isMissingNode() || !imageConfigsNode.isArray() || imageConfigsNode.size() == 0) {
            throw new RuntimeException("配置中缺少 imageConfigs 字段或为空");
        }

        ObjectNode finalConfig = OBJECT_MAPPER.createObjectNode();
        finalConfig.set("baseConfig", baseConfigNode);
        finalConfig.set("imageConfigs", imageConfigsNode);
        
        JsonNode copywriterNode = manualData.path("copywriter");
        if (!copywriterNode.isMissingNode()) {
            finalConfig.set("copywriter", copywriterNode);
        }

        psdTask.setConfig(finalConfig.toString());
        initTask(psdTask);
        psdTaskService.insertPsdTask(psdTask);
        TemPhotoshopJsxQuenu.addTask(psdTask);

        return toAjax(1);
    }

    /**
     * 发布公众号
     */
    @PostMapping("/pushOfficialAccount")
    public AjaxResult pushOfficialAccount(@RequestBody PsdTask psdTask){
        String realPath = psdTask.getRealPath();
        File outputDir = new File(realPath);
        File[] images = outputDir.listFiles((dir, name) -> name.toLowerCase().endsWith(".jpg"));

        if (images == null || images.length == 0) {
            throw new RuntimeException("目录中无 JPG 文件");
        }

        List<String> imageUrls = new ArrayList<>();
        for (File img : images) {
            if (img.getName().contains("封面")) {
                continue;
            }
            try {
                imageUrls.add(QiNiuYunUtil.uploadFile(img));
            } catch (QiniuException e) {
                throw new RuntimeException("图片上传失败: " + img.getName(), e);
            }
        }

        String executeId = psdTaskService.pushOfficialAccount(psdTask, imageUrls);
        psdTask.setGzhStatus("2");
        psdTaskService.updatePsdTask(psdTask);
        if (executeId != null && !executeId.isEmpty()) {
            pushGZHTaskQueue.addTask(executeId, psdTask);
        }
        return toAjax(1);
    }

    /**
     * 验证PSD文件
     */
    private void validatePsdFile(JsonNode configNode) {
        JsonNode psdPathNode = configNode.path("psdLocalPath");
        if (psdPathNode.isMissingNode()) {
            throw new RuntimeException("配置中缺少 psdLocalPath 字段");
        }

        String psdPath = psdPathNode.asText();
        File psdFile = new File(psdPath);

        if (!psdFile.exists()) {
            throw new RuntimeException("PSD文件不存在，路径: " + psdPath);
        }

        if (!psdFile.isFile()) {
            throw new RuntimeException("指定路径不是文件: " + psdPath);
        }

        if (!psdFile.canRead()) {
            throw new RuntimeException("PSD文件不可读，请检查权限: " + psdPath);
        }
    }

    /**
     * 初始化任务基本信息
     */
    private void initTask(PsdTask psdTask) {
        psdTask.setCreateDate(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        psdTask.setUuid(String.valueOf(UUID.randomUUID()));
        psdTask.setStatus("2");
        psdTask.setCreateBy(SecurityUtils.getAuthentication().getName());
    }
}
