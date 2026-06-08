package com.ruoyi.app.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.app.domain.AppVersion;
import com.ruoyi.app.domain.vo.AppVersionCheckResponse;
import com.ruoyi.app.mapper.AppVersionMapper;
import com.ruoyi.app.service.IAppVersionService;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.StringUtils;

/**
 * APP版本管理 服务层实现
 *
 * @author ruoyi
 */
@Service
public class AppVersionServiceImpl implements IAppVersionService
{
    @Autowired
    private AppVersionMapper appVersionMapper;

    @Override
    public AppVersion selectAppVersionById(Long id)
    {
        return appVersionMapper.selectAppVersionById(id);
    }

    @Override
    public List<AppVersion> selectAppVersionList(AppVersion appVersion)
    {
        return appVersionMapper.selectAppVersionList(appVersion);
    }

    @Override
    public boolean checkVersionUnique(AppVersion appVersion)
    {
        AppVersion exist = appVersionMapper.checkVersionUnique(appVersion);
        return exist == null;
    }

    @Override
    public int insertAppVersion(AppVersion appVersion)
    {
        if (!checkVersionUnique(appVersion))
        {
            throw new ServiceException("版本已存在");
        }
        return appVersionMapper.insertAppVersion(appVersion);
    }

    @Override
    public int updateAppVersion(AppVersion appVersion)
    {
        if (!checkVersionUnique(appVersion))
        {
            throw new ServiceException("版本已存在");
        }
        return appVersionMapper.updateAppVersion(appVersion);
    }

    @Override
    public int deleteAppVersionByIds(Long[] ids)
    {
        return appVersionMapper.deleteAppVersionByIds(ids);
    }

    @Override
    public int changeStatus(Long id, String status, String operator)
    {
        AppVersion update = new AppVersion();
        update.setId(id);
        update.setStatus(status);
        update.setUpdateBy(operator);
        return appVersionMapper.changeStatus(update);
    }

    @Override
    public AppVersionCheckResponse checkUpdate(String appId, String platform, Integer versionCode)
    {
        AppVersionCheckResponse resp = new AppVersionCheckResponse();
        // 入参不合法直接返回无更新,避免异常传播
        if (StringUtils.isEmpty(appId) || StringUtils.isEmpty(platform) || versionCode == null)
        {
            resp.setHasUpdate(false);
            resp.setForceUpdate(false);
            return resp;
        }
        AppVersion latest = appVersionMapper.selectLatestByAppAndPlatform(appId, platform);
        if (latest == null)
        {
            resp.setHasUpdate(false);
            resp.setForceUpdate(false);
            return resp;
        }
        // 填充最新版本信息
        resp.setLatestVersion(latest.getVersion());
        resp.setLatestVersionCode(latest.getVersionCode());
        resp.setUpdateType(latest.getUpdateType());
        resp.setDownloadUrl(latest.getDownloadUrl());
        resp.setUpdateLog(latest.getUpdateLog());
        resp.setPackageSize(latest.getPackageSize());
        resp.setMd5(latest.getMd5());
        resp.setPublishTime(latest.getPublishTime());

        boolean hasUpdate = latest.getVersionCode() != null && latest.getVersionCode() > versionCode;
        resp.setHasUpdate(hasUpdate);

        // 强升判定:versionCode < minSupportVersion(若配置了的话) 即强制升级
        boolean forceUpdate = false;
        if (hasUpdate && StringUtils.isNotEmpty(latest.getMinSupportVersion()))
        {
            // 简化处理:若客户端 code 数值上低于最低支持版本号(支持纯数字字符串),视为强升
            try
            {
                int minCode = Integer.parseInt(latest.getMinSupportVersion());
                if (versionCode < minCode)
                {
                    forceUpdate = true;
                }
            }
            catch (NumberFormatException ignored)
            {
                // 最低支持版本非数字时,不强升
            }
        }
        // 若 updateType 本身为 1 强制,也置为强升
        if ("1".equals(latest.getUpdateType()))
        {
            forceUpdate = true;
        }
        resp.setForceUpdate(forceUpdate);
        return resp;
    }
}
