const Mock = require('mockjs')

const data = Mock.mock({
    'items|10': [{
        id: '@id',
        machineName: '@sentence(10, 20)',
        machineCategory: '@sentence(10, 20)',
        createdTime: '@datetime'
    }]
})

module.exports = [{
    url: '/v1/sysMachine',
    type: 'get',
    response: config => {
        const items = data.items
        return {
            code: 20000,
            data: {
                total: items.length,
                items: items
            }
        }
    }
}]