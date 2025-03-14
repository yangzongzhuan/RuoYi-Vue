/*
 * 您可以更改此项目但请不要删除作者署名谢谢，否则根据中华人民共和国版权法进行处理.
 * You may change this item but please do not remove the author's signature,
 * otherwise it will be dealt with according to the Copyright Law of the People's Republic of China.
 *
 * yangbuyi Copyright (c) https://yby6.com 2024.
 */

package com.ruoyi.system.coze.session.defaults;



import com.ruoyi.system.coze.ICoZeApi;
import com.ruoyi.system.coze.interceptor.CoZeInterceptor;
import com.ruoyi.system.coze.session.CoZeConfiguration;
import com.ruoyi.system.coze.session.CoZeSessionFactory;
import com.ruoyi.system.coze.session.CoZeSession;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.util.concurrent.TimeUnit;


/**
 * 默认的实现 OpenAi API Factory 会话工厂
 *
 * @author Yang Shuai
 * Create By 2024/07/04
 */
public class DefaultCoZeSessionFactory implements CoZeSessionFactory {
    private static final Logger log = LoggerFactory.getLogger(DefaultCoZeSessionFactory.class);

    private final CoZeConfiguration cozeConfiguration;

    public DefaultCoZeSessionFactory(CoZeConfiguration cozeConfiguration) {
        this.cozeConfiguration = cozeConfiguration;
    }

    static {
    }

    /**
     * 初始化请求配置
     *
     * @return {@link CoZeSession}
     */
    @Override
    public CoZeSession openSession() {
        // 1. 日志配置
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(cozeConfiguration.getLevel());

        // 2. 开启 Http 客户端
        OkHttpClient okHttpClient = new OkHttpClient
                .Builder()
                .addInterceptor(httpLoggingInterceptor)
                .addInterceptor(new CoZeInterceptor(cozeConfiguration.getApiKey())) // 设置 apikey
                .connectTimeout(450, TimeUnit.SECONDS)
                .writeTimeout(450, TimeUnit.SECONDS)
                .readTimeout(450, TimeUnit.SECONDS)
                .build();

        // 3. 创建 API 服务
        ICoZeApi cozeApi = new Retrofit.Builder()
                .baseUrl(cozeConfiguration.getApiHost())
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(JacksonConverterFactory.create())
                .build().create(ICoZeApi.class);
        // 注入配置
        cozeConfiguration.setCoZeApi(cozeApi);
        cozeConfiguration.setOkHttpClient(okHttpClient);
        return new DefaultCoZeSession(cozeConfiguration);
    }

}
