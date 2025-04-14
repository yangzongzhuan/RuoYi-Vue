package com.ruoyi.system.service.impl;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import com.alibaba.fastjson2.JSONObject;
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

}
