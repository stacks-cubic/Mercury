import {createApp} from 'vue'
import App from './App.vue'
import { Button,Spin,Result,Form,Input,Steps,Select,Popconfirm, Modal, message } from 'ant-design-vue';
import router from './plugin/router'
import store from './plugin/store'
import api from './plugin/api'
import './plugin/pwa'

const app = createApp(App);
app.use(store).use(router).use(Button).use(Spin).use(Result).use(Form).use(Input).use(Steps).use(Select).use(Popconfirm).mount('#app');
app.config.globalProperties.$message = message;
app.config.globalProperties.$modal = Modal;
app.config.globalProperties.$api = api;