import request from '@/utils/request'

export function getList(params) {
  return request({
    url: '/v1/template',
    method: 'get',
    params
  })
}

export function deleteTemplate(id) {
  return request({
    url: '/v1/template/' + id,
    method: 'delete'
  })
}

export function saveTemplate(params) {
  return request({
    url: '/v1/template/save',
    method: 'post',
    headers: { 'Content-Type': 'application/json' },
    data: params
  })
}

export function updateTemplate(params) {
  return request({
    url: '/v1/template/update',
    method: 'post',
    headers: { 'Content-Type': 'application/json;charset=UTF-8' },
    data: params
  })
}
