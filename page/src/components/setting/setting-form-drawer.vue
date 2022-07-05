<template>
  <template v-if="show">
    <a-drawer class="setting-form-drawer no-select" v-model:visible="visible" :width="320"
              :destroyOnClose="true"
              :title="mode === 'add' ? '新增' : '编辑'" placement="right">
      <template #extra>
        <div class="drawer-btn">
          <a-button type="primary" :disabled="loading || update" v-if="mode === 'edit'"
                    @click="remove" class="mr-5" danger>删除
          </a-button>
          <a-button type="primary" :disabled="loading" :loading="update" @click="save">保存</a-button>
        </div>
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
              <a-input v-model:value="form[2].name" :disabled="update" placeholder="请输入分组名称"/>
            </a-form-item>
            <a-form-item label="排序权重">
              <a-input v-model:value="form[2].weight" :disabled="update"/>
            </a-form-item>
            <a-form-item label="默认折叠">
              <a-switch v-model:checked="form[2].fold" :disabled="update"/>
            </a-form-item>
            <a-form-item label="隐藏">
              <a-select v-model:value="form[2].hide" :disabled="update" style="width: 130px">
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
            <a-form-item label="图标">
              <a-input v-model:value="form[3].icon"/>
            </a-form-item>
            <a-form-item label="名称">
              <a-input v-model:value="form[3].title" placeholder="请输入服务名称"/>
            </a-form-item>
            <a-form-item label="分组">
              <a-select v-model:value="form[3].gid" placeholder="请选择分组" style="width: 180px">
                <a-select-option v-for="(item,i) in group" :key="'service_group_'+i" :value="item.id">
                  {{ item.name }}
                </a-select-option>
              </a-select>
            </a-form-item>
            <a-form-item label="排序权重">
              <a-input v-model:value="form[3].weight"/>
            </a-form-item>
            <a-form-item label="描述信息">
              <a-textarea :autosize="autosize" v-model:value="form[3].describe" placeholder="请输入服务描述"/>
            </a-form-item>
            <a-form-item label="外网地址">
              <a-textarea :autosize="autosize" v-model:value="form[3].era" placeholder="请输入外网地址"/>
            </a-form-item>
            <a-form-item label="内网地址">
              <a-textarea :autosize="autosize" v-model:value="form[3].ira" placeholder="请输入内网地址"/>
            </a-form-item>
            <a-form-item label="隐藏">
              <a-select v-model:value="form[3].hide" style="width: 130px">
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
            <a-form-item label="图标">
              <a-input v-model:value="form[4].icon"/>
            </a-form-item>
            <a-form-item label="名称">
              <a-input v-model:value="form[4].title" placeholder="请输入书签名称"/>
            </a-form-item>
            <a-form-item label="分组">
              <a-select v-model:value="form[4].gid" placeholder="请选择分组" style="width: 200px">
                <a-select-option v-for="(item,i) in group" :key="'mark_group_'+i" :value="item.id">
                  {{ item.name }}
                </a-select-option>
              </a-select>
            </a-form-item>
            <a-form-item label="排序权重">
              <a-input v-model:value="form[4].weight"/>
            </a-form-item>
            <a-form-item label="描述信息">
              <a-textarea :autosize="autosize" v-model:value="form[4].describe" placeholder="请输入服务描述"/>
            </a-form-item>
            <a-form-item label="地址">
              <a-textarea :autosize="autosize" v-model:value="form[4].era" placeholder="请输入链接地址"/>
            </a-form-item>
            <a-form-item label="隐藏">
              <a-select v-model:value="form[4].hide" style="width: 130px">
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
              <a-input v-model:value="form[5].name" placeholder="请输入用户名"/>
            </a-form-item>
            <a-form-item label="昵称">
              <a-input v-model:value="form[5].nickname" placeholder="请输入昵称"/>
            </a-form-item>
            <a-form-item label="登录密码">
              <a-input v-model:value="form[5].password" :placeholder="mode==='edit' ? '留空保持密码不变':'请输入登录密码'"/>
            </a-form-item>
            <a-form-item label="安全令牌">
              <a-switch v-model:checked="form[5].mfa" @change="changeMFA"/>
              <div v-if="form[5].mfa && form[5].mfaKey" class="pt-10">
                <div class="flex align-center">
                  <vue-qr :text="'otpauth://totp/Mercury(@'+form[5].name+')?secret='+form[5].mfaKey"
                          backgroundColor="#f4f4f4" :size="80" :margin="5"></vue-qr>
                  <div class="ml-5 text-gray text-small">
                    <div>请使用</div>
                    <div>Google Authenticator</div>
                    <div>扫描此二维码</div>
                  </div>
                </div>
                <a-input v-model:value="form[5].mfaCode" placeholder="请输入6位纯数字验证码" class="mt-10"/>
              </div>
            </a-form-item>
            <a-form-item label="管理员">
              <a-switch v-model:checked="form[5].admin"/>
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
import vueQr from 'vue-qr/src/packages/vue-qr.vue'

