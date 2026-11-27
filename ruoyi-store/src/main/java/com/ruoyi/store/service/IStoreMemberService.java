package com.ruoyi.store.service;

import java.util.List;
import com.ruoyi.store.domain.StoreMember;

/**
 * 会员Service接口
 *
 * @author duqy
 * @date 2026-05-26
 */
public interface IStoreMemberService
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
     * 批量删除会员
     *
     * @param memberIds 需要删除的会员ID
     * @return 结果
     */
    public int deleteStoreMemberByMemberIds(Long[] memberIds);

    /**
     * 删除会员信息
     *
     * @param memberId 会员ID
     * @return 结果
     */
    public int deleteStoreMemberByMemberId(Long memberId);

    /**
     * 根据手机号查询会员
     *
     * @param phone 手机号
     * @return 会员
     */
    public StoreMember selectStoreMemberByPhone(String phone);

    /**
     * 扣减会员余额
     *
     * @param memberId 会员ID
     * @param amount 扣减金额
     * @return 结果
     */
    public int deductBalance(Long memberId, java.math.BigDecimal amount);

    /**
     * 增加会员积分
     *
     * @param memberId 会员ID
     * @param points 积分
     * @return 结果
     */
    public int addPoints(Long memberId, Long points);
}
