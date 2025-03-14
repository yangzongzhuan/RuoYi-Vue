/*
 * 您可以更改此项目但请不要删除作者署名谢谢，否则根据中华人民共和国版权法进行处理.
 * You may change this item but please do not remove the author's signature,
 * otherwise it will be dealt with according to the Copyright Law of the People's Republic of China.
 *
 * yangbuyi Copyright (c) https://yby6.com 2024.
 */

package com.ruoyi.system.coze.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * 流式 - 对话结果信息
 *
 * @author Yang Shuai
 * Create By 2024/07/04
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class CoZeCompletionEventResponse implements Serializable {
    /**
     * 事件
     */
    @JsonProperty("event")
    private String event;
    /**
     * 消息
     */
    @JsonProperty("message")
    private Message message;
    /**
     * 已完成
     */
    @JsonProperty("is_finish")
    private boolean isFinish;
    /**
     * 指数
     */
    @JsonProperty("index")
    private int index;
    /**
     * 会话id
     */
    @JsonProperty("conversation_id")
    private String conversationId;
    /**
     * 序列id
     */
    @JsonProperty("seq_id")
    private int seqId;

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

	public boolean isFinish() {
		return isFinish;
	}

	public void setFinish(boolean finish) {
		isFinish = finish;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String getConversationId() {
		return conversationId;
	}

	public void setConversationId(String conversationId) {
		this.conversationId = conversationId;
	}

	public int getSeqId() {
		return seqId;
	}

	public void setSeqId(int seqId) {
		this.seqId = seqId;
	}
}
