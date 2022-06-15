<template>
  <div class="box">
    <a-result :status="icon" :title="title" :sub-title="subTitle"/>
  </div>
</template>

<script>
export default {
  name: 'Error',
  data: () => ({
    icon: '',
    title: '',
    subTitle: ''
  }),
  methods: {
    init(type) {
      if (type === '403') {
        this.icon = '403';
        this.title = '权限不足';
        this.subTitle = '非常抱歉, 您没有访问权限';
      } else if (type === '404') {
        this.icon = '404';
        this.title = '页面不存在';
        this.subTitle = '未找到您想访问的页面, 请检查地址是否正确';
      } else if (type === '500') {
        this.icon = '500';
        this.title = '服务器内部错误';
        this.subTitle = '服务器无法处理您的请求, 服务器可能出现故障';
      } else if (type === 'network') {
        this.icon = 'error';
        this.title = '网络异常';
        this.subTitle = '无法与服务器建立连接, 请检查您的网络状态';
      } else {
        this.icon = 'error';
        this.title = '未知错误';
        this.subTitle = '检测到出现未知错误, 请联系我们上报错误';
      }
      document.getElementsByTagName('title')[0].text = this.title + ' - Mercury'
    }
  },
  mounted() {
    let type = this.$route.params.type;
    if (!type) type = 'unknown';
    this.init(type);
  }
}
</script>
<style scoped>
.box {
  padding-top: calc(50vh - 240px);
  max-width: 900px;
  margin: 0 auto;
}
</style>