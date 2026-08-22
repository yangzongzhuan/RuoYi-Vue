package com.ruoyi.store.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.store.domain.StoreMember;
import com.ruoyi.store.mapper.StoreMemberMapper;
import com.ruoyi.store.service.IStoreMemberService;

/**
 * 会员Service业务层处理
 *
 * @author duqy
 * @date 2026-05-26
 */
@Service
public class StoreMemberServiceImpl implements IStoreMemberService
{
    @Autowired
    private StoreMemberMapper storeMemberMapper;

    @Override
    public StoreMember selectStoreMemberByMemberId(Long memberId)
    {
        return storeMemberMapper.selectStoreMemberByMemberId(memberId);
    }

    @Override
    public List<StoreMember> selectStoreMemberList(StoreMember storeMember)
    {
        return storeMemberMapper.selectStoreMemberList(storeMember);
    }

    @Override
    public int insertStoreMember(StoreMember storeMember)
    {
        if (storeMember.getBalance() == null) storeMember.setBalance(new java.math.BigDecimal("0"));
        if (storeMember.getPoints() == null) storeMember.setPoints(0L);
        if (storeMember.getTotalAmount() == null) storeMember.setTotalAmount(new java.math.BigDecimal("0"));
        if (storeMember.getTotalOrders() == null) storeMember.setTotalOrders(0L);
        if (storeMember.getStatus() == null) storeMember.setStatus("0");
        return storeMemberMapper.insertStoreMember(storeMember);
    }

    @Override
    public int updateStoreMember(StoreMember storeMember)
    {
        return storeMemberMapper.updateStoreMember(storeMember);
    }

    @Override
    public int deleteStoreMemberByMemberIds(Long[] memberIds)
    {
        return storeMemberMapper.deleteStoreMemberByMemberIds(memberIds);
    }

    @Override
    public int deleteStoreMemberByMemberId(Long memberId)
    {
        return storeMemberMapper.deleteStoreMemberByMemberId(memberId);
    }

    @Override
    public int deductBalance(Long memberId, java.math.BigDecimal amount)
    {
        StoreMember member = storeMemberMapper.selectStoreMemberByMemberId(memberId);
        if (member == null || member.getBalance().compareTo(amount) < 0)
        {
            throw new RuntimeException("会员余额不足");
        }
        member.setBalance(member.getBalance().subtract(amount));
        return storeMemberMapper.updateStoreMember(member);
    }

    @Override
    public int addPoints(Long memberId, Long points)
    {
        StoreMember member = storeMemberMapper.selectStoreMemberByMemberId(memberId);
        if (member == null)
        {
            throw new RuntimeException("会员不存在");
        }
        member.setPoints(member.getPoints() + points);
        return storeMemberMapper.updateStoreMember(member);
    }

    @Override
    public StoreMember selectStoreMemberByPhone(String phone)
    {
        return storeMemberMapper.selectStoreMemberByPhone(phone);
    }
}
