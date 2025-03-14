package com.ruoyi.system.coze.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CoZeCompletionResponse implements Serializable {

    /**
     * 返回码
     */
    private Integer code;

    /**
     * 消息
     */
    private String msg;

    /**
     * 会话id
     */
    @JsonProperty("conversation_id")
    private String conversationId;

    /**
     * 返回内容
     */
    private List<Message> messages;

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getConversationId() {
		return conversationId;
	}

	public void setConversationId(String conversationId) {
		this.conversationId = conversationId;
	}

	public List<Message> getMessages() {
		return messages;
	}

	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}
}
