import request from '@/utils/request'
import axios from 'axios'

export function getList(params) {
    return request({
        url: '/v1/customerStorage',
        method: 'get',
        params
    })
}

export function downloadTemplate(sourceType) {
    return axios.get('/api/v1/data/downloadTemplate', { responseType: 'blob', params: { sourceType } })
}

export function exportData(sourceType) {
    return axios.get('/api/v1/data/exportData', { responseType: 'blob', params: { sourceType } })
}

export function uploadTemplate(file, sourceType, customerId) {
    const formData = new FormData()
    formData.append('file', file)
    formData.append('sourceType', sourceType)
    formData.append('customerId', customerId)
    return axios.post('/api/v1/data/uploadTemplate', formData)
}