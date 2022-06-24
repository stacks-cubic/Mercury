<template>
  <div class="foot flex align-center justify-center full-width mb-10">
    <div class="mr-5">Mercury</div>
    <div>v{{ info.version }}</div>
    <div class="ml-5">· {{ info.ip }}</div>
  </div>
</template>

<script>
export default {
  name: "page-foot",
  props: {
    info: {
      type: Object,
      default: () => {
        return {
          version: '0.0.0',
          ip: '127.0.0.1'
        };
      }
    }
  }
}
</script>

<style scoped>
.foot {
  color: #5d5d5d;
}

.dark .foot {
  color: #999999;
}
</style>