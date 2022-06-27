<template>
  <div class="setting-item flex align-center justify-between pa-10 border-bottom" @click="openSub">
    <div class="flex align-center">
      <slot></slot>
      <div class="ml-5">{{ item.title }}</div>
    </div>
    <right-outlined/>
  </div>
</template>

<script>
import {RightOutlined} from '@ant-design/icons-vue';

export default {
  name: "setting-drawer-item",
  components: {RightOutlined},
  props: {
    item: {
      type: Object,
      default: () => {
        return {
          id: 0,
          title: ''
        };
      }
    }
  },
  emits: ["open"],
  methods: {
    openSub() {
      this.$emit("open", this.item.id);
    }
  }
}
</script>

<style scoped>
.setting-item {
  cursor: pointer;
}

.setting-item:hover {
  background-color: #f9f9f9;
}

.setting-item:active {
  background-color: #f4f4f4;
}
</style>