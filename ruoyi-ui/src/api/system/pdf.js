import request from '@/utils/request'

// 查询PDF文件列表
export function listPdfFile(query) {
  return request({
    url: '/system/pdf/list',
    method: 'post',
    data: query
  })
}

// 查询PDF文件详细
export function getPdfFile(pdfId) {
  return request({
    url: '/system/pdf/' + pdfId,
    method: 'get'
  })
}

// 新增PDF文件
export function addPdfFile(data) {
  return request({
    url: '/system/pdf/add',
    method: 'post',
    data: data
  })
}

// 修改PDF文件
export function updatePdfFile(data) {
  return request({
    url: '/system/pdf/edit',
    method: 'post',
    data: data
  })
}

// 删除PDF文件
export function delPdfFile(pdfIds) {
  return request({
    url: '/system/pdf/' + pdfIds,
    method: 'delete'
  })
}

// 重新处理PDF文件
export function reprocessPdfFile(pdfId) {
  return request({
    url: '/system/pdf/reprocess/' + pdfId,
    method: 'post'
  })
}