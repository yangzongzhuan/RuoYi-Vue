/*
 * 您可以更改此项目但请不要删除作者署名谢谢，否则根据中华人民共和国版权法进行处理.
 * You may change this item but please do not remove the author's signature,
 * otherwise it will be dealt with according to the Copyright Law of the People's Republic of China.
 *
 * yangbuyi Copyright (c) https://yby6.com 2024.
 */

package com.ruoyi.system.coze.session;


/**
 * CoZe 会话工厂
 *
 * @author Yang Shuai
 * Create By 2024/07/04
 */
public interface CoZeSessionFactory {

    /**
     * 开启会话
     *
     * @return {@link CoZeSession}
     */
    CoZeSession openSession();

}
