<template>
  <div class="box">
    <div class="text-center">
      <div>哦! 你不应该看到这个页面的.</div>
      <div>这是默认的占位页面.</div>
      <div>该死的, 路由肯定出问题了!</div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'Loading',
  data: () => ({}),
  methods: {}
}
</script>
<style scoped>
.box{
  justify-content: center;
  align-items: center;
  display: flex;
  height: 100vh;
  width: 100vw;
}
</style>