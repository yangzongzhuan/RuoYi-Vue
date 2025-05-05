package com.ruoyi.system.service;

import java.io.IOException;
import java.util.List;

import com.alibaba.fastjson2.JSONObject;
import com.ruoyi.system.domain.PsdTask;

/**
 * 【请填写功能名称】Service接口
 *
 * @author ruoyi
 * @date 2025-03-12
 */
public interface IPsdTaskService
{
    /**
     * 查询【请填写功能名称】
     *
     * @param id 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    public PsdTask selectPsdTaskById(Long id);

    /**
     * 查询【请填写功能名称】列表
     *
     * @param psdTask 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<PsdTask> selectPsdTaskList(PsdTask psdTask);

    /**
     * 新增【请填写功能名称】
     *
     * @param psdTask 【请填写功能名称】
     * @return 结果
     */
    public int insertPsdTask(PsdTask psdTask);

    /**
     * 修改【请填写功能名称】
     *
     * @param psdTask 【请填写功能名称】
     * @return 结果
     */
    public int updatePsdTask(PsdTask psdTask);

    /**
     * 批量删除【请填写功能名称】
     *
     * @param ids 需要删除的【请填写功能名称】主键集合
     * @return 结果
     */
    public int deletePsdTaskByIds(Long[] ids);

    /**
     * 删除【请填写功能名称】信息
     *
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    public int deletePsdTaskById(Long id);

    PsdTask selectPsdTaskByUuid(String uuid);

    String pushOfficialAccount(PsdTask psdTask);
}
