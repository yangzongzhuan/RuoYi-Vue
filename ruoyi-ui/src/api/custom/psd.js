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

export function getListPSDConfigAll() {
  return request({
    url: '/psd/listAll',
    method: 'get'
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
// 增强版请求函数
export function getImage(imagePath) {
  return request({
    url: '/psd/psd-to-jpg',
    method: 'post',
    data: imagePath,
    headers: {
      'Content-Type': 'text/plain; charset=UTF-8' // 明确编码格式[1](@ref)
    },
    responseType: 'blob',
    transformRequest: [(data) => data] // 禁止axios自动序列化
  })
}

export function getCheckInfo() {
  return request({
    url: '/psd/getCheckInfo',
    method: 'get'
  })
}

export function updateCheck(data) {
  return request({
    url: '/psd/updateCheck',
    method: 'post',
    data: data
  })
}
