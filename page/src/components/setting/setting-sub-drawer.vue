<template>
  <template v-if="show">
    <a-drawer class="setting-item-drawer no-select" v-model:visible="visible" :width="320"
              :destroyOnClose="true" :push="{distance:150}" :title="sub.title" placement="right">
      <template #extra>
        <div class="drawer-btn">
          <a-button class="mr-5" :disabled="loading" v-if="sub.id !== 1" @click="openForm('add')">添加</a-button>
          <a-button type="primary" :disabled="loading" v-if="sub.id === 1 || sub.id === 7">保存</a-button>
        </div>
      </template>
      <a-spin size="large" :spinning="loading">
        <template v-if="sub.id === 1">
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
        <template v-else-if="sub.id === 2">
          <div class="list">
            <div class="form-item flex align-center justify-between pa-10 border-bottom" @click="openForm('edit')">
              <div>
                <div class="flex align-center">
                  <strong class="mr-5">百度</strong>
                  <a-tag color="#108ee9" class="tag mr-5">默认</a-tag>
                  <a-tag color="#87d068" class="tag">史</a-tag>
                </div>
                <div class="text-small text-gray">https://www.baidu.com/?q=${keyword}</div>
              </div>
              <right-outlined/>
            </div>
            <div class="form-item flex align-center justify-between pa-10 border-bottom" @click="openForm('edit')">
              <div>
                <div class="flex align-center">
                  <strong class="mr-5">谷歌</strong>
                  <a-tag color="#2db7f5" class="tag mr-5">补</a-tag>
                  <a-tag color="#87d068" class="tag">史</a-tag>
                </div>
                <div class="text-small text-gray">https://www.google.com/?q=${keyword}</div>
              </div>
              <right-outlined/>
            </div>
          </div>
        </template>
        <template v-else-if="sub.id === 3">
          <div class="list">
            <div class="form-item flex align-center justify-between pa-10 border-bottom" @click="openForm('edit')">
              <div>
                <div class="flex align-center">
                  <strong class="mr-5">分组1</strong>
                  <a-tag color="#f50" class="tag">折叠</a-tag>
                </div>
                <div class="text-small text-gray">仅添加人可见</div>
              </div>
              <right-outlined/>
            </div>
            <div class="form-item flex align-center justify-between pa-10 border-bottom" @click="openForm('edit')">
              <div>
                <div class="flex align-center">
                  <strong class="mr-5">分组2</strong>
                </div>
                <div class="text-small text-gray">隐藏</div>
              </div>
              <right-outlined/>
            </div>
          </div>
        </template>
        <template v-else-if="sub.id === 4">
          <div class="list">
            <div class="form-item flex align-center justify-between pa-10 border-bottom" @click="openForm('edit')">
              <div>
                <div>
                  <strong>服务1</strong>
                </div>
                <div class="text-small text-gray line1">敏捷的狐狸跳过懒惰的乌龟</div>
                <div class="text-small text-gray mt-5">分组: 分组1</div>
                <div class="text-small text-gray line1">外网: http://home.skay.ink/service1</div>
                <div class="text-small text-gray line1">内网: http://192.168.1.12:49875</div>
                <div class="text-small text-gray">隐藏: 对游客隐藏</div>
              </div>
              <right-outlined/>
            </div>
          </div>
          <!-- TODO 更新服务配置 -->
        </template>
        <template v-else-if="sub.id === 5">
          <div class="list">
            <div class="form-item flex align-center justify-between pa-10 border-bottom" @click="openForm('edit')">
              <div>
                <div>
                  <strong>书签1</strong>
                </div>
                <div class="text-small text-gray line1">http://home.skay.ink/mark1</div>
                <div class="text-small text-gray mt-5">分组: 分组1</div>
                <div class="text-small text-gray line1">描述: 敏捷的狐狸跳过懒惰的乌龟</div>
                <div class="text-small text-gray">隐藏: 对游客隐藏</div>
              </div>
              <right-outlined/>
            </div>
          </div>
          <!-- TODO 书签导入导出 -->
        </template>
        <template v-else-if="sub.id === 6">
          <div class="list">
            <div class="form-item flex align-center justify-between pa-10 border-bottom" @click="openForm('edit')">
              <div>
                <div>
                  <strong class="mr-5">Skay</strong>
                  <a-tag color="#999" class="tag mr-5">@skay</a-tag>
                  <a-tag color="#108ee9" class="tag">管理员</a-tag>
                </div>
                <div class="text-small text-gray">3天前来过</div>
              </div>
              <right-outlined/>
            </div>
          </div>
        </template>
        <template v-else-if="sub.id === 7">
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
          <div class="pa-10 pb-0" style="margin-top: -20px">代理列表</div>
          <div class="mini-list">
            <div class="form-item flex align-center justify-between pa-10 border-bottom" @click="openForm('edit')">
              <div>
                <div>
                  <strong class="mr-5">内网代理1</strong>
                  <a-tag color="#2db7f5" class="tag">穿透</a-tag>
                </div>
                <div class="text-small text-gray">入: 127.0.0.1:9812</div>
                <div class="text-small text-gray">出: 192.168.192.100:7982</div>
              </div>
              <right-outlined/>
            </div>
          </div>
        </template>
      </a-spin>
      <setting-form-drawer ref="form"/>
    </a-drawer>
  </template>
</template>
<script>
import SettingFormDrawer from "@/components/setting/setting-form-drawer";
import {RightOutlined} from '@ant-design/icons-vue';

export default {
  name: "setting-sub-drawer",
  components: {RightOutlined, SettingFormDrawer},
  data: () => ({
    show: false,
    visible: false,
    loading: false,
    label: {
      style: {width: '75px'}
    },
    sub: {
      id: 0,
      title: '',
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
    open(id) {
      this.sub = {
        id,
        title: this.buildTitle(id)
      };
      this.show = true;
      setTimeout(() => {
        this.visible = true;
        this.loadData();
      }, 100);
    },
    openForm(mode) {
      this.$refs.form.open(this.sub.id, mode)
    },
    close() {
      setTimeout(() => {
        this.show = false;
      }, 350);
    },
    loadData() {
      this.loading = true;
      // 加载数据
      setTimeout(() => {
        this.loading = false;
      }, 1500)
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
    buildHide(type) {
      if (type === 0) return '不隐藏';
      else if (type === 1) return '隐藏';
      else if (type === 2) return '对用户隐藏';
      else if (type === 3) return '对游客隐藏';
      else if (type === 4) return '仅添加人可见';
      return '未知'
    }
  }
}
</script>

<style scoped>
.setting-item-drawer {
  transform: translateZ(0);
}

.drawer-btn {
  position: absolute;
  right: 10px;
  top: 10px;
}

.setting-item-drawer .ant-form-item {
  margin-bottom: 10px;
}

.list {
  min-height: calc(100vh - 55px);
}

.mini-list {
  min-height: calc(100vh - 225px);
}

.form-item {
  cursor: pointer;
}

.form-item:hover {
  background-color: #f9f9f9;
}

.form-item:active {
  background-color: #f4f4f4;
}

.form-item .tag {
  line-height: 15px;
  padding: 0 2px;
}
</style>
<style>
.setting-item-drawer .ant-drawer-body {
  padding: 0;
}

.setting-item-drawer .ant-drawer-header {
  padding: 15px;
}
</style>