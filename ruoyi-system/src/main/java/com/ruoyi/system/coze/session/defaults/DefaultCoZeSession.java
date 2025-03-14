/*
 * 您可以更改此项目但请不要删除作者署名谢谢，否则根据中华人民共和国版权法进行处理.
 * You may change this item but please do not remove the author's signature,
 * otherwise it will be dealt with according to the Copyright Law of the People's Republic of China.
 *
 * yangbuyi Copyright (c) https://yby6.com 2024.
 */

package com.ruoyi.system.coze.session.defaults;



import com.ruoyi.system.coze.ICoZeApi;
import com.ruoyi.system.coze.domain.CoZeCompletionRequest;
import com.ruoyi.system.coze.domain.CoZeCompletionResponse;
import com.ruoyi.system.coze.session.CoZeConfiguration;
import com.ruoyi.system.coze.session.CoZeSession;
import okhttp3.sse.EventSource;


/**
 * 默认的 CoZe 会话实现CoZeSession
 *
 * @author Yang Shuai
 * Create By 2024/07/04
 */
public class DefaultCoZeSession implements CoZeSession {


    /**
     * 默认配置信息
     */
    private final CoZeConfiguration cozeConfiguration;

    /**
     * CoZe 接口
     */
    private final ICoZeApi cozeApi;
    /**
     * 工厂事件
     */
    private final EventSource.Factory factory;

    public DefaultCoZeSession(CoZeConfiguration cozeConfiguration) {
        this.cozeConfiguration = cozeConfiguration;
        this.cozeApi = cozeConfiguration.getCoZeApi();
        this.factory = cozeConfiguration.createRequestFactory();
    }

    /**
     * 问答模型扣子智能体AI
     *
     * @param cozeCompletionRequest 请求信息
     * @return 应答结果
     */
    @Override
    public CoZeCompletionResponse completions(CoZeCompletionRequest cozeCompletionRequest) {
        return this.cozeApi.completions(cozeCompletionRequest).blockingGet();
    }

//    /**
//     * 聊天完成
//     *
//     * @param cozeCompletionRequest coze完成请求
//     * @param eventSourceListener   事件源侦听器
//     * @return {@link EventSource}
//     * @throws JsonProcessingException json处理异常
//     */
//    @Override
//    public EventSource chatCompletions(CoZeCompletionRequest cozeCompletionRequest, EventSourceListener eventSourceListener) throws JsonProcessingException {
//        return chatCompletions(Constants.NULL, Constants.NULL, cozeCompletionRequest, eventSourceListener);
//    }
//
//
//    /**
//     * 问答模型扣子智能体AI
//     *
//     * @param apiHostByUser         用户提供api主机
//     * @param apiKeyByUser          用户提供api密钥
//     * @param cozeCompletionRequest coze完成请求
//     * @param eventSourceListener   事件源侦听器
//     * @return {@link EventSource}
//     * @throws JsonProcessingException json处理异常
//     */
//    @Override
//    public EventSource chatCompletions(String apiHostByUser, String apiKeyByUser, CoZeCompletionRequest cozeCompletionRequest, EventSourceListener eventSourceListener) throws JsonProcessingException {
//        // 当前为流式模式，如果为false则抛出异常
//        Assert.isTrue(cozeCompletionRequest.getStream(), "illegal parameter stream is false!");
//
//        // 1. 先判断用户传递的 Host、Key 是否为空，为空则使用默认配置信息
//        // 动态设置 Host、Key，便于用户传递自己的信息
//        String apiHost = Constants.NULL.equals(apiHostByUser) ? cozeConfiguration.getApiHost() : apiHostByUser;
//        String apiKey = Constants.NULL.equals(apiKeyByUser) ? cozeConfiguration.getApiKey() : apiKeyByUser;
//
//        // 2. 构建请求信息，并设置请求头 Authorization
//        Request request = new Request.Builder()
//                // 通过 IYuanQiApi 配置的 POST 接口，用这样的方式从统一的地方获取配置信息
//                .url(apiHost.concat(ICoZeApi.v1_chat_completions))
//                .addHeader(Constants.AUTHORIZATION, apiKey)
//                // 封装请求参数信息
//                .post(RequestBody.create(MediaType.parse(ContentType.JSON.getValue()), new ObjectMapper()
//                        .writeValueAsString(cozeCompletionRequest)))
//                .build();
//
//        // 返回结果信息；EventSource 对象可以取消应答
//        return factory.newEventSource(request, eventSourceListener);
//    }


}
