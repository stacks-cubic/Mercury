<template>
  <div class="loading" :class="{hide:!loading.show,dark}" v-if="loading.build">
    <a-spin size="large"/>
  </div>
  <div>
    <router-view v-slot="{ Component }" :class="{dark:dark}">
      <transition mode="out-in">
        <component :is="Component"/>
      </transition>
    </router-view>
  </div>
</template>

<script>
export default {
  name: "App",
  data: () => ({
    dark: false,
    loading: {
      show: true,
      build: true
    }
  }),
  methods: {
    init() {
      this.$api.system.init().then(res => setTimeout(() => {
        localStorage.setItem('app:info', JSON.stringify(res.data))
        if (res.state) {
          this.dark = res.data.theme === 'dark';
          this.closeLoading(400);
          if (this.$route.path === '/' || this.$route.path.startsWith("/error/")) this.$router.replace('/home');
        } else {
          if (res.code === 10001) {
            this.$router.push('/install');
            this.closeLoading(400);
          }
        }
      }, 200)).catch(err => {
        this.$router.replace('/error/' + (err === 'ERR_NETWORK' ? 'network' : 'unknown'));
        this.closeLoading(150);
      })
    },
    closeLoading(off) {
      if (!off) off = 10;
      setTimeout(() => {
        this.loading.show = false;
      }, off);
      setTimeout(() => {
        this.loading.build = false;
      }, 350 + off);
    }
  },
  mounted() {
    let info = localStorage.getItem('app:info');
    if(info) {
      info = JSON.parse(info);
      this.dark = info.theme === 'dark';
    }
    setTimeout(() => this.init(), 200);
  }
};
</script>
<style>
@import "style/app.css";
@import "style/app-dark.css";
</style>
