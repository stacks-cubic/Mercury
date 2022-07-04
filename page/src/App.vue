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
        if (res.state) {
          this.$store.commit('updateTheme',res.data);
          res.data.dark = res.data.dark === 'true';
          res.data.switchImage = res.data.switchImage === 'true';
          res.data.autoImage = res.data.autoImage === 'true';
          res.data.phrase = res.data.phrase === 'true';
          this.closeLoading(400);
          if (this.$route.path === '/' || this.$route.path.startsWith("/error/")) this.$router.replace('/home');
        } else {
          if (res.code === 10001) {
            this.$router.push('/install');
            this.closeLoading(400);
          }
        }
        localStorage.setItem('app:info', JSON.stringify(res.data))
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
      this.dark = info.dark;
    }
    setTimeout(() => this.init(), 200);
  }
};
</script>
<style>
@import "style/app.css";
@import "style/app-dark.css";
</style>
