package com.ruoyi.store.mapper;

import java.util.List;
import java.util.Map;
import com.ruoyi.store.domain.StoreStockRecord;

/**
 * 库存流水Mapper接口
 *
 * @author duqy
 * @date 2026-05-26
 */
public interface StoreStockRecordMapper
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
     * 删除库存流水
     *
     * @param recordId 记录ID
     * @return 结果
     */
    public int deleteStoreStockRecordByRecordId(Long recordId);

    /**
     * 批量删除库存流水
     *
     * @param recordIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteStoreStockRecordByRecordIds(Long[] recordIds);

    /**
     * 近7天出入库统计
     */
    public List<Map<String, Object>> selectLast7DaysStockStats();
}
