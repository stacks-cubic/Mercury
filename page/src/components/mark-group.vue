<template>
  <div class="mark-group">
    <a-collapse v-model:active-key="open" expand-icon-position="right" ghost>
      <a-collapse-panel key="true" :header="group.title">
        <div>书签组合</div>
        <template #extra>
          <setting-outlined/>
        </template>
      </a-collapse-panel>
    </a-collapse>
  </div>
</template>

<script>
import {SettingOutlined} from '@ant-design/icons-vue';

export default {
  name: "mark-group",
  components: {SettingOutlined},
  props: {
    group: {
      type: Object,
      default: () => {
        return {
          title: '',
          fold: false
        };
      }
    }
  },
  data: () => ({
    open: 'true'
  }),
  mounted() {
    this.open = !this.group.fold + '';
  }
}
</script>

<style scoped>
.mark-group {
  backdrop-filter: saturate(100%) blur(5px);
  background-color: rgba(255, 255, 255, 0.5);
  transition: all ease-in 0.3s;
  margin-top: 10px;
}

.dark .mark-group {
  background-color: rgba(60, 60, 60, 0.6);
}

.mark-group:hover {
  background-color: rgba(255, 255, 255, 0.7);
}

.dark .mark-group:hover {
  background-color: rgba(80, 80, 80, 0.8);
}
</style>