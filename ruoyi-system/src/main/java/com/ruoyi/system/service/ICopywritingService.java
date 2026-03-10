package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.Copywriting;

/**
 * 文案Service接口
 *
 * @author ruoyi
 */
public interface ICopywritingService
{
    /**
     * 查询文案
     *
     * @param id 文案主键
     * @return 文案
     */
    public Copywriting selectCopywritingById(Long id);

    /**
     * 查询文案列表
     *
     * @param copywriting 文案
     * @return 文案集合
     */
    public List<Copywriting> selectCopywritingList(Copywriting copywriting);

    /**
     * 新增文案
     *
     * @param copywriting 文案
     * @return 结果
     */
    public int insertCopywriting(Copywriting copywriting);

    /**
     * 修改文案
     *
     * @param copywriting 文案
     * @return 结果
     */
    public int updateCopywriting(Copywriting copywriting);

    /**
     * 批量删除文案
     *
     * @param ids 需要删除的文案主键集合
     * @return 结果
     */
    public int deleteCopywritingByIds(Long[] ids);

    /**
     * 删除文案信息
     *
     * @param id 文案主键
     * @return 结果
     */
    public int deleteCopywritingById(Long id);
}
