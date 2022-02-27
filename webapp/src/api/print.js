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

export function zplPreparePrint(params) {
    return request({
        url: '/v1/zplPrint/preparePrint',
        method: 'post',
        headers: { 'Content-Type': 'application/json' },
        data: params
    })
}


export function zplPrint(params) {
    return request({
        url: '/v1/zplPrint/print',
        method: 'post',
        headers: { 'Content-Type': 'application/json' },
        data: params
    })
}