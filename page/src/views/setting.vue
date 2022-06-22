<template>
  <div>
    <div class="blur" :class="{show:blur}"></div>
    <div class="background" :style="{backgroundImage: 'url(\''+bg+'\')'}"></div>
    <div class="container no-select">
      <div class="pa-10 header float-right">
        <div class="flex align-center">
          <a-button type="text" class="mr-10" :class="{active:blur}" shape="circle" @click="switchBlur">
            <template #icon>
              <bg-colors-outlined style="font-size: 18px"/>
            </template>
          </a-button>
          <a-button type="text" class="mr-10" shape="circle" @click="$router.push('/home')">
            <template #icon>
              <logout-outlined style="font-size: 18px"/>
            </template>
          </a-button>
          <a-button type="text" class="flex align-center pl-10 pr-10">
            <user-outlined style="font-size: 18px"/>
            <div class="nickname line1 ml-5">Skay Zhang</div>
          </a-button>
        </div>
      </div>
      <div class="box px-10">
        <div class="title" :style="{color: info.color}">系统设置</div>
        <div class="card pa-10">
          系统信息。。
        </div>
        <div class="card">
          <div class="card-header border-bottom pa-10">基础设置</div>
          <div class="pa-10">
            <div>搜索引擎</div>
            <div>背景图片</div>
            <div>文字颜色</div>
            <div>黑暗模式</div>
            <div>代理设置</div>
          </div>
        </div>
        <div class="card">
          <div class="card-header border-bottom pa-10">用户管理</div>
          <div class="pa-10">
            内容
          </div>
        </div>
        <div class="card">
          <div class="card-header border-bottom pa-10">内网服务</div>
          <div class="pa-10">
            内容
          </div>
        </div>
        <div class="card">
          <div class="card-header border-bottom pa-10">书签管理</div>
          <div class="pa-10">
            内容
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import {
  BgColorsOutlined,
  LogoutOutlined,
  UserOutlined
} from '@ant-design/icons-vue';

export default {
  name: 'Setting',
  components: {BgColorsOutlined, LogoutOutlined, UserOutlined},
  data: () => ({
    blur: false,
    info: {
      name: 'Mercury',
      version: '0.0.0',
      color: '#fff'
    },
    bg: '/sys/bg'
  }),
  methods: {
    init() {
      this.bg = this.$api.host + this.bg;
      let info = localStorage.getItem('app:info');
      if (info) this.info = JSON.parse(info);
      let blur = localStorage.getItem('app:blur');
      if (blur) this.blur = blur;
    },
    switchBlur() {
      this.blur = !this.blur;
      if (this.blur) localStorage.setItem('app:blur', 'true');
      else localStorage.removeItem('app:blur')
    },
  },
  mounted() {
    this.init();
  }
}
</script>
<style scoped>
.background,
.blur {
  position: fixed;
  height: 100vh;
  width: 100vw;
  z-index: 1;
}

.background {
  background: no-repeat center center;
  background-size: cover;
}

.blur {
  backdrop-filter: saturate(100%) blur(5px);
  background-color: rgba(0, 0, 0, 0.2);
  transition: all ease-in 0.3s;
  opacity: 0;
  z-index: 2;
}

.show {
  opacity: 1;
}

.container {
  position: absolute;
  width: 100vw;
  z-index: 9;
}

.box {
  padding-top: 60px;
  max-width: 1000px;
  margin: 0 auto;
}

.title {
  padding: 40px 0 20px 0;
  text-align: center;
  font-size: 52px;
  color: #333;
}

.active {
  background-color: rgba(255, 255, 255, 0.7) !important;
}

.dark .active {
  background-color: rgba(80, 80, 80, 0.8) !important;
}

.header .nickname {
  display: block !important;
  max-width: 60px;
}

.card {
  box-shadow: 0 2px 10px 0 rgba(0, 0, 0, .1);
  transition: all ease-in 0.3s;
  background-color: #fff;
  margin-bottom: 20px;
  border-radius: 8px;
  opacity: .8;
}

.card:hover {
  box-shadow: none;
  opacity: 1;
}
</style>
<style>
.container > .header .ant-btn-text {
  backdrop-filter: saturate(100%) blur(5px);
  background-color: rgba(255, 255, 255, 0.3);
  color: #5b5b5b;
}

.dark .container > .header .ant-btn-text {
  backdrop-filter: saturate(100%) blur(5px);
  background-color: rgba(60, 60, 60, 0.6);
  color: #f4f4f4;
}

.container > .header .ant-btn-text:hover, .ant-btn-text:focus {
  background-color: rgba(255, 255, 255, 0.7);
  color: #333;
}

.dark .container > .header .ant-btn-text:hover, .dark .ant-btn-text:focus {
  background-color: rgba(80, 80, 80, 0.8);
  color: #f4f4f4;
}

.dark .ant-collapse-header,
.dark .ant-collapse-content-box {
  color: #f4f4f4 !important;
}
</style>
