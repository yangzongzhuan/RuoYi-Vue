package com.ruoyi.system.mapper;

import com.ruoyi.system.domain.PSDTemplate;
import org.apache.ibatis.annotations.Param;
import java.util.List;

public interface PSDMapper {
	/**
	 * 插入一条记录，返回受影响的行数。
	 * 如果 id 为自增，建议配置 useGeneratedKeys
	 */
	int insert(PSDTemplate psdTemplate);

	/**
	 * 根据 id 更新记录，返回受影响的行数
	 */
	int updateById(PSDTemplate psdTemplate);

	/**
	 * 根据 id 删除记录
	 */
	int deleteById(@Param("id") Integer id);

	/**
	 * 根据条件查询 PSDTemplate 列表
	 */
	List<PSDTemplate> selectByCondition(@Param("templateName") String templateName,
										@Param("accountName") String accountName);

	List<PSDTemplate> selectAll();

	void insertAccountByName(@Param("accountName") String accountName, @Param("nameList") List<String> nameList);

	List<String> selectAccountByName(@Param("accountName") String accountName);

	String getAutoCheck();
}
