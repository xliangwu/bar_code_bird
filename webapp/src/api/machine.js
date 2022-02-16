import request from '@/utils/request'

export function getList(params) {
  return request({
    url: '/v1/sysMachine',
    method: 'get',
    params
  })
}

export function deleteMachine(id) {
  return request({
    url: '/v1/sysMachine/' + id,
    method: 'delete'
  })
}

export function saveMachine(params) {
  return request({
    url: '/v1/sysMachine/save',
    method: 'post',
    headers: { 'Content-Type': 'application/json' },
    data: params
  })
}

export function updateMachine(params) {
  return request({
    url: '/v1/sysMachine/update',
    method: 'post',
    headers: { 'Content-Type': 'application/json' },
    data: params
  })
}
