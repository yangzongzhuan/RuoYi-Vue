package com.ruoyi.web.controller.util.qiniuyun;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;

import java.io.File;

/**
 * Utility class for uploading files to Qiniu Cloud and generating public URLs.
 */
public class QiNiuYunUtil {

    // 七牛 Access Key
    private static final String ACCESS_KEY = "Q4_mEXVbu9M4IgdXKRClDCthNdbKAYVJ0d1hLUqH";
    // 七牛 Secret Key
    private static final String SECRET_KEY = "pYzMOo4Ib6RVKKjarbajPyYUM40UMTC2uqvyc703";
    // 存储空间名称
    private static final String BUCKET_NAME = "jiuyanguoxue";
    // 自定义域名（需包含 http:// 或 https://）
    private static final String DOMAIN = "http://image.wade.org.cn";

    // 上传管理器
    private static final UploadManager uploadManager;
    // 上传凭证
    private static final String upToken;

    static {
        // 自动识别机房区域
        Configuration cfg = new Configuration(Region.autoRegion());
        uploadManager = new UploadManager(cfg);

        // 生成上传凭证
        Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
        upToken = auth.uploadToken(BUCKET_NAME);
    }

    /**
     * 上传本地文件到七牛云并返回访问 URL
     *
     * @param localFile 要上传的本地文件
     * @return 上传成功后的文件访问 URL
     * @throws QiniuException 上传失败时抛出
     */
    public static String uploadFile(File localFile) throws QiniuException {
        if (localFile == null || !localFile.exists() || !localFile.isFile()) {
            throw new IllegalArgumentException("Invalid file: " + localFile);
        }
        String key = localFile.getName();
        Response response = uploadManager.put(localFile, key, upToken);
        // 解析返回的 JSON
        JSONObject json = JSON.parseObject(response.bodyString());
        String returnedKey = json.getString("key");
        // 拼接访问 URL
        return DOMAIN + "/" + returnedKey;
    }

    /**
     * 示例：遍历目录上传所有 JPG 并输出 URL
     */
    public static void main(String[] args) {
        String localDirPath = "C:/output/2025-03-30/测试_测试_23-22-50";
        File outputDir = new File(localDirPath);
        if (!outputDir.exists() || !outputDir.isDirectory()) {
            System.err.println("Invalid directory: " + localDirPath);
            return;
        }
        File[] images = outputDir.listFiles((dir, name) -> name.toLowerCase().endsWith(".jpg"));
        if (images == null || images.length == 0) {
            System.out.println("No JPG files found in directory.");
            return;
        }
        for (File img : images) {
            try {
                String url = uploadFile(img);
                System.out.println(img.getName() + " → " + url);
            } catch (QiniuException e) {
                System.err.println("Failed to upload " + img.getName() + ": " + e.response.toString());
            }
        }
    }
}
