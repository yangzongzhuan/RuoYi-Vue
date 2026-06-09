import request from '@/utils/request'

// 查询APP版本列表
export function listVersion(query) {
  return request({
    url: '/app/version/list',
    method: 'get',
    params: query
  })
}

// 查询APP版本详细
export function getVersion(id) {
  return request({
    url: '/app/version/' + id,
    method: 'get'
  })
}

// 新增APP版本
export function addVersion(data) {
  return request({
    url: '/app/version',
    method: 'post',
    data: data
  })
}

// 修改APP版本
export function updateVersion(data) {
  return request({
    url: '/app/version',
    method: 'put',
    data: data
  })
}

// 删除APP版本
export function delVersion(ids) {
  return request({
    url: '/app/version/' + ids,
    method: 'delete'
  })
}

// 修改APP版本状态
export function changeVersionStatus(data) {
  return request({
    url: '/app/version/changeStatus',
    method: 'put',
    data: data
  })
}

// 导出APP版本
export function exportVersion(query) {
  return request({
    url: '/app/version/export',
    method: 'post',
    data: query,
    responseType: 'blob'
  })
}

// 上传APK/IPA/HAP安装包
// 使用 axios 直接发请求,避免 RuoYi 通用 request 工具将 FormData 错误序列化为 JSON
export function uploadApk(data) {
  return axios({
    url: process.env.VUE_APP_BASE_API + '/app/version/upload',
    method: 'post',
    data: data,
    headers: { 'Content-Type': 'multipart/form-data' }
  })
}
