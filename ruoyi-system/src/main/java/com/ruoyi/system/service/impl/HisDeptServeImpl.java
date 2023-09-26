package com.ruoyi.system.service.impl;

import com.ruoyi.system.domain.HisDept;
import com.ruoyi.system.mapper.HisDeptMapper;
import com.ruoyi.system.service.IHisDeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author xi
 * @create 2023/9/26 23:03
 */
@Service
public class HisDeptServeImpl implements IHisDeptService {

    @Autowired
    private HisDeptMapper deptMapper;
    @Override
    public List<HisDept> selectPostList(HisDept dept) {
        return deptMapper.selectPostList(dept);
    }
}
