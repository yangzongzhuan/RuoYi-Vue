package com.ruoyi.store.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.store.domain.StoreStockRecord;
import com.ruoyi.store.mapper.StoreStockRecordMapper;
import com.ruoyi.store.service.IStoreStockRecordService;

/**
 * 库存流水Service业务层处理
 *
 * @author duqy
 * @date 2026-05-26
 */
@Service
public class StoreStockRecordServiceImpl implements IStoreStockRecordService
{
    @Autowired
    private StoreStockRecordMapper storeStockRecordMapper;

    @Override
    public StoreStockRecord selectStoreStockRecordByRecordId(Long recordId)
    {
        return storeStockRecordMapper.selectStoreStockRecordByRecordId(recordId);
    }

    @Override
    public List<StoreStockRecord> selectStoreStockRecordList(StoreStockRecord storeStockRecord)
    {
        return storeStockRecordMapper.selectStoreStockRecordList(storeStockRecord);
    }

    @Override
    public int insertStoreStockRecord(StoreStockRecord storeStockRecord)
    {
        return storeStockRecordMapper.insertStoreStockRecord(storeStockRecord);
    }

    @Override
    public int updateStoreStockRecord(StoreStockRecord storeStockRecord)
    {
        return storeStockRecordMapper.updateStoreStockRecord(storeStockRecord);
    }

    @Override
    public int deleteStoreStockRecordByRecordIds(Long[] recordIds)
    {
        return storeStockRecordMapper.deleteStoreStockRecordByRecordIds(recordIds);
    }

    @Override
    public int deleteStoreStockRecordByRecordId(Long recordId)
    {
        return storeStockRecordMapper.deleteStoreStockRecordByRecordId(recordId);
    }
}
