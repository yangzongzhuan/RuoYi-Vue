import request from '@/utils/request'

/**
 * APP 端公开 API 封装
 * 对应后端 com.ruoyi.web.controller.app.AppVersionApiController(@Anonymous)
 * - GET  /api/app/version/check
 * - GET  /api/app/version/download/{id}
 *
 * 注:本模块主要用于管理端"模拟 APP 端"调通链路,验证检查更新与下载代理;
 *    真实 APP 端 SDK 由原生团队维护,本文件只确保路径与 RuoYi 后端一致。
 */

/**
 * 检查更新
 * @param {string} appId       应用标识
 * @param {string} platform    平台(ios / android / harmony)
 * @param {number} versionCode 客户端当前版本 Code(整数)
 * @returns {Promise<{code:number, msg:string, data:AppVersionCheckResponse}>}
 */
export function checkUpdate(appId, platform, versionCode) {
  return request({
    url: '/api/app/version/check',
    method: 'get',
    params: { appId, platform, versionCode }
  })
}

/**
 * 用 checkUpdate 返回的 id 调下载代理
 * 后端会:1) 递增 download_count;2) 302 跳到真实地址(外链 / 本地资源)
 * @param {number} id 版本主键(由 checkUpdate 响应 data.id 给出)
 */
export function downloadById(id) {
  return request({
    url: '/api/app/version/download/' + id,
    method: 'get'
  })
}
