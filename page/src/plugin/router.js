import {createRouter, createWebHistory} from 'vue-router'

const routes = [
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
    }
]

const router = createRouter({
    history: createWebHistory(),
    routes
})

router.beforeEach((to, from, next) => {
    document.title = (to.meta.title === undefined ? '' : to.meta.title + ' - ') + 'Mercury'
    next()
})

export default router
