/*
 * 您可以更改此项目但请不要删除作者署名谢谢，否则根据中华人民共和国版权法进行处理.
 * You may change this item but please do not remove the author's signature,
 * otherwise it will be dealt with according to the Copyright Law of the People's Republic of China.
 *
 * yangbuyi Copyright (c) https://yby6.com 2024.
 */

package com.ruoyi.system.coze.session;


import com.ruoyi.system.coze.domain.CoZeCompletionRequest;
import com.ruoyi.system.coze.domain.CoZeCompletionResponse;

/**
 * 扣子会话
 * CoZe 会话接口
 *
 * @author Yang Shuai
 * Create By 2024/07/04
 */
public interface CoZeSession {


    /**
     * 简单问答
     *
     * @param cozeCompletionRequest 扣子完成请求
     * @return {@link CoZeCompletionResponse}
     */
    CoZeCompletionResponse completions(CoZeCompletionRequest cozeCompletionRequest);


//    /**
//     * 简单问答 - 流式
//     *
//     * @param cozeCompletionRequest coze完成请求
//     * @param eventSourceListener   事件源侦听器
//     * @return {@link EventSource}
//     * @throws JsonProcessingException json处理异常
//     */
//    EventSource chatCompletions(CoZeCompletionRequest cozeCompletionRequest, EventSourceListener eventSourceListener) throws JsonProcessingException;
//
//
//    /**
//     * 简单问答 - 流式 - 自定义apiHost和apiKey
//     *
//     * @param apiHostByUser         用户提供api主机
//     * @param apiKeyByUser          用户提供api密钥
//     * @param cozeCompletionRequest coze完成请求
//     * @param eventSourceListener   事件源侦听器
//     * @return {@link EventSource}
//     * @throws JsonProcessingException json处理异常
//     */
//    EventSource chatCompletions(String apiHostByUser, String apiKeyByUser, CoZeCompletionRequest cozeCompletionRequest, EventSourceListener eventSourceListener) throws JsonProcessingException;
}
