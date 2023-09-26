package com.ruoyi.system.service;

import com.ruoyi.system.domain.HisDept;

import java.util.List;

/**
 * @author xi
 * @create 2023/9/25 2:00
 */
public interface IHisDeptService {
    /**
     * 查询科室信息集合
     *
     * @param dept 科室信息
     * @return 科室列表
     */
    public List<HisDept> selectPostList(HisDept dept);
}
