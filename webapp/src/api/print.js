import request from '@/utils/request'

export function loadMetaData(params) {
  return request({
    url: '/v1/print/loadMetaData',
    method: 'get',
    params
  })
}

export function preview(params) {
  return request({
    url: '/v1/print/preview',
    method: 'post',
    headers: { 'Content-Type': 'application/json' },
    data: params
  })
}

export function print(params) {
  return request({
    url: '/v1/print/print',
    method: 'post',
    headers: { 'Content-Type': 'application/json' },
    data: params
  })
}
