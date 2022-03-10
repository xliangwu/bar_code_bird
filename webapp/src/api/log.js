import request from '@/utils/request'
import axios from 'axios'

export function getList(params) {
    return request({
        url: '/v1/log',
        method: 'get',
        params
    })
}