package com.ruoyi.store.mapper;

import java.util.List;
import com.ruoyi.store.domain.StoreMemberLevel;

/**
 * 会员等级Mapper接口
 *
 * @author duqy
 * @date 2026-05-26
 */
public interface StoreMemberLevelMapper
{
    /**
     * 查询会员等级
     *
     * @param levelId 等级ID
     * @return 会员等级
     */
    public StoreMemberLevel selectStoreMemberLevelByLevelId(Long levelId);

    /**
     * 查询会员等级列表
     *
     * @param storeMemberLevel 会员等级
     * @return 会员等级集合
     */
    public List<StoreMemberLevel> selectStoreMemberLevelList(StoreMemberLevel storeMemberLevel);

    /**
     * 新增会员等级
     *
     * @param storeMemberLevel 会员等级
     * @return 结果
     */
    public int insertStoreMemberLevel(StoreMemberLevel storeMemberLevel);

    /**
     * 修改会员等级
     *
     * @param storeMemberLevel 会员等级
     * @return 结果
     */
    public int updateStoreMemberLevel(StoreMemberLevel storeMemberLevel);

    /**
     * 删除会员等级
     *
     * @param levelId 等级ID
     * @return 结果
     */
    public int deleteStoreMemberLevelByLevelId(Long levelId);

    /**
     * 批量删除会员等级
     *
     * @param levelIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteStoreMemberLevelByLevelIds(Long[] levelIds);
}
