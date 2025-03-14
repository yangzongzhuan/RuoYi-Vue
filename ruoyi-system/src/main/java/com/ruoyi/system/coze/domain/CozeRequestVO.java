package com.ruoyi.system.coze.domain;

import lombok.Data;

/**
 * @author gcc
 * @date 2024/8/18 10:53
 * @description:
 */
@Data
public class CozeRequestVO {


	/**
	 * 提问名称
	 */
	private String name;

	/**
	 * 性别
	 */
	private String sex;

	/**
	 * 提问名称2
	 */
	private String name2;

	/**
	 * 性别2
	 */
	private String sex2;

	/**
	 * 提问词
	 */
	private String otherQuestion = "宝宝起名";


}
