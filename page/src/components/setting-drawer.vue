<template>
  <template v-if="show">
    <a-drawer class="setting-drawer no-select" v-model:visible="visible" :width="250" :destroyOnClose="true"
              :closable="false" title="设置" placement="right" @close="close">
      <div class="flex align-center justify-center full-width border-bottom" style="height: 250px">系统信息卡片</div>
      <div class="setting-item flex align-center justify-between pa-10 border-bottom" @click="openSub(1)">
        <div>个性化</div>
        <right-outlined/>
      </div>
      <div class="setting-item flex align-center justify-between pa-10 border-bottom" @click="openSub(2)">
        <div>搜索引擎</div>
        <right-outlined/>
      </div>
      <div class="setting-item flex align-center justify-between pa-10 border-bottom" @click="openSub(3)">
        <div>服务管理</div>
        <right-outlined/>
      </div>
      <div class="setting-item flex align-center justify-between pa-10 border-bottom" @click="openSub(4)">
        <div>书签管理</div>
        <right-outlined/>
      </div>
      <div class="setting-item flex align-center justify-between pa-10 border-bottom" @click="openSub(5)">
        <div>用户管理</div>
        <right-outlined/>
      </div>
      <div class="setting-item flex align-center justify-between pa-10 border-bottom" @click="openSub(6)">
        <div>高级</div>
        <right-outlined/>
      </div>
      <a-drawer class="setting-drawer no-select" v-model:visible="subVisible" :width="320" :destroyOnClose="true"
                :title="subTitle" placement="right">
        123
      </a-drawer>
    </a-drawer>
  </template>
</template>

<script>
import {RightOutlined} from '@ant-design/icons-vue';

export default {
  name: "setting-drawer",
  components: {RightOutlined},
  data: () => ({
    show: false,
    visible: false,
    subVisible: false,
    subId: 0,
    subTitle: ''
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
    openSub(id){
      let title = ''
      if(id === 1) title = '个性化';
      else if(id === 2) title = '搜索引擎';
      else if(id === 3) title = '服务管理';
      else if(id === 4) title = '书签管理';
      else if(id === 5) title = '用户管理';
      else if(id === 6) title = '高级';
      this.subId = id;
      this.subTitle = title;
      this.subVisible = true;
    }
  }
}
</script>

<style scoped>

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

.setting-drawer .ant-drawer-header{
  padding: 15px;
}
</style>