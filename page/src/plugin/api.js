import request from './request'
import parser from 'ua-parser-js'
import md5 from 'js-md5'

// 设备工具
const device = {
    get: function () {
        let info = parser(navigator.userAgent);
        return "[网页端|" + info.engine.name + "] " + info.browser.name + " / " + this.uuid() + " ( " + info.os.name + " " + info.os.version + " )";
    }
}

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
        if(form.dbUrl) data.append('dbUrl', form.dbUrl);
        if(form.dbUser) data.append('dbUser', form.dbUser);
        if(form.dbPassword) data.append('dbPassword', form.dbPassword);
        return request({
            url: '/sys/init/db',
            method: 'POST',
            data
        })
    },
    install: form => {
        let data = new URLSearchParams();
        if(form.dbUrl) data.append('dbUrl', form.dbUrl);
        if(form.dbDriver) data.append('dbDriver', form.dbDriver);
        if(form.dbUser) data.append('dbUser', form.dbUser);
        if(form.dbPassword) data.append('dbPassword', form.dbPassword);
        if(form.adminName) data.append('adminName', form.adminName);
        if(form.adminNickname) data.append('adminNickname', form.adminNickname);
        if(form.adminPassword) data.append('adminPassword', md5(form.adminPassword));
        data.append('registerState', form.registerState);
        if(form.title) data.append('title', form.title);
        return request({
            url: '/sys/init',
            method: 'POST',
            data
        })
    }
}

export default {
    host: process.env.VUE_APP_BASE_URL,
    device,
    system
}