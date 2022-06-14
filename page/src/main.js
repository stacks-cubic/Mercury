import {createApp} from 'vue'
import App from './App.vue'
import { Button,Spin, message } from 'ant-design-vue';
import router from './plugin/router'
import store from './plugin/store'
import api from './plugin/api'
import './plugin/pwa'

const app = createApp(App);
app.use(store).use(router).use(Button).use(Spin).mount('#app');
app.config.globalProperties.$message = message;
app.config.globalProperties.$api = api;