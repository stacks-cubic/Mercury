<template>
  <template v-if="show">
    <a-drawer class="user-drawer no-select" v-model:visible="visible" :title="title" :width="320" placement="right">
      <a-spin :spinning="info.loading" v-if="login">
        <div>用户名: {{ info.name }}</div>
        <div>昵称: {{ info.nickname }}</div>
        <div>管理员: {{ info.admin }}</div>
        <div>双因素: {{ info.mfa }}</div>
      </a-spin>
      <template v-else>
        <div class="welcome">
          <div class="text-small text-gray">欢迎使用</div>
          <div class="title">Mercury</div>
        </div>
        <a-form :label-col="label" class="px-20 pt-10">
          <a-form-item label="用户名">
            <a-input :disabled="form.loading" v-model:value="form.name"/>
          </a-form-item>
          <a-form-item label="密码">
            <a-input :disabled="form.loading" v-model:value="form.password"/>
          </a-form-item>
          <a-form-item label="验证码">
            <a-input :disabled="form.loading" v-model:value="form.code"/>
          </a-form-item>
        </a-form>
        <a-button class="ml-10" type="text" :disabled="form.loading">忘记密码?</a-button>
        <div class="float-right px-20">
          <a-button class="mr-10" :disabled="form.loading">注册</a-button>
          <a-button type="primary" :loading="form.loading" @click="submit">登录</a-button>
        </div>
      </template>
    </a-drawer>
  </template>
</template>

<script>
export default {
  name: "user-drawer",
  data: () => ({
    show: false,
    visible: false,
    login: false,
    title: '',
    label: {
      style: {width: '55px'}
    },
    form: {
      loading: false,
      name: '',
      password: '',
      code: ''
    },
    info: {
      loading: false
    },
  }),
  emits: ["switchLogin"],
  methods: {
    open() {
      this.show = true;
      this.login = Boolean(localStorage.getItem('app:token'));
      if (this.login) this.getInfo();
      this.title = this.login ? '用户信息' : '登录账户';
      setTimeout(() => {
        this.visible = true;
      }, 100);
    },
    close() {
      setTimeout(() => {
        this.show = false;
      }, 350);
    },
    check() {
      let tips;
      if (!this.form.password) tips = '密码不能为空';
      if (!this.form.name) tips = '用户名不能为空';
      if (tips) {
        this.$message.warn(tips);
        return false;
      }
      return true;
    },
    submit() {
      if (!this.check()) return false;
      this.form.loading = true;
      this.$api.user.login(this.form).then(res => setTimeout(() => {
        this.form.loading = false;
        if (res.state) {
          this.$emit("switchLogin", true);
          localStorage.setItem('app:token', res.message);
          this.$message.success('登录成功');
          this.getInfo();
          this.login = true;
        } else {
          this.$message.warn(res.message ? res.message : '登录失败');
        }
      }, 500)).catch(() => {
        this.form.loading = false;
        this.$message.warn('网络异常, 无法连接到服务器');
      })
    },
    getInfo() {
      this.info.loading = true;
      this.$api.user.getMyInfo().then(res => setTimeout(() => {
        if (res.state) {
          res.data.mfa = res.data.mfa === 'true';
          this.info = res.data;
        }
        this.info.loading = false;
      }, 500)).catch(() => {
        this.info.loading = false;
        this.$message.warn('网络异常, 无法连接到服务器');
      })
    }
  }
}
</script>

<style scoped>
.welcome {
  width: max-content;
  line-height: 12px;
  margin: 40px auto;
}

.title {
  font-size: 42px;
  line-height: 32px;
}
</style>
<style>
.user-drawer .ant-drawer-body {
  padding: 0;
}

.user-drawer .ant-drawer-header {
  padding: 15px;
}
</style>