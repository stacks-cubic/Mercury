<template>
  <template v-if="show">
    <a-drawer class="setting-drawer no-select" v-model:visible="visible" :width="250" :destroyOnClose="true"
              :closable="false" title="设置" :push="{distance:320}" placement="right" @close="close">
      <div class="flex align-center justify-center full-width border-bottom" style="height: 250px">系统信息卡片</div>
      <div class="setting-item flex align-center justify-between pa-10 border-bottom" @click="openSub(1)">
        <div class="flex align-center">
          <skin-outlined class="mr-5 icon"/>
          <div>个性化</div>
        </div>
        <right-outlined/>
      </div>
      <div class="setting-item flex align-center justify-between pa-10 border-bottom" @click="openSub(2)">
        <div class="flex align-center">
          <search-outlined class="mr-5 icon"/>
          <div>搜索引擎</div>
        </div>
        <right-outlined/>
      </div>
      <div class="setting-item flex align-center justify-between pa-10 border-bottom" @click="openSub(3)">
        <div class="flex align-center">
          <appstore-outlined class="mr-5 icon"/>
          <div>分组管理</div>
        </div>
        <right-outlined/>
      </div>
      <div class="setting-item flex align-center justify-between pa-10 border-bottom" @click="openSub(4)">
        <div class="flex align-center">
          <code-outlined class="mr-5 icon"/>
          <div>服务管理</div>
        </div>
        <right-outlined/>
      </div>
      <div class="setting-item flex align-center justify-between pa-10 border-bottom" @click="openSub(5)">
        <div class="flex align-center">
          <container-outlined class="mr-5 icon"/>
          <div>书签管理</div>
        </div>
        <right-outlined/>
      </div>
      <div class="setting-item flex align-center justify-between pa-10 border-bottom" @click="openSub(6)">
        <div class="flex align-center">
          <user-outlined class="mr-5 icon"/>
          <div>用户管理</div>
        </div>
        <right-outlined/>
      </div>
      <div class="setting-item flex align-center justify-between pa-10 border-bottom" @click="openSub(7)">
        <div class="flex align-center">
          <experiment-outlined class="mr-5 icon"/>
          <div>高级</div>
        </div>
        <right-outlined/>
      </div>
      <a-drawer class="setting-item-drawer no-select" v-model:visible="subVisible" :width="subWidth"
                :destroyOnClose="true"
                :title="subTitle" placement="right">
        <template #extra>
          <a-button type="primary" class="drawer-btn">保存</a-button>
        </template>
        <template v-if="subId === 1">
          <a-form :label-col="label" class="border-bottom pa-10">
            <a-form-item label="系统名称">
              <a-input v-model:value="form[0].name"/>
            </a-form-item>
            <a-form-item label="黑暗模式">
              <a-switch v-model:checked="form[0].dark"/>
            </a-form-item>
            <a-form-item label="主体颜色">
              <a-input v-model:value="form[0].color"/>
            </a-form-item>
            <a-form-item label="字体大小">
              <a-select v-model:value="form[0].textSize" style="width: 100px">
                <a-select-option value="12" style="font-size: 12px">小(12px)</a-select-option>
                <a-select-option value="14" style="font-size: 14px">中(14px)</a-select-option>
                <a-select-option value="16" style="font-size: 16px">大(16px)</a-select-option>
              </a-select>
            </a-form-item>
          </a-form>
          <div class="pa-10" style="margin-top: -20px">背景</div>
          <a-form :label-col="label" class="border-bottom pa-10">
            <a-form-item label="背景轮换">
              <a-switch v-model:checked="form[0].switchImage"/>
            </a-form-item>
            <a-form-item label="自动获取">
              <a-switch v-model:checked="form[0].autoImage"/>
            </a-form-item>
            <a-form-item label="背景图" v-if="!form[0].autoImage||!form[0].autoImage">
              <a-input v-model:value="form[0].image"/>
            </a-form-item>
            <a-form-item label="图片来源" v-else>
              <a-select v-model:value="form[0].imageSource" style="width: 170px">
                <a-select-option value="wallhaven">Wallhaven</a-select-option>
                <a-select-option value="bing">Bing</a-select-option>
              </a-select>
            </a-form-item>
          </a-form>
          <div class="pa-10" style="margin-top: -20px">卡片</div>
          <a-form :label-col="label" class="pa-10 pb-0">
            <a-form-item label="工具卡片">
              <a-select v-model:value="form[0].toolsSize" style="width: 170px">
                <a-select-option value="">不显示工具卡片</a-select-option>
                <a-select-option value="long">长条卡片(图标在左)</a-select-option>
                <a-select-option value="small">方形卡片(图标在上)</a-select-option>
              </a-select>
            </a-form-item>
            <a-form-item label="服务卡片">
              <a-select v-model:value="form[0].serviceSize" style="width: 170px">
                <a-select-option value="long">长条卡片(图标在左)</a-select-option>
                <a-select-option value="small">方形卡片(图标在上)</a-select-option>
              </a-select>
            </a-form-item>
            <a-form-item label="书签卡片">
              <a-select v-model:value="form[0].markSize" style="width: 170px">
                <a-select-option value="long">长条卡片(图标在左)</a-select-option>
                <a-select-option value="small">方形卡片(图标在上)</a-select-option>
              </a-select>
            </a-form-item>
            <a-form-item label="短语卡片">
              <a-switch v-model:checked="form[0].phrase"/>
            </a-form-item>
            <a-form-item label="短语接口">
              <a-select v-model:value="form[0].phraseApi" style="width: 100px">
                <a-select-option value="hitokoto">一言</a-select-option>
              </a-select>
            </a-form-item>
          </a-form>
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
import {
  SkinOutlined,
  SearchOutlined,
  AppstoreOutlined,
  CodeOutlined,
  ContainerOutlined,
  UserOutlined,
  ExperimentOutlined,
  RightOutlined
} from '@ant-design/icons-vue';

