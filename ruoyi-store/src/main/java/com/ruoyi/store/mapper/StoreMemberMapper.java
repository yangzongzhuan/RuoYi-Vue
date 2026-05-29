package com.ruoyi.store.mapper;

import java.util.List;
import java.util.Map;
import com.ruoyi.store.domain.StoreMember;

/**
 * 会员Mapper接口
 *
 * @author duqy
 * @date 2026-05-26
 */
public interface StoreMemberMapper
{
    /**
     * 查询会员
     *
     * @param memberId 会员ID
     * @return 会员
     */
    public StoreMember selectStoreMemberByMemberId(Long memberId);

    /**
     * 查询会员列表
     *
     * @param storeMember 会员
     * @return 会员集合
     */
    public List<StoreMember> selectStoreMemberList(StoreMember storeMember);

    /**
     * 新增会员
     *
     * @param storeMember 会员
     * @return 结果
     */
    public int insertStoreMember(StoreMember storeMember);

    /**
     * 修改会员
     *
     * @param storeMember 会员
     * @return 结果
     */
    public int updateStoreMember(StoreMember storeMember);

    /**
     * 删除会员
     *
     * @param memberId 会员ID
     * @return 结果
     */
    public int deleteStoreMemberByMemberId(Long memberId);

    /**
     * 批量删除会员
     *
     * @param memberIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteStoreMemberByMemberIds(Long[] memberIds);

    /**
     * 会员总数
     */
    public Long selectMemberCount();

    /**
     * 会员等级分布
     */
    public List<Map<String, Object>> selectMemberLevelDistribution();

    /**
     * 近7天新增会员趋势
     */
    public List<Map<String, Object>> selectLast7DaysNewMemberStats();

    /**
     * 会员消费排行TOP10
     */
    public List<Map<String, Object>> selectMemberConsumeRank();

    /**
     * 根据手机号查询会员
     */
    public StoreMember selectStoreMemberByPhone(String phone);
}
