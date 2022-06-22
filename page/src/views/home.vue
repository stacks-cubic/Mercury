<template>
  <div>
    <div class="blur" :class="{show:blur}"></div>
    <div class="background" :style="{backgroundImage: 'url(\''+bg+'\')'}"></div>
    <div class="container no-select">
      <div class="pa-10 header flex align-center justify-between full-width">
        <a-button type="text" class="flex align-center px-10" @click="switchInside">
          <global-outlined style="font-size: 18px" v-if="inside"/>
          <deployment-unit-outlined style="font-size: 18px" v-else/>
          <div class="ml-5">{{ inside ? '切换外网' : '切换内网' }}</div>
        </a-button>
        <div class="flex align-center">
          <a-button type="text" class="mr-10" :class="{active:blur}" shape="circle" @click="switchBlur">
            <template #icon>
              <bg-colors-outlined style="font-size: 18px"/>
            </template>
          </a-button>
          <a-button type="text" class="mr-10" shape="circle" @click="$router.push('/setting')">
            <template #icon>
              <setting-outlined style="font-size: 18px"/>
            </template>
          </a-button>
          <a-button type="text" class="flex align-center pl-10 pr-10">
            <user-outlined style="font-size: 18px"/>
            <div class="nickname line1 ml-5">Skay Zhang</div>
          </a-button>
        </div>
      </div>
      <div class="box">
        <div class="title" :style="{color: info.color}">{{ info.name }}</div>
        <div class="search">
          <a-input-group compact>
            <a-input v-model:value="search.keyword" style="width: calc(100% - 60px)">
              <template #addonBefore>
                <a-select v-model:value="search.type" style="width: 70px">
                  <a-select-option value="baidu">百度</a-select-option>
                  <a-select-option value="google">谷歌</a-select-option>
                  <a-select-option value="bing">必应</a-select-option>
                </a-select>
              </template>
            </a-input>
            <a-button type="primary" style="width: 60px">搜索</a-button>
          </a-input-group>
        </div>
        <div class="mark-box px-10 pb-20">
          <a-row :gutter="[10,10]" class="justify-center pb-20" v-if="type===1">
            <a-col :xs="12" :md="6" :lg="4">
              <div class="mark-item">
                <img src="../assets/logo.png" class="mark-icon"/>
                <div class="mark-info">
                  <div class="line1">内网服务标题</div>
                  <div class="text-small text-gray">内网服务信息</div>
                </div>
              </div>
            </a-col>
            <a-col :xs="12" :md="6" :lg="4">
              <div class="mark-item">
                <img src="https://www.antdv.com/assets/logo.1ef800a8.svg" class="mark-icon"/>
                <div class="mark-info">
                  <div class="line1">内网服务标题</div>
                </div>
              </div>
            </a-col>
            <a-col :xs="12" :md="6" :lg="4">
              <div class="mark-item">
                <img src="../assets/logo.png" class="mark-icon"/>
                <div class="mark-info">
                  <div class="line1">内网服务标题内网服务标题</div>
                </div>
              </div>
            </a-col>
          </a-row>
          <a-row :gutter="[10,10]" class="justify-center pb-20" v-if="type===2">
            <a-col :xs="6" :md="3" :lg="2">
              <div class="mark-item-small">
                <img src="../assets/logo.png" class="mark-icon"/>
                <div class="mark-info line1">内网服务标题</div>
              </div>
            </a-col>
            <a-col :xs="6" :md="3" :lg="2">
              <div class="mark-item-small">
                <img src="https://www.antdv.com/assets/logo.1ef800a8.svg" class="mark-icon"/>
                <div class="mark-info line1">内网服务标题</div>
              </div>
            </a-col>
            <a-col :xs="6" :md="3" :lg="2">
              <div class="mark-item-small">
                <img src="../assets/logo.png" class="mark-icon"/>
                <div class="mark-info line1">内网服务标题内网服务标题</div>
              </div>
            </a-col>
          </a-row>
          <div class="tool-group flex align-center">
            <div class="pa-10 border-right">
              <div>网页小工具</div>
              <div class="text-small flex align-center" style="color: #5e5e5e;cursor: pointer">
                <span>查看全部</span>
                <right-outlined style="fontSize: 10px;margin: -1px 3px 0 0"/>
              </div>
            </div>
          </div>
          <div class="mark-group">
            <a-collapse v-model:active-key="open[0]" expand-icon-position="right" ghost>
              <a-collapse-panel key="1" header="书签组1">
                <div>书签组合1</div>
                <template #extra>
                  <setting-outlined/>
                </template>
              </a-collapse-panel>
            </a-collapse>
          </div>
          <div class="mark-group">
            <a-collapse v-model:active-key="open[1]" expand-icon-position="right" ghost>
              <a-collapse-panel key="1" header="书签组2">
                <div>书签组合2</div>
                <template #extra>
                  <setting-outlined/>
                </template>
              </a-collapse-panel>
            </a-collapse>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import {
  RightOutlined,
  SettingOutlined,
  BgColorsOutlined,
  UserOutlined,
  GlobalOutlined,
  DeploymentUnitOutlined
} from '@ant-design/icons-vue';

export default {
  name: 'Home',
  components: {RightOutlined, SettingOutlined, BgColorsOutlined, UserOutlined, GlobalOutlined, DeploymentUnitOutlined},
  data: () => ({
    inside: false,
    blur: false,
    type: 1,
    info: {
      name: 'Mercury',
      version: '0.0.0',
      color: '#fff'
    },
    search: {
      type: 'google',
      keyword: ''
    },
    open: ['1'],
    bg: '/sys/bg'
  }),
  methods: {
    init() {
      this.bg = this.$api.host + this.bg;
      let info = localStorage.getItem('app:info');
      if(info) this.info = JSON.parse(info);
      let blur = localStorage.getItem('app:blur');
      if(blur) this.blur = blur;
    },
    switchBlur() {
      this.blur = !this.blur;
      if(this.blur) localStorage.setItem('app:blur','true');
      else localStorage.removeItem('app:blur')
    },
    switchInside() {
      this.inside = !this.inside;
    }
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
  background-color: rgba(255, 255, 255, 0.7);
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
  background-color: #fff;
  color: #333;
}

.dark .mark-item-small:hover {
  background-color: rgba(80, 80, 80, 0.8);
  color: #f4f4f4;
}

.mark-group,
.tool-group {
  backdrop-filter: saturate(100%) blur(5px);
  background-color: rgba(255, 255, 255, 0.5);
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
  background-color: rgba(255, 255, 255, 0.7);
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
  background-color: rgba(255, 255, 255, 0.7) !important;
}

.dark .active {
  background-color: rgba(80, 80, 80, 0.8) !important;
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
