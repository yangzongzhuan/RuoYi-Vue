package com.ruoyi.web.controller.custom;

import java.io.File;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.bind.annotation.RequestParam;

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
import org.springframework.beans.factory.annotation.Value;
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
    
    // Python 服务地址
    private static final String PYTHON_SERVICE_URL = "http://localhost:5409";

    // 当前 Java 服务端口，从配置文件 server.port 读取
    @Value("${server.port}")
    private int serverPort;

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

    /**
     * 获取抖音账号列表
     */
    @GetMapping("/getDouyinAccounts")
    public AjaxResult getDouyinAccounts() {
        try {
            RestTemplate restTemplate = new RestTemplate();
            String url = PYTHON_SERVICE_URL + "/custom/api/douyin/getAccounts";
            
            ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);
            
            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                Map<String, Object> body = response.getBody();
                Map<String, Object> dataMap = (Map<String, Object>) body.get("data");
                List<List<Object>> accounts = (List<List<Object>>) dataMap.get("data");
                
                // 过滤出抖音账号（platform = 3）并转换格式
                // 数组格式：[id, platform, filePath, name, ...]
                List<Map<String, Object>> douyinAccounts = accounts.stream()
                    .filter(account -> account.size() >= 4 && "3".equals(String.valueOf(account.get(1))))
                    .map(account -> {
                        Map<String, Object> result = new HashMap<>();
                        result.put("id", account.get(0));
                        result.put("platform", account.get(1));
                        result.put("filePath", account.get(2));
                        result.put("name", account.get(3));
                        return result;
                    })
                    .collect(Collectors.toList());
                
                return AjaxResult.success(douyinAccounts);
            }
            
            return AjaxResult.error("获取账号列表失败");
        } catch (Exception e) {
            logger.error("获取抖音账号列表失败", e);
            return AjaxResult.error("获取账号列表失败: " + e.getMessage());
        }
    }

    /**
     * 发布到抖音
     */
    @PostMapping("/publishToDouyin")
    public AjaxResult publishToDouyin(@RequestBody Map<String, Object> params) {
        try {
            Long taskId = Long.valueOf(params.get("taskId").toString());
            String accountFile = params.get("accountFile").toString();
            String folderPath = params.get("folderPath").toString();
            String title = params.getOrDefault("title", "").toString();
            String copywriter = params.getOrDefault("copywriter", "").toString();
            String musicName = params.getOrDefault("musicName", "").toString();
            String musicType = params.getOrDefault("musicType", "search").toString();
            String publishType = params.get("publishType").toString();
            String publishTime = params.getOrDefault("publishTime", "").toString();
            
            // 更新任务状态为发布中
            PsdTask psdTask = psdTaskService.selectPsdTaskById(taskId);
            if (psdTask == null) {
                return AjaxResult.error("任务不存在");
            }
            
            // 如果folderPath为空，尝试使用任务的realPath
            if (folderPath == null || folderPath.trim().isEmpty()) {
                folderPath = psdTask.getRealPath();
                if (folderPath == null || folderPath.trim().isEmpty()) {
                    return AjaxResult.error("文件夹路径为空，请手动输入或等待任务执行完成");
                }
            }
            
            // 验证文件夹是否存在
            java.io.File folder = new java.io.File(folderPath);
            if (!folder.exists() || !folder.isDirectory()) {
                return AjaxResult.error("文件夹路径不存在: " + folderPath);
            }
            
            psdTask.setDyStatus("0"); // 0: 发布中
            psdTaskService.updatePsdTask(psdTask);
            
            // 构建回调URL（Java服务需要暴露此接口），端口从配置文件 server.port 读取
            String callbackUrl = "http://localhost:" + serverPort + "/psd/task/douyinCallback";
            logger.info("设置回调 URL: {}", callbackUrl);

            // 调用 Python 服务发布接口
            RestTemplate restTemplate = new RestTemplate();
            String url = PYTHON_SERVICE_URL + "/custom/api/douyin/publishImage";
            logger.info("准备调用 Python 服务: {}", url);
            
            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("account_file", accountFile);
            requestBody.put("folder_path", folderPath);
            requestBody.put("title", title);
            requestBody.put("copywriter", copywriter);
            requestBody.put("music_name", musicName);
            requestBody.put("music_type", musicType);
            requestBody.put("publish_type", publishType);
            requestBody.put("publish_time", publishTime);
            requestBody.put("task_id", taskId.toString());
            requestBody.put("callback_url", callbackUrl);
            
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);
            
            ResponseEntity<Map> response = restTemplate.postForEntity(url, entity, Map.class);
            
            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                Map<String, Object> body = response.getBody();
                Integer code = (Integer) body.get("code");
                
                if (code == 200) {
                    return AjaxResult.success("发布任务已提交");
                } else {
                    // 发布失败，更新状态
                    psdTask.setDyStatus("2");
                    psdTaskService.updatePsdTask(psdTask);
                    return AjaxResult.error(body.get("msg").toString());
                }
            }
            
            // 发布失败，更新状态
            psdTask.setDyStatus("2");
            psdTaskService.updatePsdTask(psdTask);
            return AjaxResult.error("调用发布接口失败");
            
        } catch (Exception e) {
            logger.error("发布到抖音失败", e);
            
            // 更新任务状态为失败
            try {
                Long taskId = Long.valueOf(params.get("taskId").toString());
                PsdTask psdTask = psdTaskService.selectPsdTaskById(taskId);
                if (psdTask != null) {
                    psdTask.setDyStatus("2");
                    psdTaskService.updatePsdTask(psdTask);
                }
            } catch (Exception ex) {
                logger.error("更新任务状态失败", ex);
            }
            
            return AjaxResult.error("发布失败: " + e.getMessage());
        }
    }

    /**
     * 抖音发布回调接口
     */
    @PostMapping("/douyinCallback")
    public AjaxResult douyinCallback(@RequestBody Map<String, Object> params) {
        logger.info("收到抖音发布回调请求，参数: {}", params);

        try {
            String taskIdStr = params.get("task_id").toString();
            Integer status = Integer.valueOf(params.get("status").toString());
            String message = params.getOrDefault("message", "").toString();

            logger.info("解析回调参数 - taskId: {}, status: {}, message: {}", taskIdStr, status, message);

            Long taskId = Long.valueOf(taskIdStr);
            PsdTask psdTask = psdTaskService.selectPsdTaskById(taskId);

            if (psdTask == null) {
                logger.error("回调失败：任务不存在，taskId=" + taskId);
                return AjaxResult.error("任务不存在");
            }

            logger.info("更新前任务状态: dyStatus={}", psdTask.getDyStatus());

            // 更新任务状态
            // status: 1-成功, 2-失败
            psdTask.setDyStatus(status.toString());
            int updateResult = psdTaskService.updatePsdTask(psdTask);

            logger.info("抖音发布回调成功 - taskId: {}, status: {}, message: {}, 更新结果: {}",
                       taskId, status, message, updateResult);

            return AjaxResult.success("回调处理成功");

        } catch (Exception e) {
            logger.error("处理抖音发布回调失败", e);
            return AjaxResult.error("回调处理失败: " + e.getMessage());
        }
    }
}
