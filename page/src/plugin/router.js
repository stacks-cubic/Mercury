import {createRouter, createWebHistory} from 'vue-router'

const routes = [
    {
        path: '/',
        name: 'Loading',
        component: () => import(/* webpackChunkName: "base" */ '../views/home')
    },
    {
        path: '/install',
        name: 'Install',
        component: () => import(/* webpackChunkName: "install" */ '../views/install'),
        meta: {title: "安装向导"}
    },
    {
        path: '/home',
        name: 'Home',
        component: () => import(/* webpackChunkName: "base" */ '../views/home')
    },
    {
        path: '/error/:type',
        name: 'Error',
        component: () => import(/* webpackChunkName: "base" */ '../views/error'),
        meta: {title: "Error"}
    },
    {
        path: '/:pathMatch(.*)',
        redirect: '/error/404'
    }
]

const router = createRouter({
    history: createWebHistory(),
    routes
})

router.beforeEach((to, from, next) => {
    let info = localStorage.getItem('app:info');
    if(info) info = JSON.parse(info);
    let name = info ? info.name:'Mercury'
    document.title = (to.meta.title === undefined ? '' : to.meta.title + ' - ') + name
    next()
})

export default router
