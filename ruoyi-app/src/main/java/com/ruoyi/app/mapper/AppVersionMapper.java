package com.ruoyi.app.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.ruoyi.app.domain.AppVersion;

/**
 * APP版本管理 数据层
 *
 * @author ruoyi
 */
public interface AppVersionMapper
{
    /**
     * 查询APP版本信息
     *
     * @param id 主键
     * @return APP版本
     */
    public AppVersion selectAppVersionById(Long id);

    /**
     * 查询APP版本列表
     *
     * @param appVersion 查询条件
     * @return APP版本集合
     */
    public List<AppVersion> selectAppVersionList(AppVersion appVersion);

    /**
     * 查询某应用某平台最新启用版本
     *
     * @param appId   应用ID
     * @param platform 平台
     * @return 最新版本
     */
    public AppVersion selectLatestByAppAndPlatform(String appId, String platform);

    /**
     * 校验同 app+platform+version 是否存在(忽略自身)
     */
    public AppVersion checkVersionUnique(AppVersion appVersion);

    /**
     * 新增APP版本
     */
    public int insertAppVersion(AppVersion appVersion);

    /**
     * 修改APP版本
     */
    public int updateAppVersion(AppVersion appVersion);

    /**
     * 删除APP版本
     */
    public int deleteAppVersionById(Long id);

    /**
     * 批量删除APP版本
     */
    public int deleteAppVersionByIds(Long[] ids);

    /**
     * 切换状态
     */
    public int changeStatus(AppVersion appVersion);

    /**
     * 递增下载次数(+1)
     */
    public int incrementDownloadCount(@Param("id") Long id);
}
