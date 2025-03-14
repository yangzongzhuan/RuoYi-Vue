/*
 * 您可以更改此项目但请不要删除作者署名谢谢，否则根据中华人民共和国版权法进行处理.
 * You may change this item but please do not remove the author's signature,
 * otherwise it will be dealt with according to the Copyright Law of the People's Republic of China.
 *
 * yangbuyi Copyright (c) https://yby6.com 2024.
 */

package com.ruoyi.system.coze.constant;


/**
 * 常量 通用类
 *
 * @author Yang Shuai
 * Create By 2024/07/04
 */
public class Constants {
    /**
     * 空
     */
    public final static String NULL = "NULL";
    /**
     * 鉴权
     */
    public final static String AUTHORIZATION = "Authorization";
    /**
     * token前缀
     */
    public final static String BEARER = "Bearer ";

    /**
     * 角色
     * 官网支持的请求角色类型；user、assistant
     */
    public enum Role {

        /**
         * 使用者
         */
        USER("user"),
        /**
         * 助理
         */
        ASSISTANT("assistant"),
        ;

        /**
         * 密码
         */
        private final String code;

        /**
         * 角色
         *
         * @param code 密码
         */
        Role(String code) {
            this.code = code;
        }

		public String getCode() {
			return code;
		}
	}

    /**
     * 问答类型
     */
    public enum Type {

        /**
         * 文本
         */
        TEXT("text"),
        /**
         * img
         */
        IMG("file_url")
        ;

        /**
         * 密码
         */
        private final String code;

        /**
         * 角色
         *
         * @param code 密码
         */
        Type(String code) {
            this.code = code;
        }

		public String getCode() {
			return code;
		}
	}

}
