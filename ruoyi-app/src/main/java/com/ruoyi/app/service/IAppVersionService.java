package com.ruoyi.app.service;

import java.util.List;
import com.ruoyi.app.domain.AppVersion;
import com.ruoyi.app.domain.vo.AppVersionCheckResponse;

/**
 * APP版本管理 服务层
 *
 * @author ruoyi
 */
public interface IAppVersionService
{
    /**
     * 查询APP版本信息
     */
    public AppVersion selectAppVersionById(Long id);

    /**
     * 查询APP版本列表
     */
    public List<AppVersion> selectAppVersionList(AppVersion appVersion);

    /**
     * 校验唯一性
     */
    public boolean checkVersionUnique(AppVersion appVersion);

    /**
     * 新增APP版本
     */
    public int insertAppVersion(AppVersion appVersion);

    /**
     * 修改APP版本
     */
    public int updateAppVersion(AppVersion appVersion);

    /**
     * 批量删除APP版本
     */
    public int deleteAppVersionByIds(Long[] ids);

    /**
     * 修改状态
     */
    public int changeStatus(Long id, String status, String operator);

    /**
     * APP 端检查更新
     *
     * @param appId       应用ID
     * @param platform    平台
     * @param versionCode 客户端当前 versionCode
     * @return 响应对象
     */
    public AppVersionCheckResponse checkUpdate(String appId, String platform, Integer versionCode);
}
