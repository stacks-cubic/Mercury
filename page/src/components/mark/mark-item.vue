<template>
  <div class="mark-item">
    <img :src="item.icon" class="mark-icon" :alt="item.title"/>
    <div class="mark-info">
      <div class="line1">{{ item.title }}</div>
      <div class="text-small text-gray" v-if="item.describe">{{ item.describe }}</div>
    </div>
  </div>
</template>

<script>
export default {
  name: "mark-item",
  props: {
    item: {
      type: Object,
      default: () => {
        return {
          icon: 'favicon.ico',
          title: '',
          describe: ''
        };
      }
    }
  }
}
</script>

<style scoped>
.mark-item {
  backdrop-filter: saturate(100%) blur(5px);
  background-color: rgba(255, 255, 255, 0.7);
  transition: all ease-in 0.3s;
  transform: translateZ(0);
  align-items: center;
  cursor: pointer;
  display: flex;
}

.dark .mark-item {
  background-color: rgba(60, 60, 60, 0.6);
}

.mark-item:hover {
  background-color: rgba(255, 255, 255, 1);
}

.dark .mark-item:hover {
  background-color: rgba(80, 80, 80, 0.8);
}

.mark-icon {
  border-radius: 8px;
  padding: 5px;
  height: 50px;
  width: 50px;
}

.mark-info {
  padding: 5px;
}
</style>