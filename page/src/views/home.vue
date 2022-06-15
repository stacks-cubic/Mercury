<template>
  <div class="blur" :class="{show:blur}"></div>
  <div class="background"></div>
  <div class="container">
    <div class="pa-10 header">
      <a-button>内网</a-button>
      <div class="float-right">
        <a-button class="mr-10" @click="switchBlur">{{blur ? '透明':'毛玻璃'}}</a-button>
        <a-button class="mr-10">设置</a-button>
        <a-button>我的</a-button>
      </div>
    </div>
    <div class="box">
      <div class="title">{{info.name}}</div>
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
              <img src="../assets/logo.png" class="mark-icon" />
              <div class="mark-info">
                <div class="line1">内网服务标题</div>
                <div class="text-small text-gray">内网服务信息</div>
              </div>
            </div>
          </a-col>
          <a-col :xs="12" :md="6" :lg="4">
            <div class="mark-item">
              <img src="https://www.antdv.com/assets/logo.1ef800a8.svg" class="mark-icon" />
              <div class="mark-info">
                <div class="line1">内网服务标题</div>
              </div>
            </div>
          </a-col>
          <a-col :xs="12" :md="6" :lg="4">
            <div class="mark-item">
              <img src="../assets/logo.png" class="mark-icon" />
              <div class="mark-info">
                <div class="line1">内网服务标题内网服务标题</div>
              </div>
            </div>
          </a-col>
        </a-row>
        <a-row :gutter="[10,10]" class="justify-center" v-if="type===2">
          <a-col :xs="6" :md="3" :lg="2">
            <div class="mark-item-small">
              <img src="../assets/logo.png" class="mark-icon" />
              <div class="mark-info line1">内网服务标题</div>
            </div>
          </a-col>
          <a-col :xs="6" :md="3" :lg="2">
            <div class="mark-item-small">
              <img src="https://www.antdv.com/assets/logo.1ef800a8.svg" class="mark-icon" />
              <div class="mark-info line1">内网服务标题</div>
            </div>
          </a-col>
          <a-col :xs="6" :md="3" :lg="2">
            <div class="mark-item-small">
              <img src="../assets/logo.png" class="mark-icon" />
              <div class="mark-info line1">内网服务标题内网服务标题</div>
            </div>
          </a-col>
        </a-row>
        <div class="mark-group"></div>
        <div class="mark-group"></div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'Home',
  data: () => ({
    blur: false,
    type: 1,
    info: {
      name: 'Mercury',
      version: '0.0.0'
    },
    search:{
      type: 'google',
      keyword: ''
    }
  }),
  methods: {
    init() {
      let info = localStorage.getItem('app:info');
      this.info = JSON.parse(info);
    },
    switchBlur(){
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
.blur{
  position: fixed;
  height: 100vh;
  width: 100vw;
  z-index: 1;
}

.background{
  background: url("https://w.wallhaven.cc/full/y8/wallhaven-y8622k.jpg") no-repeat center center;
}

.blur{
  backdrop-filter: saturate(100%) blur(5px);
  transition: all ease-in 0.3s;
  background: rgba(0,0,0,0.2);
  opacity: 0;
  z-index: 2;
}

.show{
  opacity: 1;
}

.container{
  position: absolute;
  width: 100vw;
  z-index: 9;
}

.box{
  max-width: 1000px;
  margin: 0 auto;
}

.title{
  padding: 40px 0 20px 0;
  text-align: center;
  font-size: 56px;
  color: #fff;
}

.search{
  padding: 0 10px 30px 10px;
  max-width: 700px;
  margin: 0 auto;
}

.mark-item{
  backdrop-filter: saturate(100%) blur(5px);
  background: rgba(255,255,255,0.7);
  transition: all ease-in 0.3s;
  align-items: center;
  border-radius: 8px;
  cursor: pointer;
  display: flex;
}

.mark-item:hover{
  background: rgba(255,255,255,1);
}

.mark-icon{
  border-radius: 8px;
  padding: 5px;
  height: 50px;
  width: 50px;
}

.mark-info{
  padding: 5px;
}

.mark-item-small{
  transition: all ease-in 0.3s;
  border-radius: 8px;
  text-align: center;
  font-size: 12px;
  color: #fff;
}

.mark-item-small .mark-icon{
  backdrop-filter: saturate(100%) blur(5px);
  background: rgba(255,255,255,0.7);
  transition: all ease-in 0.3s;
}

.mark-item-small .mark-info{
  padding: 2px 1px;
  max-width: 90px;
  margin: 0 auto;
}

.mark-item-small:hover{
  background: #fff;
  color: #333;
}
</style>
