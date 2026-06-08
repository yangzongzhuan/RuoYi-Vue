package com.ruoyi.web.controller.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.app.domain.vo.AppVersionCheckResponse;
import com.ruoyi.app.service.IAppVersionService;
import com.ruoyi.common.annotation.Anonymous;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;

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
}
