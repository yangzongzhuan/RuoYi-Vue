import request from '@/utils/request'

// 获取支持的模型类型列表
export function getModelTypes() {
  return request({
    url: '/tool/ai-gen/modelTypes',
    method: 'get'
  })
}
