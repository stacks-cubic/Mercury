<template>
  <div class="blur" :class="{show:blur}"></div>
  <div class="background"></div>
  <div class="container">
    <div class="pa-10 header">
      <a-button>内网</a-button>
      <div class="float-right">
        <a-button class="mr-10" @click="switchBlur">{{ blur ? '透明' : '毛玻璃' }}</a-button>
        <a-button class="mr-10">设置</a-button>
        <a-button>我的</a-button>
      </div>
    </div>
    <div class="box">
      <div class="title">{{ info.name }}</div>
      <div class="search">
        <a-input-group compact>
          <a-input v-model:value="search.keyword" style="width: calc(100% - 60px)">
            <template #addonBefore>
              <a-select v-model:value="search.type" style="width: 70px">
                <a-select-option value="baidu">百度</a-select-option>
                <a-select-option value="google">谷歌</a-select-option>
                <a-select-option value="bing">必应</a-select-option>
              </a-select>
            </template>
          </a-input>
          <a-button type="primary" style="width: 60px">搜索</a-button>
        </a-input-group>
      </div>
      <div class="mark-box px-10">
        <a-row :gutter="[10,10]" class="justify-center" v-if="type===1">
          <a-col :xs="12" :md="6" :lg="4">
            <div class="mark-item">
              <img src="../assets/logo.png" class="mark-icon"/>
              <div class="mark-info">
                <div class="line1">内网服务标题</div>
                <div class="text-small text-gray">内网服务信息</div>
              </div>
            </div>
          </a-col>
          <a-col :xs="12" :md="6" :lg="4">
            <div class="mark-item">
              <img src="https://www.antdv.com/assets/logo.1ef800a8.svg" class="mark-icon"/>
              <div class="mark-info">
                <div class="line1">内网服务标题</div>
              </div>
            </div>
          </a-col>
          <a-col :xs="12" :md="6" :lg="4">
            <div class="mark-item">
              <img src="../assets/logo.png" class="mark-icon"/>
              <div class="mark-info">
                <div class="line1">内网服务标题内网服务标题</div>
              </div>
            </div>
          </a-col>
        </a-row>
        <a-row :gutter="[10,10]" class="justify-center" v-if="type===2">
          <a-col :xs="6" :md="3" :lg="2">
            <div class="mark-item-small">
              <img src="../assets/logo.png" class="mark-icon"/>
              <div class="mark-info line1">内网服务标题</div>
            </div>
          </a-col>
          <a-col :xs="6" :md="3" :lg="2">
            <div class="mark-item-small">
              <img src="https://www.antdv.com/assets/logo.1ef800a8.svg" class="mark-icon"/>
              <div class="mark-info line1">内网服务标题</div>
            </div>
          </a-col>
          <a-col :xs="6" :md="3" :lg="2">
            <div class="mark-item-small">
              <img src="../assets/logo.png" class="mark-icon"/>
              <div class="mark-info line1">内网服务标题内网服务标题</div>
            </div>
          </a-col>
        </a-row>
        <div class="tool-group flex align-center">
          <div class="pa-10 border-right">
            <div>网页小工具</div>
            <div class="text-small flex align-center" style="color: #5e5e5e;cursor: pointer">
              <span>查看全部</span>
              <right-outlined style="fontSize: 10px;margin: -1px 3px 0 0" />
            </div>
          </div>
        </div>
        <div class="mark-group">
          <a-collapse v-model:active-key="open[0]" expand-icon-position="right" ghost>
            <a-collapse-panel key="1" header="书签组1">
              <div>书签组合1</div>
              <template #extra>
                <setting-outlined />
              </template>
            </a-collapse-panel>
          </a-collapse>
        </div>
        <div class="mark-group">
          <a-collapse v-model:active-key="open[1]" expand-icon-position="right" ghost>
            <a-collapse-panel key="1" header="书签组2">
              <div>书签组合2</div>
              <template #extra>
                <setting-outlined />
              </template>
            </a-collapse-panel>
          </a-collapse>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { RightOutlined,SettingOutlined } from '@ant-design/icons-vue';
export default {
  name: 'Home',
  components:{RightOutlined,SettingOutlined},
  data: () => ({
    blur: false,
    type: 1,
    info: {
      name: 'Mercury',
      version: '0.0.0'
    },
    search: {
      type: 'google',
      keyword: ''
    },
    open:['1']
  }),
  methods: {
    init() {
      let info = localStorage.getItem('app:info');
      this.info = JSON.parse(info);
    },
    switchBlur() {
      this.blur = !this.blur;
    }
  },
  mounted() {
    this.init();
  }
}
</script>
<style scoped>
.background,
.blur {
  position: fixed;
  height: 100vh;
  width: 100vw;
  z-index: 1;
}

.background {
  background: url("https://w.wallhaven.cc/full/y8/wallhaven-y8622k.jpg") no-repeat center center;
}

.blur {
  backdrop-filter: saturate(100%) blur(5px);
  transition: all ease-in 0.3s;
  background: rgba(0, 0, 0, 0.2);
  opacity: 0;
  z-index: 2;
}

.show {
  opacity: 1;
}

.container {
  position: absolute;
  width: 100vw;
  z-index: 9;
}

.box {
  max-width: 1000px;
  margin: 0 auto;
}

.title {
  padding: 40px 0 20px 0;
  text-align: center;
  font-size: 56px;
  color: #fff;
}

.search {
  padding: 0 10px 30px 10px;
  max-width: 700px;
  margin: 0 auto;
}

.mark-item {
  backdrop-filter: saturate(100%) blur(5px);
  background: rgba(255, 255, 255, 0.7);
  transition: all ease-in 0.3s;
  align-items: center;
  border-radius: 8px;
  cursor: pointer;
  display: flex;
}

.mark-item:hover {
  background: rgba(255, 255, 255, 1);
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

.mark-item-small {
  transition: all ease-in 0.3s;
  border-radius: 8px;
  text-align: center;
  font-size: 12px;
  color: #fff;
}

.mark-item-small .mark-icon {
  backdrop-filter: saturate(100%) blur(5px);
  background: rgba(255, 255, 255, 0.7);
  transition: all ease-in 0.3s;
}

.mark-item-small .mark-info {
  padding: 2px 1px;
  max-width: 90px;
  margin: 0 auto;
}

.mark-item-small:hover {
  background: #fff;
  color: #333;
}

.mark-group,
.tool-group{
  backdrop-filter: saturate(100%) blur(5px);
  background: rgba(255, 255, 255, 0.3);
  transition: all ease-in 0.3s;
  border-radius: 8px;
  margin-top: 10px;
}

.mark-group:hover,
.tool-group:hover{
  background: rgba(255, 255, 255, 0.7);
}

.tool-group .border-right{
  border-right: 1px solid #bdbdbd;
}
</style>
<style>
.mark-group .ant-collapse-header{
  padding: 10px 40px 10px 10px !important;
}
</style>
