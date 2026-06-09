package com.ruoyi.web.controller.app;

import java.util.List;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.ruoyi.app.domain.AppVersion;
import com.ruoyi.app.domain.vo.AppVersionUploadResponse;
import com.ruoyi.app.service.IAppVersionService;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;

/**
 * APP版本管理
 *
 * @author ruoyi
 */
@RestController
@RequestMapping("/app/version")
public class AppVersionController extends BaseController
{
    @Autowired
    private IAppVersionService appVersionService;

    /**
     * 查询APP版本列表
     */
    @PreAuthorize("@ss.hasPermi('app:version:list')")
    @GetMapping("/list")
    public TableDataInfo list(AppVersion appVersion)
    {
        startPage();
        List<AppVersion> list = appVersionService.selectAppVersionList(appVersion);
        return getDataTable(list);
    }

    /**
     * 导出APP版本列表
     */
    @PreAuthorize("@ss.hasPermi('app:version:export')")
    @Log(title = "APP版本管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, AppVersion appVersion)
    {
        List<AppVersion> list = appVersionService.selectAppVersionList(appVersion);
        ExcelUtil<AppVersion> util = new ExcelUtil<AppVersion>(AppVersion.class);
        util.exportExcel(response, list, "APP版本数据");
    }

    /**
     * 获取APP版本详细信息
     */
    @PreAuthorize("@ss.hasPermi('app:version:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(appVersionService.selectAppVersionById(id));
    }

    /**
     * 新增APP版本
     */
    @PreAuthorize("@ss.hasPermi('app:version:add')")
    @Log(title = "APP版本管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody AppVersion appVersion)
    {
        appVersion.setCreateBy(getUsername());
        return toAjax(appVersionService.insertAppVersion(appVersion));
    }

    /**
     * 修改APP版本
     */
    @PreAuthorize("@ss.hasPermi('app:version:edit')")
    @Log(title = "APP版本管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody AppVersion appVersion)
    {
        appVersion.setUpdateBy(getUsername());
        return toAjax(appVersionService.updateAppVersion(appVersion));
    }

    /**
     * 删除APP版本
     */
    @PreAuthorize("@ss.hasPermi('app:version:remove')")
    @Log(title = "APP版本管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(appVersionService.deleteAppVersionByIds(ids));
    }

    /**
     * 状态修改
     */
    @PreAuthorize("@ss.hasPermi('app:version:editStatus')")
    @Log(title = "APP版本管理", businessType = BusinessType.UPDATE)
    @PutMapping("/changeStatus")
    public AjaxResult changeStatus(@RequestBody AppVersion appVersion)
    {
        return toAjax(appVersionService.changeStatus(appVersion.getId(), appVersion.getStatus(), getUsername()));
    }

    /**
     * 上传 APK/IPA/HAP 安装包
     * 与 add/edit 共用 app:version:add | app:version:edit 权限字符;
     * 真实的数据写入仍由 add/edit 的 @PreAuthorize 保护,此处不降级权限。
     * 调用方须在 Authorization 头携带有效 token(JwtAuthenticationTokenFilter 校验)。
     */
    @PreAuthorize("@ss.hasPermi('app:version:add') or @ss.hasPermi('app:version:edit')")
    @Log(title = "APP版本管理", businessType = BusinessType.INSERT)
    @PostMapping("/upload")
    public AjaxResult uploadApk(@RequestParam("file") MultipartFile file,
                                @RequestParam("appId") String appId,
                                @RequestParam("platform") String platform,
                                @RequestParam("versionCode") Integer versionCode) throws Exception
    {
        AppVersionUploadResponse data = appVersionService.uploadApk(file, appId, platform, versionCode);
        return success(data);
    }
}
