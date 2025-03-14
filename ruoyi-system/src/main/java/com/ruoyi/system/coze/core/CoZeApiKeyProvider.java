/*
 * 您可以更改此项目但请不要删除作者署名谢谢，否则根据中华人民共和国版权法进行处理.
 * You may change this item but please do not remove the author's signature,
 * otherwise it will be dealt with according to the Copyright Law of the People's Republic of China.
 *
 * yangbuyi Copyright (c) https://yby6.com 2024.
 */
package com.ruoyi.system.coze.core;

/**
 * Api密钥提供程序 Authorization Key Provider
 *
 * <p>
 * 如果你需要在程序当中更新API密钥，可以使用{@link CoZeApiKeyProvider#setApiKey(String)}方法 <br />
 * 如果你需要获取API密钥，可以使用{@link CoZeApiKeyProvider#getApiKey()}方法 <br />
 * 请注意，这个类是线程安全的
 * </p>
 *
 * @author Yang Shuai
 * Create By 2024/06/17
 */
public class CoZeApiKeyProvider {
    /**
     * api密钥
     */
    private static volatile String apiKey;

    /**
     * 获取api密钥
     *
     * @return api密钥
     */
    public static String getApiKey() {
        return apiKey;
    }

    /**
     * 设置api密钥
     *
     * @param newApiKey 新api密钥
     */
    public static void setApiKey(String newApiKey) {
        apiKey = newApiKey;
    }
}
