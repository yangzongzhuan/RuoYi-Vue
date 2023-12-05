package com.ruoyi.web.mapper;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.web.domain.TEnterpriseBasicDto;
import com.ruoyi.web.domain.TPatentsRelations;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ITPatentsRelationsMapper extends BaseMapper<TPatentsRelations> {

}
