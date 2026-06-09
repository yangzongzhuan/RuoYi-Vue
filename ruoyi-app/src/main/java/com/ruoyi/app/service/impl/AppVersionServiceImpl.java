package com.ruoyi.app.service.impl;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.web.multipart.MultipartFile;
import com.ruoyi.app.domain.AppVersion;
import com.ruoyi.app.domain.vo.AppVersionCheckResponse;
import com.ruoyi.app.domain.vo.AppVersionUploadResponse;
import com.ruoyi.app.mapper.AppVersionMapper;
import com.ruoyi.app.service.IAppVersionService;
import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.framework.config.ServerConfig;

/**
 * APP版本管理 服务层实现
 *
 * @author ruoyi
 */
@Service
public class AppVersionServiceImpl implements IAppVersionService
{
    private static final Logger log = LoggerFactory.getLogger(AppVersionServiceImpl.class);

    /** APK/IPA/HAP 文件扩展名白名单 */
    private static final List<String> ALLOW_EXTS = Arrays.asList("apk", "ipa", "hap");

    @Autowired
    private AppVersionMapper appVersionMapper;

    @Autowired
    private ServerConfig serverConfig;

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
        resp.setId(latest.getId());
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

        boolean forceUpdate = false;
        if (hasUpdate && StringUtils.isNotEmpty(latest.getMinSupportVersion()))
        {
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
            }
        }
        if ("1".equals(latest.getUpdateType()))
        {
            forceUpdate = true;
        }
        resp.setForceUpdate(forceUpdate);
        return resp;
    }

    @Override
    public AppVersionUploadResponse uploadApk(MultipartFile file, String appId, String platform, Integer versionCode)
            throws Exception
    {
        // 1. 基础校验
        if (file == null || file.isEmpty())
        {
            throw new ServiceException("文件不能为空");
        }
        if (StringUtils.isAnyBlank(appId, platform) || versionCode == null)
        {
            throw new ServiceException("appId/platform/versionCode 不能为空");
        }
        // 2. 扩展名校验
        String original = file.getOriginalFilename();
        String ext = StringUtils.isEmpty(original) ? "" : StringUtils.substringAfterLast(original, ".").toLowerCase();
        if (!ALLOW_EXTS.contains(ext))
        {
            throw new ServiceException("文件类型不支持,仅允许 apk/ipa/hap");
        }
        // 3. 落盘目录:{profile}/upload/app/
        String filePath = RuoYiConfig.getUploadPath() + "/app";
        File dir = new File(filePath);
        if (!dir.exists() && !dir.mkdirs())
        {
            throw new ServiceException("创建上传目录失败:" + filePath);
        }
        // 4. 自定义文件名,避免重名
        String ts = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String safeAppId = appId.replaceAll("[^a-zA-Z0-9_-]", "_");
        String safePlatform = platform.replaceAll("[^a-zA-Z0-9_-]", "_");
        String newName = String.format("%s_%s_%d_%s.%s", safeAppId, safePlatform, versionCode, ts, ext);
        File dest = new File(dir, newName);
        file.transferTo(dest);
        // 5. 计算 MD5(流式读取,避免大文件 OOM)
        String md5;
        try (InputStream is = new BufferedInputStream(new FileInputStream(dest)))
        {
            md5 = DigestUtils.md5DigestAsHex(is);
        }
        // 6. 包大小 MB
        BigDecimal size = BigDecimal.valueOf(file.getSize() / 1024.0 / 1024.0)
                .setScale(2, RoundingMode.HALF_UP);
        // 7. 拼接 URL
        String url = serverConfig.getUrl() + "/profile/upload/app/" + newName;
        // 8. 封装响应
        AppVersionUploadResponse resp = new AppVersionUploadResponse();
        resp.setUrl(url);
        resp.setFileName("upload/app/" + newName);
        resp.setOriginalName(original);
        resp.setSize(size);
        resp.setMd5(md5);
        return resp;
    }

    @Override
    public String downloadById(Long id)
    {
        AppVersion v = appVersionMapper.selectAppVersionById(id);
        if (v == null)
        {
            throw new ServiceException("版本不存在");
        }
        if (StringUtils.isEmpty(v.getDownloadUrl()))
        {
            throw new ServiceException("下载地址未配置");
        }
        try
        {
            // 计数失败不阻塞下载,仅记录日志
            appVersionMapper.incrementDownloadCount(id);
        }
        catch (Exception e)
        {
            log.warn("递增下载次数失败,id={}", id, e);
        }
        return v.getDownloadUrl();
    }
}
