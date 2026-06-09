import request from '@/utils/request'
import axios from 'axios'

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
// 不要显式设置 Content-Type,axios 检测到 data 是 FormData 时会自动设置
// `multipart/form-data; boundary=...`,否则服务器无法解析 body,连接会被重置
export function uploadApk(data) {
  return axios({
    url: process.env.VUE_APP_BASE_API + '/app/version/upload',
    method: 'post',
    data: data
  })
}
