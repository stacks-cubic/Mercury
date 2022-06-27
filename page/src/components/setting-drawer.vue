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
          <div class="px-10 pt-10" style="margin-top: -20px">背景</div>
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
          <div class="px-10 pt-10" style="margin-top: -20px">卡片</div>
          <a-form :label-col="label" class="pa-10 pb-0">
            <a-form-item label="工具卡片">
              <a-select v-model:value="form[0].toolsStyle" style="width: 170px">
                <a-select-option value="">不显示工具卡片</a-select-option>
                <a-select-option value="long">长条卡片(图标在左)</a-select-option>
                <a-select-option value="small">方形卡片(图标在上)</a-select-option>
              </a-select>
            </a-form-item>
            <a-form-item label="服务卡片">
              <a-select v-model:value="form[0].serviceStyle" style="width: 170px">
                <a-select-option value="long">长条卡片(图标在左)</a-select-option>
                <a-select-option value="small">方形卡片(图标在上)</a-select-option>
              </a-select>
            </a-form-item>
            <a-form-item label="书签卡片">
              <a-select v-model:value="form[0].markStyle" style="width: 170px">
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
          <a-form :label-col="label" class="border-bottom pa-10">
            <a-form-item label="名称">
              <a-input v-model:value="form[1].name"/>
            </a-form-item>
            <a-form-item label="默认引擎">
              <a-switch v-model:checked="form[1].defaultEngine"/>
            </a-form-item>
            <a-form-item label="查询网址">
              <a-input v-model:value="form[1].queryUrl"/>
            </a-form-item>
            <a-form-item label="自动补齐">
              <a-switch v-model:checked="form[1].autoFill"/>
            </a-form-item>
            <a-form-item label="补齐接口">
              <a-input v-model:value="form[1].fillUrl"/>
            </a-form-item>
            <a-form-item label="搜索历史">
              <a-switch v-model:checked="form[1].searchHistory"/>
            </a-form-item>
          </a-form>
          <div class="pa-10" style="margin-top: -20px">引擎列表</div>
          <div>
            这是列表。。。
          </div>
        </template>
        <template v-else-if="subId === 3">
          <a-form :label-col="label" class="border-bottom pa-10">
            <a-form-item label="名称">
              <a-input v-model:value="form[2].name"/>
            </a-form-item>
            <a-form-item label="排序权重">
              <a-input v-model:value="form[2].weight"/>
            </a-form-item>
            <a-form-item label="默认折叠">
              <a-switch v-model:checked="form[2].fold"/>
            </a-form-item>
            <a-form-item label="隐藏">
              <a-select v-model:value="form[2].hide" style="width: 100px">
                <a-select-option value="0">不隐藏</a-select-option>
                <a-select-option value="1">隐藏</a-select-option>
                <a-select-option value="2">对用户隐藏</a-select-option>
                <a-select-option value="3">对游客隐藏</a-select-option>
                <a-select-option value="4">对非添加人隐藏</a-select-option>
              </a-select>
            </a-form-item>
          </a-form>
          <div class="pa-10" style="margin-top: -20px">分组列表</div>
          <div>
            这是列表。。。
          </div>
        </template>
        <template v-else-if="subId === 4">
          <a-form :label-col="label" class="border-bottom pa-10">
            <a-form-item label="匹配标识">
              <a-input v-model:value="form[3].ssid"/>
            </a-form-item>
            <a-form-item label="名称">
              <a-input v-model:value="form[3].title"/>
            </a-form-item>
            <a-form-item label="分组">
              <a-input v-model:value="form[3].gid"/>
            </a-form-item>
            <a-form-item label="排序权重">
              <a-input v-model:value="form[3].weight"/>
            </a-form-item>
            <a-form-item label="描述信息">
              <a-input v-model:value="form[3].describe"/>
            </a-form-item>
            <a-form-item label="外网地址">
              <a-input v-model:value="form[3].era"/>
            </a-form-item>
            <a-form-item label="内网地址">
              <a-input v-model:value="form[3].ira"/>
            </a-form-item>
            <a-form-item label="隐藏">
              <a-select v-model:value="form[3].hide" style="width: 100px">
                <a-select-option value="0">不隐藏</a-select-option>
                <a-select-option value="1">隐藏</a-select-option>
                <a-select-option value="2">对用户隐藏</a-select-option>
                <a-select-option value="3">对游客隐藏</a-select-option>
                <a-select-option value="4">对非添加人隐藏</a-select-option>
              </a-select>
            </a-form-item>
          </a-form>
          <div class="pa-10" style="margin-top: -20px">服务列表</div>
          <div>
            这是列表。。。
          </div>
          <!-- TODO 更新服务配置 -->
        </template>
        <template v-else-if="subId === 5">
          <a-form :label-col="label" class="border-bottom pa-10">
            <a-form-item label="名称">
              <a-input v-model:value="form[4].title"/>
            </a-form-item>
            <a-form-item label="分组">
              <a-input v-model:value="form[4].gid"/>
            </a-form-item>
            <a-form-item label="排序权重">
              <a-input v-model:value="form[4].weight"/>
            </a-form-item>
            <a-form-item label="描述信息">
              <a-input v-model:value="form[4].describe"/>
            </a-form-item>
            <a-form-item label="地址">
              <a-input v-model:value="form[4].era"/>
            </a-form-item>
            <a-form-item label="隐藏">
              <a-select v-model:value="form[4].hide" style="width: 100px">
                <a-select-option value="0">不隐藏</a-select-option>
                <a-select-option value="1">隐藏</a-select-option>
                <a-select-option value="2">对用户隐藏</a-select-option>
                <a-select-option value="3">对游客隐藏</a-select-option>
                <a-select-option value="4">对非添加人隐藏</a-select-option>
              </a-select>
            </a-form-item>
          </a-form>
          <div class="pa-10" style="margin-top: -20px">书签列表</div>
          <div>
            这是列表。。。
          </div>
          <!-- TODO 书签导入导出 -->
        </template>
        <template v-else-if="subId === 6">
          <a-form :label-col="label" class="border-bottom pa-10">
            <a-form-item label="用户名">
              <a-input v-model:value="form[5].name"/>
            </a-form-item>
            <a-form-item label="昵称">
              <a-input v-model:value="form[5].nickname"/>
            </a-form-item>
            <a-form-item label="登录密码">
              <a-input v-model:value="form[5].password"/>
            </a-form-item>
            <a-form-item label="管理员">
              <a-switch v-model:checked="form[5].admin"/>
            </a-form-item>
            <a-form-item label="安全令牌">
              <a-switch v-model:checked="form[5].mfa"/>
            </a-form-item>
          </a-form>
          <div class="pa-10" style="margin-top: -20px">用户列表</div>
          <div>
            这是列表。。。
          </div>
        </template>
        <template v-else-if="subId === 7">
          <a-form :label-col="label" class="border-bottom pa-10">
            <a-form-item label="内网穿透">
              <a-switch v-model:checked="form[6].pierce"/>
            </a-form-item>
            <a-form-item label="服务商">
              <a-select v-model:value="form[6].service" style="width: 100px">
                <a-select-option value="zerotier">ZeroTier</a-select-option>
              </a-select>
            </a-form-item>
            <a-form-item label="网络标识">
              <a-input v-model:value="form[6].networkId"/>
            </a-form-item>
          </a-form>
          <div class="pa-10" style="margin-top: -20px">内网代理</div>
          <a-form :label-col="label" class="border-bottom pa-10">
            <a-form-item label="名称">
              <a-input v-model:value="form[6].title"/>
            </a-form-item>
            <a-form-item label="主机地址">
              <a-input v-model:value="form[6].host"/>
            </a-form-item>
            <a-form-item label="通讯端口">
              <a-input v-model:value="form[6].port"/>
            </a-form-item>
            <a-form-item label="访问来源">
              <a-input v-model:value="form[6].source"/>
            </a-form-item>
            <a-form-item label="穿透代理">
              <a-switch v-model:checked="form[6].mode"/>
            </a-form-item>
          </a-form>
          <div class="pa-10" style="margin-top: -20px">代理列表</div>
          <div>
            这是列表。。。
          </div>
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
        color: '',
        textSize: '14',
        switchImage: false,
        autoImage: false,
        image: '',
        imageSource: 'wallhaven',
        toolsStyle: 'long',
        serviceStyle: 'long',
        markStyle: 'long',
        phrase: false,
        phraseApi: 'hitokoto'
      },
      {
        name: '',
        defaultEngine: false,
        queryUrl: '',
        searchTips: false,
        autoFill: false,
        fillUrl: '',
        searchHistory: false
      },
      {
        name: '',
        weight: 0,
        fold: false,
        hide: '0'
      },
      {
        ssid: '',
        title: '',
        gid: 0,
        weight: 0,
        describe: '',
        era: '',
        ira: '',
        hide: '0'
      },
      {
        title: '',
        gid: 0,
        weight: 0,
        describe: '',
        era: '',
        hide: '0'
      },
      {
        name: '',
        nickname: '',
        password: '',
        admin: false,
        mfa: ''
      },
      {
        pierce: false,
        service: 'zerotier',
        networkId: '',
        title: '',
        host: '',
        port: 0,
        source: '',
        mode: false
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

.drawer-btn {
  position: absolute;
  right: 10px;
  top: 10px;
}

.setting-item-drawer .ant-form-item {
  margin-bottom: 10px;
}
</style>
<style>
.setting-drawer .ant-drawer-body,
.setting-item-drawer .ant-drawer-body {
  padding: 0;
}

.setting-drawer .ant-drawer-header,
.setting-item-drawer .ant-drawer-header {
  padding: 15px;
}
</style>