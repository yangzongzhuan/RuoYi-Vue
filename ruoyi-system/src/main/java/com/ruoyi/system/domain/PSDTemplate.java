package com.ruoyi.system.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;


@Data
@TableName("psd_template")
public class PSDTemplate {
	// 设置id主键自增
	@TableId(type = IdType.AUTO)
	private Integer id;

	private String config;

	private String images;

}
