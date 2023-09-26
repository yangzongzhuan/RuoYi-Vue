package com.ruoyi.system.mapper;

import com.ruoyi.system.domain.HisDept;

import java.util.List;

/**
 * @author xi
 * @create 2023/9/25 1:46
 */
public interface HisDeptMapper {
    /**
     * 查询科室数据集合
     *
     * @param dept 科室信息
     * @return 科室数据集合
     */
    public List<HisDept> selectPostList(HisDept dept);
}
