/*
 * 您可以更改此项目但请不要删除作者署名谢谢，否则根据中华人民共和国版权法进行处理.
 * You may change this item but please do not remove the author's signature,
 * otherwise it will be dealt with according to the Copyright Law of the People's Republic of China.
 *
 * yangbuyi Copyright (c) https://yby6.com 2024.
 */

package com.ruoyi.system.coze.interceptor;


import cn.hutool.http.ContentType;
import cn.hutool.http.Header;
//import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.coze.constant.Constants;
import com.ruoyi.system.coze.core.CoZeApiKeyProvider;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;

/**
 * 自定义 YuanQI 拦截器
 * 在 okhttp3 请求的时候拦截
 *
 * @author Yang Shuai
 * Create By 2024/07/04
 */
public class CoZeInterceptor implements Interceptor {


    /**
     * YuanQI apiKey 需要在官网申请
     */
    private final String apiKeyBySystem;

    /**
     * 访问授权接口的认证 Token
     *
     * @param apiKeyBySystem 系统提供api密钥
     */

    public CoZeInterceptor(String apiKeyBySystem) {
        this.apiKeyBySystem = apiKeyBySystem;
    }

    /**
     * 拦截okhttp请求
     *
     * @param chain 链
     * @return 是否继续执行
     */
    @Override
    public Response intercept(Chain chain) throws IOException {
        // 1. 获取原始 Request
        Request original = chain.request();

        // 2. 如果调用者传递了apiKey，则使用调用者传递的apiKey
        String apiKeyByUser = original.header("apiKey");
        // 如果动态设置了apiKey，则使用动态设置的apiKey 否则使用系统提供的apiKey
        String apiKey = null == apiKeyByUser || Constants.NULL.equals(apiKeyByUser) ?
                StringUtils.isNotEmpty(CoZeApiKeyProvider.getApiKey()) ?
                        CoZeApiKeyProvider.getApiKey() : apiKeyBySystem : apiKeyByUser;
        // 如果鉴权头部没有Bearer则添加
        apiKey = apiKey.startsWith(Constants.BEARER) ? apiKey : Constants.BEARER.concat(apiKey);

        // 3. 构建 Request
        Request request = original.newBuilder()
                .url(original.url())
                .header(Header.AUTHORIZATION.getValue(),apiKey)
                .header(Header.CONTENT_TYPE.getValue(), ContentType.JSON.getValue())
                .method(original.method(), original.body())
                .build();

        // 4. 返回执行结果
        return chain.proceed(request);
    }

}
