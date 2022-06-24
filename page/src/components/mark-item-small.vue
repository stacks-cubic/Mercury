<template>
  <div class="mark-item-small">
    <img :src="item.icon" class="mark-icon" :alt="item.title"/>
    <div class="mark-info line1">{{item.title}}</div>
  </div>
</template>

<script>
export default {
  name: "mark-item-small",
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
.mark-item-small {
  transition: all ease-in 0.3s;
  /*border-radius: 8px;*/
  text-align: center;
  font-size: 12px;
  color: #f4f4f4;
}

.dark .mark-item-small {
  color: #f4f4f4;
}

.mark-item-small .mark-icon {
  backdrop-filter: saturate(100%) blur(5px);
  background-color: rgba(255, 255, 255, 0.7);
  transition: all ease-in 0.3s;
  border-radius: 6px;
  padding: 5px;
  height: 50px;
  width: 50px;
}

.dark .mark-item-small .mark-icon {
  background-color: rgba(60, 60, 60, 0.6);
}

.dark .mark-item-small:hover .mark-icon {
  background-color: rgba(80, 80, 80, 0.8);
}

.mark-item-small .mark-info {
  padding: 2px 1px;
  max-width: 90px;
  margin: 0 auto;
}

.mark-item-small:hover {
  background-color: #fff;
  color: #333;
}

.dark .mark-item-small:hover {
  background-color: rgba(80, 80, 80, 0.8);
  color: #f4f4f4;
}
</style>