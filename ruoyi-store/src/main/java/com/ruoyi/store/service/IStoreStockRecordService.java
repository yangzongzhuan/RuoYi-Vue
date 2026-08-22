package com.ruoyi.store.service;

import java.util.List;
import com.ruoyi.store.domain.StoreStockRecord;

/**
 * 库存流水Service接口
 *
 * @author duqy
 * @date 2026-05-26
 */
public interface IStoreStockRecordService
{
    /**
     * 查询库存流水
     *
     * @param recordId 记录ID
     * @return 库存流水
     */
    public StoreStockRecord selectStoreStockRecordByRecordId(Long recordId);

    /**
     * 查询库存流水列表
     *
     * @param storeStockRecord 库存流水
     * @return 库存流水集合
     */
    public List<StoreStockRecord> selectStoreStockRecordList(StoreStockRecord storeStockRecord);

    /**
     * 新增库存流水
     *
     * @param storeStockRecord 库存流水
     * @return 结果
     */
    public int insertStoreStockRecord(StoreStockRecord storeStockRecord);

    /**
     * 修改库存流水
     *
     * @param storeStockRecord 库存流水
     * @return 结果
     */
    public int updateStoreStockRecord(StoreStockRecord storeStockRecord);

    /**
     * 批量删除库存流水
     *
     * @param recordIds 需要删除的记录ID
     * @return 结果
     */
    public int deleteStoreStockRecordByRecordIds(Long[] recordIds);

    /**
     * 删除库存流水信息
     *
     * @param recordId 记录ID
     * @return 结果
     */
    public int deleteStoreStockRecordByRecordId(Long recordId);
}
