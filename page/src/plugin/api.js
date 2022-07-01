import request from './request'
import md5 from 'js-md5'

// 初始化接口
const system = {
    init: () => {
        return request({
            url: '/sys/init',
            method: 'GET'
        })
    },
    reboot: () => {
        return request({
            url: '/sys/reboot',
            method: 'GET'
        })
    },
    testDb: form => {
        let data = new URLSearchParams();
        if (form.dbUrl) data.append('dbUrl', form.dbUrl);
        if (form.dbUser) data.append('dbUser', form.dbUser);
        if (form.dbPassword) data.append('dbPassword', form.dbPassword);
        return request({
            url: '/sys/init/db',
            method: 'POST',
            data
        })
    },
    install: form => {
        let data = new URLSearchParams();
        if (form.dbUrl) data.append('dbUrl', form.dbUrl);
        if (form.dbDriver) data.append('dbDriver', form.dbDriver);
        if (form.dbUser) data.append('dbUser', form.dbUser);
        if (form.dbPassword) data.append('dbPassword', form.dbPassword);
        if (form.adminName) data.append('adminName', form.adminName);
        if (form.adminNickname) data.append('adminNickname', form.adminNickname);
        if (form.adminPassword) data.append('adminPassword', md5(form.adminPassword));
        data.append('registerState', form.registerState);
        if (form.title) data.append('title', form.title);
        return request({
            url: '/sys/init',
            method: 'POST',
            data
        })
    }
}

// 设置接口
const setting = {
    update: (id, form) => {
        let data = new URLSearchParams();
        for (let key in form) {
            data.append(key, form[key]);
        }
        return request({
            url: '/setting/update/p' + id,
            method: 'POST',
            data
        })
    }
}

// 用户接口
const user = {
    login: form => {
        let data = new URLSearchParams();
        data.append('name', form.name)
        data.append('password', md5(form.password))
        if (form.code) data.append('code', form.code)
        return request({
            url: '/user/auth/login',
            method: 'POST',
            data
        })
    }
}

export default {
    host: process.env.VUE_APP_BASE_URL,
    system,
    setting,
    user
}