export default {
  name: "setting-form-drawer",
  components: {vueQr},
  data: () => ({
    id: 0,
    type: 0,
    mode: 'add',
    show: false,
    update: false,
    visible: false,
    loading: false,
    label: {
      style: {width: '75px'}
    },
    old: {},
    form: [],
    group: [],
    autosize: {minRows: 3, maxRows: 6}
  }),
  emits: ["update"],
  methods: {
    open(type, mode, id) {
      this.type = type;
      this.mode = mode;
      this.id = id ? id : 0;
      this.clean();
      this.show = true;
      setTimeout(() => {
        this.visible = true;
        if (this.mode === 'edit') this.loadData();
        else if (this.type === 4 || this.type === 5) this.loadData();
      }, 100);
    },
    close() {
      setTimeout(() => {
        this.show = false;
      }, 350);
    },
    clean() {
      this.old = {};
      this.form = [
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
          service: true,
          icon: '',
          title: '',
          weight: 0,
          describe: '',
          era: '',
          ira: '',
          hide: '0'
        },
        {
          service: false,
          icon: '',
          title: '',
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
          mfa: false,
          mfaKey: '',
          mfaCode: ''
        },
        {
          title: '',
          host: '',
          port: 0,
          source: '',
          mode: false
        }
      ]
    },
    exit() {
      this.visible = false;
      this.close();
    },
    loadData() {
      this.loading = true;
      if (this.type === 3) {
        this.$api.mark.getGroupInfo(this.id).then(res => this.completeLoad(res, data => {
          data.hide = '' + data.hide;
          return data;
        })).catch(() => this.exit())
      } else if (this.type === 4 || this.type === 5) {
        this.$api.mark.getGroupList().then(res => {
          if (res.state) this.group = res.data;
          if (!this.id) {
            this.loading = false;
            return;
          }
          this.$api.mark.getInfo(this.id).then(r => this.completeLoad(r, data => {
            data.hide = '' + data.hide;
            return data;
          })).catch(() => this.exit())
        }).catch(() => this.exit())
      } else if (this.type === 6) {
        this.$api.user.getInfo(this.id).then(res => this.completeLoad(res, data => {
          data.mfa = data.mfa === 'true';
          return data;
        })).catch(() => this.exit())
      } else {
        setTimeout(() => {
          this.loading = false;
        }, 1500)
      }
    },
    save() {
      let id = this.id;
      let form = this.form[this.type - 1];
      if (this.type === 3) {
        this.update = true;
        if (this.mode === 'add') this.$api.mark.addGroup(form).then(res => this.complete(res, '保存', true)).catch(() => this.error())
        else this.$api.mark.updateGroup(id, form).then(res => this.complete(res, '保存', false)).catch(() => this.error())
      } else if (this.type === 4 || this.type === 5) {
        this.update = true;
        if (this.mode === 'add') this.$api.mark.add(form).then(res => this.complete(res, '保存', true)).catch(() => this.error())
        else this.$api.mark.update(id, form).then(res => this.complete(res, '保存', false)).catch(() => this.error())
      } else if (this.type === 6) {
        this.update = true;
        if (this.mode === 'add') this.$api.user.add(form).then(res => this.complete(res, '保存', true)).catch(() => this.error())
        else this.$api.user.update(id, form).then(res => this.complete(res, '保存', false)).catch(() => this.error())
      }
    },
    remove() {
      this.$modal.confirm({
        title: '确认要删除吗?',
        content: '此操作不可逆, 数据在删除后无法恢复, 确认删除?',
        okText: '删除',
        cancelText: '取消',
        onOk: () => {
          this.update = true;
          if (this.type === 3)
            this.$api.mark.removeGroup(this.id).then(res => this.complete(res, '删除', true)).catch(() => this.error())
          else if (this.type === 4 || this.type === 5)
            this.$api.mark.remove(this.id).then(res => this.complete(res, '删除', true)).catch(() => this.error())
        },
      })
    },
    changeMFA(state) {
      if (state && (!this.form[5].mfaKey || this.mode === 'add')) {
        if (this.old.mfa) return;
        this.$api.user.getMFAKey().then(res => {
          if (res.state) {
            this.form[5].mfaKey = res.message;
            if (this.mode === 'add') this.old.mfa = res.message;
          }
        });
      }
    },
    completeLoad(res, cleanData) {
      setTimeout(() => {
        if (res.state) {
          this.loading = false;
          if (cleanData !== undefined)
            res.data = cleanData(res.data);
          this.form[this.type - 1] = res.data;
          this.old = JSON.parse(JSON.stringify(res.data));
        } else {
          this.exit();
          this.$message.warn(res.message ? res.message : '加载失败');
        }
      }, 500)
    },
    complete(res, action, back) {
      setTimeout(() => {
        this.update = false;
        if (res.state) {
          this.$message.success(action + '成功');
          this.$emit("update", {});
          if (back) this.exit();
          else this.loadData();
        } else this.$message.warn(res.message ? res.message : action + '失败');
      }, 500)
    },
    error() {
      this.update = false;
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

.qrCode {
  background-color: #f4f4f4;
  margin: 10px 0;
  height: 110px;
  padding: 5px;
  width: 110px;
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