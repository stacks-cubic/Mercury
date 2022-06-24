<template>
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
      <a-button type="text" class="mr-10" shape="circle" @click="openDrawer('setting')">
        <template #icon>
          <setting-outlined style="font-size: 18px"/>
        </template>
      </a-button>
      <a-button type="text" class="flex align-center pl-10 pr-10" @click="openDrawer('user')">
        <user-outlined style="font-size: 18px"/>
        <div class="nickname line1 ml-5">登录</div>
      </a-button>
    </div>
  </div>
  <a-drawer class="setting-drawer no-select" v-model:visible="drawer.setting" :width="250" :destroyOnClose="true"
            :closable="false" title="设置" placement="right">
    <div class="flex align-center justify-center full-width border-bottom" style="height: 250px">系统信息卡片</div>
    <div class="setting-item flex align-center justify-between pa-10 border-bottom">
      <div>个性化</div>
      <right-outlined/>
    </div>
    <div class="setting-item flex align-center justify-between pa-10 border-bottom">
      <div>搜索引擎</div>
      <right-outlined/>
    </div>
    <div class="setting-item flex align-center justify-between pa-10 border-bottom">
      <div>服务管理</div>
      <right-outlined/>
    </div>
    <div class="setting-item flex align-center justify-between pa-10 border-bottom">
      <div>书签管理</div>
      <right-outlined/>
    </div>
    <div class="setting-item flex align-center justify-between pa-10 border-bottom">
      <div>用户管理</div>
      <right-outlined/>
    </div>
    <div class="setting-item flex align-center justify-between pa-10 border-bottom">
      <div>高级</div>
      <right-outlined/>
    </div>
  </a-drawer>
  <a-drawer class="user-drawer no-select" v-model:visible="drawer.user" title="登录账户" :width="320" placement="right">
    <p>登录账户</p>
    <p>Some contents...</p>
    <p>Some contents...</p>
  </a-drawer>
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
  name: "page-header",
  components: {RightOutlined,SettingOutlined, BgColorsOutlined, UserOutlined, GlobalOutlined, DeploymentUnitOutlined},
  props: {
    blur: {
      type: Boolean,
      default: false
    },
    inside: {
      type: Boolean,
      default: false
    }
  },
  data: () => ({
    drawer: {
      setting: false,
      user: false
    }
  }),
  emits: ["switchBlur", "switchInside"],
  methods: {
    switchBlur() {
      if (this.blur) {
        this.$emit("switchBlur", false);
        localStorage.removeItem('app:blur');
      } else {
        this.$modal.confirm({
          title: '确认要启用背景虚化吗?',
          content: '背景虚化需对图片进行高斯模糊, 此功能对动画性能与资源占用有一定的影响, 确认启用吗?',
          okText: '启用背景虚化',
          cancelText: '取消',
          onOk: () => {
            this.$emit("switchBlur", true);
            localStorage.setItem('app:blur', 'true');
          }
        });
      }
    },
    switchInside() {
      this.$emit("switchInside", {});
    },
    openDrawer(type) {
      this.drawer[type] = true;
    }
  }
}
</script>

<style scoped>
.header .nickname {
  display: block !important;
  max-width: 60px;
}

.header .active {
  background-color: rgba(255, 255, 255, 0.7) !important;
}

.dark .header .active {
  background-color: rgba(80, 80, 80, 0.8) !important;
}

.setting-drawer{
  transform: translateZ(0);
}

.setting-item{
  cursor: pointer;
}

.setting-item:hover{
  background-color: #f9f9f9;
}

.setting-item:active{
  background-color: #f4f4f4;
}
</style>
<style>
.setting-drawer .ant-drawer-body {
  padding: 0;
}

.setting-drawer .ant-drawer-header,
.user-drawer .ant-drawer-header{
  padding: 15px;
}
</style>