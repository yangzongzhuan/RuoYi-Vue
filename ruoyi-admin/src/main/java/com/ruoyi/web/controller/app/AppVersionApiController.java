package com.ruoyi.web.controller.app;

import java.io.IOException;
import java.net.URLEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.app.domain.vo.AppVersionCheckResponse;
import com.ruoyi.app.service.IAppVersionService;
import com.ruoyi.common.annotation.Anonymous;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import jakarta.servlet.http.HttpServletResponse;

/**
 * APP 端公开 API
 *
 * @author ruoyi
 */
@RestController
@RequestMapping("/api/app/version")
public class AppVersionApiController extends BaseController
{
    @Autowired
    private IAppVersionService appVersionService;

    /**
     * 检查更新 (公开接口,无需登录)
     *
     * @param appId       应用ID
     * @param platform    平台(ios/android/harmony)
     * @param versionCode 客户端当前版本Code
     * @param channel     渠道标识(可选,预留)
     */
    @Anonymous
    @GetMapping("/check")
    public AjaxResult check(@RequestParam("appId") String appId,
                            @RequestParam("platform") String platform,
                            @RequestParam("versionCode") Integer versionCode,
                            @RequestParam(value = "channel", required = false) String channel)
    {
        AppVersionCheckResponse data = appVersionService.checkUpdate(appId, platform, versionCode);
        return success(data);
    }

    /**
     * APP 端下载代理 (公开接口,无需登录)
     * 行为:递增 download_count,然后 302 跳转到真实地址。
     * - 外链(http/https)直接 302 到外链
     * - 本地资源(以 / 开头)302 到 RuoYi 通用下载接口
     *
     * @param id 版本主键
     */
    @Anonymous
    @GetMapping("/download/{id}")
    public void download(@PathVariable("id") Long id, HttpServletResponse response) throws IOException
    {
        String url = appVersionService.downloadById(id);
        String target;
        if (url.startsWith("http://") || url.startsWith("https://"))
        {
            target = url;
        }
        else
        {
            // 本地资源:统一走 RuoYi 通用下载接口,resource 必须以 / 开头
            String resource = url.startsWith("/") ? url : "/" + url;
            target = "/common/download/resource?resource=" + URLEncoder.encode(resource, "UTF-8");
        }
        response.setStatus(HttpServletResponse.SC_FOUND);
        response.setHeader("Location", target);
    }
}
