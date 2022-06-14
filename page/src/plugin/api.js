import request from './request'
import parser from 'ua-parser-js'
// import md5 from 'js-md5'

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
    }
}

export default {
    device,
    system
}