package com.ruoyi.common.utils;

import com.alibaba.fastjson2.JSONObject;
import com.ruoyi.common.utils.http.CustomHttpUtils;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * 企业微信机器人通知工具类
 *
 * @author ruoyi
 */
public class WechatNotifyUtils
{
    private static final Logger log = LoggerFactory.getLogger(WechatNotifyUtils.class);

    private static final String HOST = "https://qyapi.weixin.qq.com";
    private static final String PATH = "/cgi-bin/webhook/send";

    /**
     * 发送抖音评论失败通知
     *
     * @param key 企业微信机器人key
     * @param accountName 作图账号名称
     * @param templateName 模板名称
     * @param createTime 创建时间
     * @param commentStatus 评论状态
     */
    public static void sendDouyinCommentFailedNotify(String key, String accountName, String templateName,
                                                      String createTime, String commentStatus)
    {
        Map<String, String> headers = new HashMap<>();
        Map<String, String> querys = new HashMap<>();
        querys.put("key", key);

        // 使用markdown类型消息，不需要跳转URL
        String content = "### 抖音评论失败通知\n" +
                "> **请及时检查**\n\n" +
                "**作图账号：** " + escapeMarkdown(accountName) + "\n" +
                "**模板名称：** " + escapeMarkdown(templateName) + "\n" +
                "**创建时间：** " + createTime + "\n" +
                "**评论状态：** <font color=\"warning\">" + escapeMarkdown(commentStatus) + "</font>";

        String jsonTemplate = "{\"msgtype\":\"markdown\"," +
                "\"markdown\":{\"content\":\"%s\"}}";

        String formattedJson = String.format(jsonTemplate, escapeJson(content));

        JSONObject json = JSONObject.parseObject(formattedJson);

        log.info("发送企业微信通知: {}", json.toString());

        try {
            HttpResponse response = CustomHttpUtils.doPost(HOST, PATH, headers, querys, json);
            String result = EntityUtils.toString(response.getEntity());
            log.info("企业微信通知响应: {}", result);
        } catch (Exception e) {
            log.error("发送企业微信通知失败", e);
        }
    }

    /**
     * 转义JSON特殊字符
     */
    private static String escapeJson(String str)
    {
        if (str == null)
        {
            return "";
        }
        return str.replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t");
    }

    /**
     * 转义Markdown特殊字符
     */
    private static String escapeMarkdown(String str)
    {
        if (str == null)
        {
            return "";
        }
        return str.replace("\\", "\\\\")
                .replace("*", "\\*")
                .replace("_", "\\_")
                .replace("[", "\\[")
                .replace("]", "\\]")
                .replace("(", "\\(")
                .replace(")", "\\)")
                .replace("#", "\\#")
                .replace("`", "\\`");
    }

    /**
     * 测试方法
     */
    public static void main(String[] args)
    {
        // 替换为你的企业微信机器人key
        String key = "65532a8e-3495-4c8f-baad-53f9445f7772";
        String accountName = "测试账号";
        String templateName = "测试模板";
        String createTime = "2026-03-15 01:13:55";
        String commentStatus = "失败";

        System.out.println("开始测试企业微信通知...");
        System.out.println("key: " + key);

        sendDouyinCommentFailedNotify(key, accountName, templateName, createTime, commentStatus);

        System.out.println("测试完成");
    }
}
