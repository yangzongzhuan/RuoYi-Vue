import request from '@/utils/request'

/**
 */
export function saveTemplate(data) {
  return request({
    url: '/psd/saveTemplate',
    method: 'post',
    data: data
  })
}


/**
 * 获取 PSD 配置列表（支持分页、条件查询）
 * @param {Object} query 查询参数，例如 { pageNum: 1, pageSize: 10, templateName: '', accountName: '' }
 */
export function listPSDConfig(query) {
  return request({
    url: '/psd/list',
    method: 'get',
    params: query
  })
}

/**
 * 修改 PSD 配置
 * @param {Object} data 包含 id 和配置数据
 */
export function updatePSDConfig(data) {
  return request({
    url: '/psd/updateTemplate',
    method: 'put',
    data: data
  })
}

/**
 * 删除 PSD 配置
 * @param {Number|String} id 配置的 id
 */
export function delPSDConfig(id) {
  return request({
    url: `/psd/deleteTemplate/${id}`,
    method: 'delete'
  })
}
