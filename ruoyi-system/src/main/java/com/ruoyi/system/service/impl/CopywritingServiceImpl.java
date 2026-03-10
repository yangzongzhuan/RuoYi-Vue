package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.CopywritingMapper;
import com.ruoyi.system.domain.Copywriting;
import com.ruoyi.system.service.ICopywritingService;

/**
 * 文案Service业务层处理
 *
 * @author ruoyi
 */
@Service
public class CopywritingServiceImpl implements ICopywritingService
{
    @Autowired
    private CopywritingMapper copywritingMapper;

    /**
     * 查询文案
     *
     * @param id 文案主键
     * @return 文案
     */
    @Override
    public Copywriting selectCopywritingById(Long id)
    {
        return copywritingMapper.selectCopywritingById(id);
    }

    /**
     * 查询文案列表
     *
     * @param copywriting 文案
     * @return 文案
     */
    @Override
    public List<Copywriting> selectCopywritingList(Copywriting copywriting)
    {
        return copywritingMapper.selectCopywritingList(copywriting);
    }

    /**
     * 新增文案
     *
     * @param copywriting 文案
     * @return 结果
     */
    @Override
    public int insertCopywriting(Copywriting copywriting)
    {
        return copywritingMapper.insertCopywriting(copywriting);
    }

    /**
     * 修改文案
     *
     * @param copywriting 文案
     * @return 结果
     */
    @Override
    public int updateCopywriting(Copywriting copywriting)
    {
        return copywritingMapper.updateCopywriting(copywriting);
    }

    /**
     * 批量删除文案
     *
     * @param ids 需要删除的文案主键
     * @return 结果
     */
    @Override
    public int deleteCopywritingByIds(Long[] ids)
    {
        return copywritingMapper.deleteCopywritingByIds(ids);
    }

    /**
     * 删除文案信息
     *
     * @param id 文案主键
     * @return 结果
     */
    @Override
    public int deleteCopywritingById(Long id)
    {
        return copywritingMapper.deleteCopywritingById(id);
    }
}
