/*
 * 您可以更改此项目但请不要删除作者署名谢谢，否则根据中华人民共和国版权法进行处理.
 * You may change this item but please do not remove the author's signature,
 * otherwise it will be dealt with according to the Copyright Law of the People's Republic of China.
 *
 * yangbuyi Copyright (c) https://yby6.com 2024.
 */

package com.ruoyi.system.coze.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

/**
 * 构建请求
 *
 * @author Yang Shuai
 * Create By 2024/07/04
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CoZeCompletionRequest implements Serializable {

    /**
     * 智能体ID
     */
    @JsonProperty("bot_id")
    private String botId;

    /**
     * 用户id
     */
    @JsonProperty("user")
    private String user;

    /**
     * 用户id
     */
    @JsonProperty("conversation_id")
    private String conversationId;

    /**
     * 问 - 内容
     */
    private String query;

    /**
     * 历史消息 上下文用
     */
    @JsonProperty("chat_history")
    private List<Message> chatHistory;

    /**
     * 流动
     */
    @JsonProperty("stream")
    private Boolean stream;


	public String getBotId() {
		return botId;
	}

	public void setBotId(String botId) {
		this.botId = botId;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getConversationId() {
		return conversationId;
	}

	public void setConversationId(String conversationId) {
		this.conversationId = conversationId;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public List<Message> getChatHistory() {
		return chatHistory;
	}

	public void setChatHistory(List<Message> chatHistory) {
		this.chatHistory = chatHistory;
	}

	public Boolean getStream() {
		return stream;
	}

	public void setStream(Boolean stream) {
		this.stream = stream;
	}
}
