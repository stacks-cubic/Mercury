<template>
  <div class="loading" :class="{hide:!loading.show}" v-if="loading.build">
    <a-spin size="large"/>
  </div>
  <router-view v-slot="{ Component }">
    <transition mode="out-in">
      <component :is="Component"/>
    </transition>
  </router-view>
</template>

<script>
export default {
  name: "App",
  data: () => ({
    loading: {
      show: true,
      build: true
    }
  }),
  methods: {
    init() {
      this.$api.system.init().then(res => setTimeout(() => {
        if (res.state) {
          this.closeLoading(10);
          console.log(res);
        } else {
          if (res.code === 10001) {
            this.$router.push('/install');
            this.closeLoading(150);
          }
        }
      }, 300)).catch(err => {
        console.log(err)
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
    setTimeout(() => this.init(), 200);
  }
};
</script>
<style>
@import "style/app.css";
</style>
