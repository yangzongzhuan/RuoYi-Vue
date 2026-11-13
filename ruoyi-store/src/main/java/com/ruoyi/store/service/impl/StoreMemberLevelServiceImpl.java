package com.ruoyi.store.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.store.domain.StoreMemberLevel;
import com.ruoyi.store.mapper.StoreMemberLevelMapper;
import com.ruoyi.store.service.IStoreMemberLevelService;

/**
 * 会员等级Service业务层处理
 *
 * @author duqy
 * @date 2026-05-26
 */
@Service
public class StoreMemberLevelServiceImpl implements IStoreMemberLevelService
{
    @Autowired
    private StoreMemberLevelMapper storeMemberLevelMapper;

    @Override
    public StoreMemberLevel selectStoreMemberLevelByLevelId(Long levelId)
    {
        return storeMemberLevelMapper.selectStoreMemberLevelByLevelId(levelId);
    }

    @Override
    public List<StoreMemberLevel> selectStoreMemberLevelList(StoreMemberLevel storeMemberLevel)
    {
        return storeMemberLevelMapper.selectStoreMemberLevelList(storeMemberLevel);
    }

    @Override
    public int insertStoreMemberLevel(StoreMemberLevel storeMemberLevel)
    {
        if (storeMemberLevel.getStatus() == null) storeMemberLevel.setStatus("0");
        return storeMemberLevelMapper.insertStoreMemberLevel(storeMemberLevel);
    }

    @Override
    public int updateStoreMemberLevel(StoreMemberLevel storeMemberLevel)
    {
        return storeMemberLevelMapper.updateStoreMemberLevel(storeMemberLevel);
    }

    @Override
    public int deleteStoreMemberLevelByLevelIds(Long[] levelIds)
    {
        return storeMemberLevelMapper.deleteStoreMemberLevelByLevelIds(levelIds);
    }

    @Override
    public int deleteStoreMemberLevelByLevelId(Long levelId)
    {
        return storeMemberLevelMapper.deleteStoreMemberLevelByLevelId(levelId);
    }
}