export default {
  name: "setting-drawer",
  components: {
    SkinOutlined,
    SearchOutlined,
    AppstoreOutlined,
    CodeOutlined,
    ContainerOutlined,
    UserOutlined,
    ExperimentOutlined,
    RightOutlined
  },
  data: () => ({
    show: false,
    visible: false,
    subVisible: false,
    subId: 0,
    subWidth: 320,
    subTitle: '',
    label: {
      style: {width: '75px'}
    },
    form: [
      {
        name: '',
        dark: false,
        image: '',
        switchImage: false,
        autoImage: false,
        imageSource: 'wallhaven',
        toolsSize: 'long',
        serviceSize: 'long',
        markSize: 'long',
        textSize: '14',
        phrase: false,
        phraseApi: 'hitokoto'
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
      this.subId = id;
      this.subTitle = this.buildTitle(id);
      this.subVisible = true;
    },
    buildTitle(id) {
      if (id === 1) return '个性化';
      else if (id === 2) return '搜索引擎';
      else if (id === 3) return '分组管理';
      else if (id === 4) return '服务管理';
      else if (id === 5) return '书签管理';
      else if (id === 6) return '用户管理';
      else if (id === 7) return '高级';
      return '';
    }
  }
}
</script>

<style scoped>
.setting-drawer，
.setting-item-drawer {
  transform: translateZ(0);
}

.setting-item {
  cursor: pointer;
}

.setting-item:hover {
  background-color: #f9f9f9;
}

.setting-item:active {
  background-color: #f4f4f4;
}

.icon {
  font-size: 18px;
}

.drawer-btn{
  position: absolute;
  right: 10px;
  top: 10px;
}
</style>
<style>
.setting-drawer .ant-drawer-body,
.setting-item-drawer .ant-drawer-body{
  padding: 0;
}

.setting-drawer .ant-drawer-header,
.setting-item-drawer .ant-drawer-header {
  padding: 15px;
}
</style>