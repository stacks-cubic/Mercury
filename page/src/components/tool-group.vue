<template>
  <div class="tool-group flex align-center">
    <div class="pa-10 border-right">
      <div>网页小工具</div>
      <div class="text-small flex align-center" style="color: #5e5e5e;cursor: pointer">
        <span>查看全部</span>
        <right-outlined style="fontSize: 10px;margin: -1px 3px 0 0"/>
      </div>
    </div>
  </div>
</template>

<script>
import {RightOutlined} from '@ant-design/icons-vue';

export default {
  name: "tool-group",
  components: {RightOutlined}
}
</script>

<style scoped>
.tool-group {
  backdrop-filter: saturate(100%) blur(5px);
  background-color: rgba(255, 255, 255, 0.5);
  transition: all ease-in 0.3s;
  margin-top: 10px;
}

.dark .tool-group {
  background-color: rgba(60, 60, 60, 0.6);
}

.tool-group:hover {
  background-color: rgba(255, 255, 255, 0.7);
}

.dark .tool-group:hover {
  background-color: rgba(80, 80, 80, 0.8);
}

.tool-group .border-right {
  border-right: 1px solid #bdbdbd;
}

.dark .tool-group .border-right {
  border-right: 1px solid #f4f4f4;
}
</style>