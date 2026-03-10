import request from '@/utils/request'

// 查询文案列表
export function listCopywriting(query) {
  return request({
    url: '/custom/copywriting/list',
    method: 'get',
    params: query
  })
}

// 查询文案详细
export function getCopywriting(id) {
  return request({
    url: '/custom/copywriting/' + id,
    method: 'get'
  })
}

// 新增文案
export function addCopywriting(data) {
  return request({
    url: '/custom/copywriting',
    method: 'post',
    data: data
  })
}

// 修改文案
export function updateCopywriting(data) {
  return request({
    url: '/custom/copywriting',
    method: 'put',
    data: data
  })
}

// 删除文案
export function delCopywriting(id) {
  return request({
    url: '/custom/copywriting/' + id,
    method: 'delete'
  })
}

// 导出文案
export function exportCopywriting(query) {
  return request({
    url: '/custom/copywriting/export',
    method: 'post',
    params: query
  })
}
