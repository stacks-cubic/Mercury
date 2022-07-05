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
    },
    getMyInfo: () => {
        return request({
            url: '/user/my/info',
            method: 'GET'
        })
    },
    getList: () => {
        return request({
            url: '/user/list',
            method: 'GET'
        })
    },
    getInfo: uid => {
        return request({
            url: '/user/' + uid,
            method: 'GET'
        })
    },
    getMFAKey: () => {
        return request({
            url: '/user/mfa',
            method: 'GET'
        })
    },
    update: (uid, form) => {
        let data = new URLSearchParams();
        data.append('name', form.name)
        data.append('nickname', form.nickname)
        if(form.password) data.append('password', form.password)
        data.append('admin', form.admin)
        data.append('mfa', form.mfa)
        if(form.mfaKey) data.append('mfaKey', form.mfaKey)
        if(form.mfaCode) data.append('mfaCode', form.mfaCode)
        return request({
            url: '/user/' + uid,
            method: 'POST',
            data
        })
    },
}

const mark = {
    getGroupList: () => {
        return request({
            url: '/mark/group/list',
            method: 'GET'
        })
    },
    getGroupInfo: gid => {
        return request({
            url: '/mark/group/' + gid,
            method: 'GET'
        })
    },
    addGroup: form => {
        let data = new URLSearchParams();
        data.append('name', form.name)
        data.append('fold', form.fold)
        data.append('hide', form.hide)
        data.append('weight', form.weight)
        return request({
            url: '/mark/group/add',
            method: 'POST',
            data
        })
    },
    updateGroup: (gid, form) => {
        let data = new URLSearchParams();
        data.append('name', form.name)
        data.append('fold', form.fold)
        data.append('hide', form.hide)
        data.append('weight', form.weight)
        return request({
            url: '/mark/group/' + gid,
            method: 'POST',
            data
        })
    },
    removeGroup: gid => {
        return request({
            url: '/mark/group/' + gid,
            method: 'DELETE'
        })
    },
    getList: service => {
        return request({
            url: '/mark/list' + (service ? '?service=true' : ''),
            method: 'GET'
        })
    },
    getInfo: mid => {
        return request({
            url: '/mark/' + mid,
            method: 'GET'
        })
    },
    add: form => {
        let data = new URLSearchParams();
        data.append('gid', form.gid)
        data.append('title', form.title)
        data.append('icon', form.icon)
        data.append('describe', form.describe)
        data.append('weight', form.weight)
        data.append('service', form.service)
        data.append('era', form.era)
        data.append('ira', form.ira)
        data.append('hide', form.hide)
        return request({
            url: '/mark/add',
            method: 'POST',
            data
        })
    },
    update: (mid, form) => {
        let data = new URLSearchParams();
        data.append('gid', form.gid)
        data.append('title', form.title)
        data.append('describe', form.describe)
        data.append('weight', form.weight)
        data.append('era', form.era)
        data.append('ira', form.ira)
        data.append('hide', form.hide)
        return request({
            url: '/mark/' + mid,
            method: 'POST',
            data
        })
    },
    remove: mid => {
        return request({
            url: '/mark/' + mid,
            method: 'DELETE'
        })
    }
}

export default {
    host: process.env.VUE_APP_BASE_URL,
    system,
    setting,
    user,
    mark
}