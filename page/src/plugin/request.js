import axios from 'axios'
import {message} from 'ant-design-vue';

axios.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded';

const request = axios.create({
    baseURL: process.env.VUE_APP_BASE_URL,
    timeout: 10000
})

request.interceptors.request.use(
    config => {
        let token = localStorage.getItem("app:token");
        if (token) {
            config.headers['Authorization'] = 'Bearer '+token;
        }
        return config
    },
    error => {
        return Promise.reject(error)
    }
)

request.interceptors.response.use(
    response => {
        let code = parseInt(response.data.code);
        if (code >= 10010 && code <= 10012) {
            localStorage.removeItem("app:token");
            message.error({
                content: response.data.message,
                key: 'LOGIN'
            });
            return false;
        } else return response.data
    }, err => {
        if (err.code === 'ERR_NETWORK')
            message.error({
                content: '无法连接服务器',
                key: 'ERR_NETWORK'
            });
        else
            message.error({
                content: '请求出错',
                key: 'ERR_REQUEST'
            });
        throw err.code;
    }
)

export default request
