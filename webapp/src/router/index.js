import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

/* Layout */
import Layout from '@/layout'

/**
 * Note: sub-menu only appear when route children.length >= 1
 * Detail see: https://panjiachen.github.io/vue-element-admin-site/guide/essentials/router-and-nav.html
 *
 * hidden: true                   if set true, item will not show in the sidebar(default is false)
 * alwaysShow: true               if set true, will always show the root menu
 *                                if not set alwaysShow, when item has more than one children route,
 *                                it will becomes nested mode, otherwise not show the root menu
 * redirect: noRedirect           if set noRedirect will no redirect in the breadcrumb
 * name:'router-name'             the name is used by <keep-alive> (must set!!!)
 * meta : {
    roles: ['admin','editor']    control the page roles (you can set multiple roles)
    title: 'title'               the name show in sidebar and breadcrumb (recommend set)
    icon: 'svg-name'/'el-icon-x' the icon show in the sidebar
    breadcrumb: false            if set false, the item will hidden in breadcrumb(default is true)
    activeMenu: '/example/list'  if set path, the sidebar will highlight the path you set
  }
 */

/**
 * constantRoutes
 * a base page that does not have permission requirements
 * all roles can be accessed
 */
export const constantRoutes = [{
        path: '/login',
        component: () =>
            import ('@/views/login/index'),
        hidden: true
    },
    {
        path: '/404',
        component: () =>
            import ('@/views/404'),
        hidden: true
    },
    {
        path: '/',
        component: Layout,
        redirect: '/dashboard',
        children: [{
            path: 'dashboard',
            name: 'Dashboard',
            component: () =>
                import ('@/views/dashboard/index'),
            meta: { title: 'Dashboard', icon: 'dashboard' }
        }]
    },
    {
        path: '/print',
        component: Layout,
        children: [{
            path: 'index',
            name: 'Print',
            component: () =>
                import ('@/views/print/common/index'),
            meta: { title: '打印', icon: 'el-icon-printer' }
        }]
    },
    {
        path: '/zplPrint',
        component: Layout,
        children: [{
            path: 'index',
            name: 'zplPrint',
            component: () =>
                import ('@/views/print/zpl/index'),
            meta: { title: '斑马打印', icon: 'el-icon-takeaway-box' }
        }]
    },
    {
        path: '/product',
        component: Layout,
        redirect: '/product/commodity',
        name: 'product',
        meta: { title: '产品', icon: 'el-icon-s-help' },
        children: [{
                path: 'commodity',
                name: 'Commodity',
                component: () =>
                    import ('@/views/product/commodity/index'),
                meta: { title: '品项信息', icon: 'table' }
            },
            {
                path: 'template',
                name: 'Template',
                component: () =>
                    import ('@/views/product/template/index'),
                meta: { title: '配置模板', icon: 'el-icon-collection' }
            },
            {
                path: 'machine',
                name: 'Machine',
                component: () =>
                    import ('@/views/product/machine/index'),
                meta: { title: '机器管理', icon: 'el-icon-setting' }
            },
            {
                path: 'log',
                name: 'Log',
                component: () =>
                    import ('@/views/product/log/index'),
                meta: { title: '系统日志', icon: 'el-icon-wallet' }
            }
        ]
    }
]

/**
 * asyncRoutes
 * the routes that need to be dynamically loaded based on user roles
 */
export const asyncRoutes = [
    // 404 page must be placed at the end !!!
    { path: '*', redirect: '/404', hidden: true }
]

const createRouter = () => new Router({
    // mode: 'history', // require service support
    scrollBehavior: () => ({ y: 0 }),
    routes: constantRoutes
})

const router = createRouter()

// Detail see: https://github.com/vuejs/vue-router/issues/1234#issuecomment-357941465
export function resetRouter() {
    const newRouter = createRouter()
    router.matcher = newRouter.matcher // reset router
}

export default router