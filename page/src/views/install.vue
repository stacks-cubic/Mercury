<template>
  <div class="box">
    <div class="pa-10 border-bottom">
      <div>安装向导 - Mercury v{{version}}</div>
    </div>
    <a-result v-if="state" status="warning" title="系统已安装" sub-title="如需重新安装, 请删除 Mercury 根目录下的 Yaml 配置文件">
      <template #extra>
        <a-button key="console" type="primary">欢迎使用</a-button>
      </template>
    </a-result>
    <div class="form pa-10" v-else>
      <a-form :label-col="label">
        <a-form-item label="类型">
          <a-input v-model:value="form.dbDriver" />
        </a-form-item>
        <a-form-item label="连接语句">
          <a-input v-model:value="form.dbUrl" />
        </a-form-item>
        <a-form-item label="用户名">
          <a-input v-model:value="form.dbUser" />
        </a-form-item>
        <a-form-item label="密码">
          <a-input v-model:value="form.dbPassword" />
        </a-form-item>
        <a-form-item :wrapper-col="{ span: 14, offset: 4 }">
          <a-button type="primary">Create</a-button>
          <a-button style="margin-left: 10px">Cancel</a-button>
        </a-form-item>
      </a-form>
    </div>
  </div>
</template>

<script>
export default {
  name: 'Install',
  data: () => ({
    loading: true,
    state: false,
    version: '',
    label:{ style: { width: '150px' } },
    form:{
      dbUrl: '',
      dbDriver: '',
      dbUser: '',
      dbPassword: '',
      adminName: '',
      adminNickname: '',
      adminPassword: '',
      title: ''
    }
  }),
  methods: {
    init(){
      this.$api.system.init().then(res => {
        this.state = res.state;
        if(!res.state) this.version = res.message;
        else this.version = res.data.version;
        this.loading = false;
      }).catch(() => {
        this.loading = false;
        this.state = true;
      })
    }
  },
  mounted() {
    this.init();
  }
}
</script>
<style scoped>
.box{
  box-shadow: 0 2px 10px 0 rgba(0, 0, 0, .1);
  border: 1px solid #eeeeee;
  background-color: #fff;
  border-radius: 8px;
  margin: 20px auto;
  max-width: 900px;
}
</style>