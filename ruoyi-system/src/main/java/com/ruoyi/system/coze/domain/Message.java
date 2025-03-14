package com.ruoyi.system.coze.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ruoyi.system.coze.constant.Constants;

import java.io.Serializable;

/**
 * 消息内容
 *
 * @author yangs
 * Create By 2024/07/04
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Message implements Serializable {

    /**
     * 角色 assistant
     */
    private String role;

    /**
     * 消息类型  verbose、answer、follow_up
     */
    private String type;

    /**
     * 会话内容
     */
    private String content;

    /**
     * 内容类型
     */
    @JsonProperty("content_type")
    private String contentType;

    /**
     * 消息
     *
     * @param builder 建设者
     */
    private Message(Builder builder) {
        this.role = builder.role;
        this.content = builder.content;
    }

	// 默认构造函数
	public Message() {
	}

    /**
     * 建设者
     *
     * @return {@link Builder}
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * 建造者模式
     *
     * @author yangs
     * Create By 2024/07/04
     */
    public static final class Builder {

        /**
         * 角色
         */
        private String role;
        /**
         * 所容纳之物
         */
        private String content;

        /**
         * 建设者
         */
        public Builder() {
        }

        /**
         * 角色
         *
         * @param role 角色
         * @return {@link Builder}
         */
        public Builder role(Constants.Role role) {
            this.role = role.getCode();
            return this;
        }

        /**
         * 所容纳之物
         *
         * @param content 所容纳之物
         * @return {@link Builder}
         */
        public Builder content(String content) {
            this.content = content;
            return this;
        }

        /**
         * 建筑
         *
         * @return {@link Message}
         */
        public Message build() {
            return new Message(this);
        }
    }

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
}
