<template>
  <template v-if="show">
    <a-drawer class="setting-drawer no-select" v-model:visible="visible" :width="250" :destroyOnClose="true"
              :closable="false" title="设置" :push="{distance:320}" placement="right" @close="close">
      <div class="flex align-center justify-center full-width border-bottom" style="height: 250px">系统信息卡片</div>
      <div class="setting-item flex align-center justify-between pa-10 border-bottom" @click="openSub(1)">
        <div class="flex align-center">
          <skin-outlined class="mr-5 icon" />
          <div>个性化</div>
        </div>
        <right-outlined/>
      </div>
      <div class="setting-item flex align-center justify-between pa-10 border-bottom" @click="openSub(2)">
        <div class="flex align-center">
          <search-outlined class="mr-5 icon" />
          <div>搜索引擎</div>
        </div>
        <right-outlined/>
      </div>
      <div class="setting-item flex align-center justify-between pa-10 border-bottom" @click="openSub(3)">
        <div class="flex align-center">
          <appstore-outlined class="mr-5 icon" />
          <div>分组管理</div>
        </div>
        <right-outlined/>
      </div>
      <div class="setting-item flex align-center justify-between pa-10 border-bottom" @click="openSub(4)">
        <div class="flex align-center">
          <code-outlined class="mr-5 icon" />
          <div>服务管理</div>
        </div>
        <right-outlined/>
      </div>
      <div class="setting-item flex align-center justify-between pa-10 border-bottom" @click="openSub(5)">
        <div class="flex align-center">
          <container-outlined class="mr-5 icon" />
          <div>书签管理</div>
        </div>
        <right-outlined/>
      </div>
      <div class="setting-item flex align-center justify-between pa-10 border-bottom" @click="openSub(6)">
        <div class="flex align-center">
          <user-outlined class="mr-5 icon" />
          <div>用户管理</div>
        </div>
        <right-outlined/>
      </div>
      <div class="setting-item flex align-center justify-between pa-10 border-bottom" @click="openSub(7)">
        <div class="flex align-center">
          <experiment-outlined class="mr-5 icon" />
          <div>高级</div>
        </div>
        <right-outlined/>
      </div>
      <a-drawer class="setting-drawer no-select" v-model:visible="subVisible" :width="subWidth" :destroyOnClose="true"
                :title="subTitle" placement="right">
        <template v-if="subId === 1">
          黑暗模式、主体(文本)颜色、背景图、背景自动轮换、服务卡片样式、字体大小、小工具显示与否、一句话
        </template>
        <template v-else-if="subId === 2">
          搜索引擎列表、设定默认搜索引擎、编辑搜索引擎配置
        </template>
        <template v-else-if="subId === 3">
          分组列表、编辑分组
        </template>
        <template v-else-if="subId === 4">
          服务列表、服务模板更新、编辑服务、编辑模板
        </template>
        <template v-else-if="subId === 5">
          书签列表、书签导入导出、编辑书签
        </template>
        <template v-else-if="subId === 6">
          用户列表、编辑用户
        </template>
        <template v-else-if="subId === 7">
          代理设置、内网穿透
        </template>
      </a-drawer>
    </a-drawer>
  </template>
</template>

<script>
import {SkinOutlined,SearchOutlined,AppstoreOutlined,CodeOutlined,ContainerOutlined,UserOutlined,ExperimentOutlined,RightOutlined} from '@ant-design/icons-vue';

export default {
  name: "setting-drawer",
  components: {SkinOutlined,SearchOutlined,AppstoreOutlined,CodeOutlined,ContainerOutlined,UserOutlined,ExperimentOutlined,RightOutlined},
  data: () => ({
    show: false,
    visible: false,
    subVisible: false,
    subId: 0,
    subWidth: 320,
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
      this.subId = id;
      this.subTitle = this.buildTitle(id);
      this.subVisible = true;
    },
    buildTitle(id){
      if(id === 1) return '个性化';
      else if(id === 2) return '搜索引擎';
      else if(id === 3) return '分组管理';
      else if(id === 4) return '服务管理';
      else if(id === 5) return '书签管理';
      else if(id === 6) return '用户管理';
      else if(id === 7) return '高级';
      return '';
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

.icon{
  font-size: 18px;
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