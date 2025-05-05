package com.ruoyi.system.service.impl;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import com.alibaba.fastjson2.JSONObject;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.system.coze.utils.CozeGZHWorkFlow;
import com.ruoyi.system.domain.AutoCheck;
import com.ruoyi.system.mapper.PSDMapper;
import com.ruoyi.system.mapper.PsdTaskMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.domain.PsdTask;
import com.ruoyi.system.service.IPsdTaskService;

/**
 * 【请填写功能名称】Service业务层处理
 *
 * @author ruoyi
 * @date 2025-03-12
 */
@Service
public class PsdTaskServiceImpl implements IPsdTaskService
{
    @Autowired
    private PsdTaskMapper psdTaskMapper;

    @Autowired
    private PSDMapper psdMapper;

    /**
     * 查询【请填写功能名称】
     *
     * @param id 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    @Override
    public PsdTask selectPsdTaskById(Long id)
    {
        return psdTaskMapper.selectPsdTaskById(id);
    }

    /**
     * 查询【请填写功能名称】列表
     *
     * @param psdTask 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public List<PsdTask> selectPsdTaskList(PsdTask psdTask)
    {
        return psdTaskMapper.selectPsdTaskList(psdTask);
    }

    /**
     * 新增【请填写功能名称】
     *
     * @param psdTask 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int insertPsdTask(PsdTask psdTask)
    {
        return psdTaskMapper.insertPsdTask(psdTask);
    }

    /**
     * 修改【请填写功能名称】
     *
     * @param psdTask 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int updatePsdTask(PsdTask psdTask)
    {
        return psdTaskMapper.updatePsdTask(psdTask);
    }

    /**
     * 批量删除【请填写功能名称】
     *
     * @param ids 需要删除的【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deletePsdTaskByIds(Long[] ids)
    {
        return psdTaskMapper.deletePsdTaskByIds(ids);
    }

    /**
     * 删除【请填写功能名称】信息
     *
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deletePsdTaskById(Long id)
    {
        return psdTaskMapper.deletePsdTaskById(id);
    }

    @Override
    public PsdTask selectPsdTaskByUuid(String uuid) {
        return psdTaskMapper.selectPsdTaskByUuid(uuid);
    }

    @Override
    public String pushOfficialAccount(PsdTask psdTask) {
        AutoCheck checkInfo = psdMapper.getCheckInfo();
        String token = checkInfo.getToken();
        String realPath = psdTask.getRealPath();
        Path copywriterFile = Paths.get(realPath, "copywriter.txt");
        Path urlFile = Paths.get(realPath, "url.txt");

        try {
            // 1. 校验文件存在
            if (!Files.exists(copywriterFile)) {
                String msg = "缺少文件: " + copywriterFile;
                System.err.println(msg);
                throw new IOException(msg);
            }
            if (!Files.exists(urlFile)) {
                String msg = "缺少文件: " + urlFile;
                System.err.println(msg);
                throw new IOException(msg);
            }

            ObjectMapper mapper = new ObjectMapper();
            ObjectNode params = mapper.createObjectNode();

            // 2. 公公众号名称与作者
            params.put("gzhmc", psdTask.getGzhName());
            params.put("zuozhe", psdTask.getCreateBy());

            // 3. 读取 copywriter.txt
            String copywriter = String.join(
                    "\n",
                    Files.readAllLines(copywriterFile, StandardCharsets.UTF_8)
            );
            params.put("xhszw", copywriter);

            // 4. 二维码
            params.put("erweima", checkInfo.getQrCode());

            // 5. 读取 url.txt 并填充 tu1～tu5
            List<String> urls = Files.readAllLines(urlFile, StandardCharsets.UTF_8)
                    .stream()
                    .filter(s -> !s.trim().isEmpty())
                    .collect(Collectors.toList());
            for (int i = 0; i < urls.size() && i < 5; i++) {
                params.put("tu" + (i + 1), urls.get(i));
            }

            // 6. 调用工作流
            return CozeGZHWorkFlow.executeWithRetry(params, token);
        } catch (IOException e) {
            // 打印并继续抛出，让调用方感知到失败
            System.err.println("文件读取失败：" + e.getMessage());
            throw new RuntimeException("发布公众号前置文件校验失败", e);
        } catch (Exception e) {
            System.err.println("发布公众号执行异常：");
            e.printStackTrace();
            throw new RuntimeException("发布公众号失败", e);
        }
    }

}
