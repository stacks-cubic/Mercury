<template>
  <template v-if="show">
    <a-drawer class="setting-drawer no-select" v-model:visible="visible" :width="250" :destroyOnClose="true"
              :closable="false" title="设置" :push="{distance:320}" placement="right" @close="close">
      <template #extra>
        <div class="drawer-btn">
          <a-button type="primary" :loading="reboot" danger @click="rebootSystem">重启</a-button>
        </div>
      </template>
      <div class="flex align-center justify-center full-width border-bottom" style="height: 250px">系统信息卡片</div>
      <template v-for="(item,i) in list" :key="'setting_'+i">
        <setting-drawer-item :item="item" @open="openSub">
          <skin-outlined class="icon" v-if="item.id === 1"/>
          <search-outlined class="icon" v-else-if="item.id === 2"/>
          <appstore-outlined class="icon" v-else-if="item.id === 3"/>
          <code-outlined class="icon" v-else-if="item.id === 4"/>
          <container-outlined class="icon" v-else-if="item.id === 5"/>
          <user-outlined class="icon" v-else-if="item.id === 6"/>
          <compass-outlined class="icon" v-else-if="item.id === 7"/>
        </setting-drawer-item>
      </template>
      <setting-sub-drawer ref="sub"/>
    </a-drawer>
  </template>
</template>

<script>
import {
  SkinOutlined,
  SearchOutlined,
  AppstoreOutlined,
  CodeOutlined,
  ContainerOutlined,
  UserOutlined,
  CompassOutlined
} from '@ant-design/icons-vue';
import SettingSubDrawer from "@/components/setting/setting-sub-drawer";
import SettingDrawerItem from "@/components/setting/setting-drawer-item";

export default {
  name: "setting-drawer",
  components: {
    SettingDrawerItem,
    SettingSubDrawer,
    SkinOutlined,
    SearchOutlined,
    AppstoreOutlined,
    CodeOutlined,
    ContainerOutlined,
    UserOutlined,
    CompassOutlined
  },
  data: () => ({
    show: false,
    visible: false,
    reboot: false,
    list: [
      {
        id: 1,
        title: '个性化'
      },
      {
        id: 2,
        title: '搜索引擎'
      },
      {
        id: 3,
        title: '分组管理'
      },
      {
        id: 4,
        title: '服务管理'
      },
      {
        id: 5,
        title: '书签管理'
      },
      {
        id: 6,
        title: '用户管理'
      },
      {
        id: 7,
        title: '代理'
      }
    ]
  }),
  methods: {
    open() {
      this.show = true;
      setTimeout(() => {
        this.visible = true;
      }, 100);
    },
    close() {
      setTimeout(() => {
        this.show = false;
      }, 350);
    },
    openSub(id) {
      this.$refs.sub.open(id);
    },
    buildTitle(id) {
      if (id === 1) return '个性化';
      else if (id === 2) return '搜索引擎';
      else if (id === 3) return '分组管理';
      else if (id === 4) return '服务管理';
      else if (id === 5) return '书签管理';
      else if (id === 6) return '用户管理';
      else if (id === 7) return '代理';
      return '';
    },
    rebootSystem() {
      this.$modal.confirm({
        title: '确认要重启系统吗?',
        content: '此操作只会重启 "Mercury" 不会影响操作系统, 重启预计需要花费3～8秒, 确认重启?',
        okText: '重启',
        cancelText: '取消',
        onOk: () => {
          this.reboot = true;
          this.$api.system.reboot().then(res => {
            if (res.state) {
              this.$message.success('重启成功, 请稍等...');
              setTimeout(() => {
                this.reboot = false;
              }, 3000);
            }else{
              this.$message.success(res.message ? res.message:'重启失败');
              this.reboot = false;
            }
          }).catch(() => {
            this.reboot = false;
            this.$message.warn('网络异常, 无法连接到服务器');
          })
        }
      });
    }
  }
}
</script>

<style scoped>
.setting-drawer {
  transform: translateZ(0);
}

.icon {
  font-size: 18px;
}

.drawer-btn {
  position: absolute;
  right: 10px;
  top: 10px;
}
</style>
<style>
.setting-drawer .ant-drawer-body {
  padding: 0;
}

.setting-drawer .ant-drawer-header {
  padding: 15px;
}
</style>