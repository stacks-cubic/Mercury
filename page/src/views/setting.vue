<template>
  <div>
    <div class="blur" :class="{show:blur}"></div>
    <div class="background" :style="{backgroundImage: 'url(\''+bg+'\')'}"></div>
    <div class="container">
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
      if(info) this.info = JSON.parse(info);
      let blur = localStorage.getItem('blur');
      if(blur) this.blur = blur;
    },
    switchBlur() {
      this.blur = !this.blur;
      localStorage.setItem('blur',this.blur);
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
  background: rgba(0, 0, 0, 0.2);
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
  max-width: 1000px;
  margin: 0 auto;
}

.title {
  padding: 40px 0 20px 0;
  text-align: center;
  font-size: 56px;
  color: #333;
}

.dark .title {
  color: #fff;
}

.search {
  padding: 0 10px 30px 10px;
  max-width: 700px;
  margin: 0 auto;
}

.mark-item {
  backdrop-filter: saturate(100%) blur(5px);
  background-color: rgba(255, 255, 255, 0.7);
  transition: all ease-in 0.3s;
  align-items: center;
  border-radius: 8px;
  cursor: pointer;
  display: flex;
}

.dark .mark-item {
  background-color: rgba(60, 60, 60, 0.6);
}

.mark-item:hover {
  background-color: rgba(255, 255, 255, 1);
}

.dark .mark-item:hover {
  background-color: rgba(80, 80, 80, 0.8);
}

.mark-icon {
  border-radius: 8px;
  padding: 5px;
  height: 50px;
  width: 50px;
}

.mark-info {
  padding: 5px;
}

.mark-item-small {
  transition: all ease-in 0.3s;
  border-radius: 8px;
  text-align: center;
  font-size: 12px;
  color: #f4f4f4;
}

.dark .mark-item-small {
  color: #f4f4f4;
}

.mark-item-small .mark-icon {
  backdrop-filter: saturate(100%) blur(5px);
  background: rgba(255, 255, 255, 0.7);
  transition: all ease-in 0.3s;
}

.dark .mark-item-small .mark-icon {
  background-color: rgba(60, 60, 60, 0.6);
}

.dark .mark-item-small:hover .mark-icon {
  background-color: rgba(80, 80, 80, 0.8);
}

.mark-item-small .mark-info {
  padding: 2px 1px;
  max-width: 90px;
  margin: 0 auto;
}

.mark-item-small:hover {
  background: #fff;
  color: #333;
}

.dark .mark-item-small:hover {
  background-color: rgba(80, 80, 80, 0.8);
  color: #f4f4f4;
}

.mark-group,
.tool-group {
  backdrop-filter: saturate(100%) blur(5px);
  background: rgba(255, 255, 255, 0.3);
  transition: all ease-in 0.3s;
  border-radius: 8px;
  margin-top: 10px;
}

.dark .mark-group,
.dark .tool-group {
  background-color: rgba(60, 60, 60, 0.6);
}

.mark-group:hover,
.tool-group:hover {
  background: rgba(255, 255, 255, 0.7);
}

.dark .mark-group:hover,
.dark .tool-group:hover {
  background-color: rgba(80, 80, 80, 0.8);
}

.tool-group .border-right {
  border-right: 1px solid #bdbdbd;
}

.dark .tool-group .border-right {
  border-right: 1px solid #f4f4f4;
}

.active {
  background: rgba(255, 255, 255, 0.7) !important;
}

.dark .active {
  background: rgba(80, 80, 80, 0.8) !important;
}

.header .nickname {
  display: block !important;
  max-width: 60px;
}
</style>
<style>
.mark-group .ant-collapse-header {
  padding: 10px 40px 10px 10px !important;
}

.container > .header .ant-btn-text {
  backdrop-filter: saturate(100%) blur(5px);
  background: rgba(255, 255, 255, 0.3);
  color: #5b5b5b;
}

.dark .container > .header .ant-btn-text {
  backdrop-filter: saturate(100%) blur(5px);
  background-color: rgba(60, 60, 60, 0.6);
  color: #f4f4f4;
}

.container > .header .ant-btn-text:hover, .ant-btn-text:focus {
  background: rgba(255, 255, 255, 0.7);
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
