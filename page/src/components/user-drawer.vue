<template>
  <template v-if="show">
    <a-drawer class="user-drawer no-select" v-model:visible="visible" :title="title" :width="320" placement="right">
      <template v-if="login">
        已登录
      </template>
      <template v-else>
        <div class="welcome">
          <div class="text-small text-gray">欢迎使用</div>
          <div class="title">Mercury</div>
        </div>
        <a-form :label-col="label" class="px-20 pt-10">
          <a-form-item label="用户名">
            <a-input :disabled="loading" v-model:value="form.name"/>
          </a-form-item>
          <a-form-item label="密码">
            <a-input :disabled="loading" v-model:value="form.password"/>
          </a-form-item>
          <a-form-item label="验证码">
            <a-input :disabled="loading" v-model:value="form.code"/>
          </a-form-item>
        </a-form>
        <a-button class="ml-10" type="text" :disabled="loading">忘记密码?</a-button>
        <div class="float-right px-20">
          <a-button class="mr-10" :disabled="loading">注册</a-button>
          <a-button type="primary" :loading="loading" @click="submit">登录</a-button>
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
    loading: false,
    login: false,
    title: '',
    label: {
      style: {width: '55px'}
    },
    form: {
      name: '',
      password: '',
      code: ''
    }
  }),
  methods: {
    open() {
      this.show = true;
      this.login = Boolean(localStorage.getItem('app:token'));
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
      this.loading = true;
      this.$api.user.login(this.form).then(res => setTimeout(() => {
        this.loading = false;
        if (res.state) {
          localStorage.setItem('app:token',res.message);
          this.$message.success('登录成功');
          this.login = true;
        } else {
          this.$message.warn(res.message ? res.message : '登录失败');
        }
      }, 500)).catch(() => {
        this.loading = false;
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