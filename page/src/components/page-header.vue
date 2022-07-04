<template>
  <div class="pa-10 header flex align-center justify-between full-width">
    <a-button type="text" class="flex align-center px-10" @click="openEmit('switchInside')">
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
      <a-button type="text" class="mr-10" shape="circle" @click="openEmit('setting')" v-if="login && user.admin">
        <template #icon>
          <setting-outlined style="font-size: 18px"/>
        </template>
      </a-button>
      <a-button type="text" class="flex align-center pl-10 pr-10" :disabled="loading" @click="openEmit('user')"
                style="max-width: 105px">
        <user-outlined style="font-size: 18px"/>
        <div class="nickname line1 ml-5">{{ login ? user.nickname : '登录' }}</div>
      </a-button>
    </div>
  </div>
</template>

<script>
import {
  SettingOutlined,
  BgColorsOutlined,
  UserOutlined,
  GlobalOutlined,
  DeploymentUnitOutlined
} from '@ant-design/icons-vue';

export default {
  name: "page-header",
  components: {SettingOutlined, BgColorsOutlined, UserOutlined, GlobalOutlined, DeploymentUnitOutlined},
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
    login: false,
    loading: false,
    user: {
      admin: false,
      mfa: false,
      name: '',
      nickname: '登录中',
    }
  }),
  emits: ["switchBlur", "switchInside", "openDrawer"],
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
    openEmit(active) {
      this.$emit('openDrawer', active);
    },
    switchLogin(state) {
      this.login = state;
      if (state) this.getInfo();
    },
    getInfo() {
      this.loading = true;
      this.$api.user.getMyInfo().then(res => setTimeout(() => {
        this.loading = false;
        if (res.state) {
          res.data.mfa = res.data.mfa === 'true';
          this.user = res.data;
        }
      }, 500)).catch(() => {
        this.loading = false;
        this.$message.warn('网络异常, 无法连接到服务器');
      })
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
</style>
<style>
.user-drawer .ant-drawer-header {
  padding: 15px;
}
</style>