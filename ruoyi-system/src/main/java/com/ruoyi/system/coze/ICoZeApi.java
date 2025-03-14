/*
 * 您可以更改此项目但请不要删除作者署名谢谢，否则根据中华人民共和国版权法进行处理.
 * You may change this item but please do not remove the author's signature,
 * otherwise it will be dealt with according to the Copyright Law of the People's Republic of China.
 *
 * yangbuyi Copyright (c) https://yby6.com 2024.
 */

package com.ruoyi.system.coze;



import com.ruoyi.system.coze.domain.CoZeCompletionRequest;
import com.ruoyi.system.coze.domain.CoZeCompletionResponse;
import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * 以 CoZe 官网 API 模型，定义接口。
 * <p>
 * 这里虽然说是定义接口但是也是http远程请求，根据对应的参数传递
 *
 * @author Yang Shuai
 * Create By 2024/07/04
 */
public interface ICoZeApi {

    /**
     * v1聊天完成
     */
    String v1_chat_completions = "v2/chat";

    /**
     * 问答智能体
     *
     * @param CoZeCompletionRequest 请求信息
     * @return 应答结果
     */
    @POST(v1_chat_completions)
    Single<CoZeCompletionResponse> completions(@Body CoZeCompletionRequest CoZeCompletionRequest);


}
