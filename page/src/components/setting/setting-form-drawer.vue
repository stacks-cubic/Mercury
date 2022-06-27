<template>
  <template v-if="show">
    <a-drawer class="setting-form-drawer no-select" v-model:visible="visible" :width="320"
              :destroyOnClose="true"
              :title="mode === 'add' ? '新增' : '编辑'" placement="right">
      <template #extra>
        <a-button type="primary" class="drawer-btn" :disabled="loading">保存</a-button>
      </template>
      <a-spin size="large" :spinning="loading">
        <template v-if="type === 2">
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
        </template>
        <template v-else-if="type === 3">
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
                <a-select-option value="4">仅添加人可见</a-select-option>
              </a-select>
            </a-form-item>
          </a-form>
        </template>
        <template v-else-if="type === 4">
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
                <a-select-option value="4">仅添加人可见</a-select-option>
              </a-select>
            </a-form-item>
          </a-form>
          <!-- TODO 更新服务配置 -->
        </template>
        <template v-else-if="type === 5">
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
                <a-select-option value="4">仅添加人可见</a-select-option>
              </a-select>
            </a-form-item>
          </a-form>
          <!-- TODO 书签导入导出 -->
        </template>
        <template v-else-if="type === 6">
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
        </template>
        <template v-else-if="type === 7">
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
        </template>
      </a-spin>
    </a-drawer>
  </template>
</template>

<script>
export default {
  name: "setting-form-drawer",
  data: () => ({
    id: 0,
    type: 0,
    mode: 'add',
    show: false,
    visible: false,
    loading: false,
    label: {
      style: {width: '75px'}
    },
    form: [
      {},
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
        title: '',
        host: '',
        port: 0,
        source: '',
        mode: false
      }
    ]
  }),
  methods: {
    open(type, mode, title, id) {
      this.type = type;
      this.mode = mode;
      this.id = id ? id : 0;
      this.show = true;
      setTimeout(() => {
        this.visible = true;
        this.loadData();
      }, 100);
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
    }
  }
}
</script>

<style scoped>
.setting-form-drawer {
  transform: translateZ(0);
}

.drawer-btn {
  position: absolute;
  right: 10px;
  top: 10px;
}

.setting-form-drawer .ant-form-item {
  margin-bottom: 10px;
}
</style>
<style>
.setting-form-drawer .ant-drawer-body {
  padding: 0;
}

.setting-form-drawer .ant-drawer-header {
  padding: 15px;
}
</style>