<template>
  <div class="search">
    <a-input-group compact>
      <a-input v-model:value="keyword" style="width: calc(100% - 60px)">
        <template #addonBefore>
          <a-select v-model:value="type" style="width: 70px">
            <a-select-option value="baidu">百度</a-select-option>
            <a-select-option value="google">谷歌</a-select-option>
            <a-select-option value="bing">必应</a-select-option>
          </a-select>
        </template>
      </a-input>
      <a-button type="primary" style="width: 60px">搜索</a-button>
    </a-input-group>
  </div>
</template>

<script>
export default {
  name: 'search',
  data: () => ({
    type: 'google',
    keyword: ''
  })
}
</script>

<style scoped>
.search {
  padding: 0 10px 30px 10px;
  max-width: 700px;
  margin: 0 auto;
}
</style>