<template>
  <div>
    <div class="blur" :class="{show:blur}"></div>
    <div class="background" :style="{backgroundImage: 'url(\''+bg+'\')'}"></div>
    <div class="container no-select">
      <page-header ref="header" :blur="blur" :inside="inside" @switchBlur="switchBlur" @switchInside="switchInside"
                   @openDrawer="openDrawer"/>
      <div class="box">
        <div class="title" :style="{color: info.color}">{{ info.name }}</div>
        <search/>
        <div class="mark-box px-10 pb-20">
          <!-- 标准大小 服务卡片 -->
          <a-row :gutter="[10,10]" class="justify-center pb-20" v-if="type===1">
            <template v-for="(item,i) in mark.server" :key="'ms_'+i">
              <a-col :xs="12" :md="6" :lg="4">
                <mark-item :item="item"/>
              </a-col>
            </template>
          </a-row>
          <!-- 小型 服务卡片 -->
          <a-row :gutter="[10,10]" class="justify-center pb-20" v-else-if="type===2">
            <template v-for="(item,i) in mark.server" :key="'ms_'+i">
              <a-col :xs="12" :md="6" :lg="4">
                <mark-item-small :item="item"/>
              </a-col>
            </template>
          </a-row>
          <tool-group/>
          <template v-for="(item,i) in mark.group" :key="'mg_'+i">
            <mark-group :group="item"/>
          </template>
        </div>
      </div>
      <page-foot :info="info"/>
    </div>
    <setting-drawer ref="setting"/>
    <user-drawer ref="user" @switchLogin="switchLogin"/>
  </div>
</template>

<script>
import MarkItem from "@/components/mark/mark-item";
import MarkItemSmall from "@/components/mark/mark-item-small";
import MarkGroup from "@/components/mark/mark-group";
import PageHeader from "@/components/page-header";
import PageFoot from "@/components/page-foot";
import Search from "@/components/search";
import ToolGroup from "@/components/tool-group";
import SettingDrawer from "@/components/setting/setting-drawer";
import UserDrawer from "@/components/user-drawer";

export default {
  name: 'Home',
  components: {
    UserDrawer,
    SettingDrawer,
    ToolGroup,
    Search,
    PageFoot,
    PageHeader,
    MarkGroup,
    MarkItemSmall,
    MarkItem
  },
  data: () => ({
    blur: false,
    login: false,
    inside: false,
    type: 1,
    info: {
      name: 'Mercury',
      version: '0.0.0',
      color: '#fff'
    },
    mark: {
      server: [
        {
          icon: 'favicon.ico',
          title: '内网服务标题',
          describe: '内网服务信息'
        },
        {
          icon: 'https://www.antdv.com/assets/logo.1ef800a8.svg',
          title: '内网服务标题'
        },
        {
          icon: 'favicon.ico',
          title: '内网服务标题内网服务标题'
        }
      ],
      group: [
        {
          title: '书签组1',
          fold: false
        },
        {
          title: '书签组2',
          fold: true
        },
        {
          title: '书签组3',
          fold: true
        }
      ]
    },
    open: ['1'],
    bg: '/sys/bg'
  }),
  methods: {
    init() {
      this.bg = this.$api.host + this.bg;
      let info = localStorage.getItem('app:info');
      if (info) {
        this.info = JSON.parse(info);
        this.inside = this.info.inside;
      }
      let blur = localStorage.getItem('app:blur') === 'true';
      if (blur) this.blur = blur;

      this.login = Boolean(localStorage.getItem('app:token'));
      this.switchLogin(this.login);
    },
    switchBlur(state) {
      this.blur = state;
    },
    switchInside() {
      this.inside = !this.inside;
    },
    openDrawer(action) {
      if (action === 'user') this.$refs.user.open();
      else this.$refs.setting.open();
    },
    switchLogin(state) {
      this.$refs.header.switchLogin(state);
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
  backdrop-filter: saturate(100%) blur(15px);
  background-color: rgba(0, 0, 0, 0.2);
  transition: all ease-in 0.3s;
  transform: translateZ(0);
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
  min-height: calc(100vh - 90px);
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
</style>
<style>
.mark-group .ant-collapse-header {
  padding: 10px 40px 10px 10px !important;
}

.container > .header .ant-btn-text {
  backdrop-filter: saturate(100%) blur(5px);
  background-color: rgba(255, 255, 255, 0.3);
  transform: translateZ(0);
  color: #5b5b5b;
}

.dark .container > .header .ant-btn-text {
  backdrop-filter: saturate(100%) blur(5px);
  background-color: rgba(60, 60, 60, 0.6);
  transform: translateZ(0);
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
