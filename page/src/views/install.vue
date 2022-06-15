<template>
  <div class="box no-select">
    <div class="card">
      <div class="pa-10 border-bottom">
        <div class="float-right">v{{ version }}</div>
        <div>Mercury 安装向导</div>
      </div>
      <div class="border-bottom pa-10" v-if="!state">
        <a-steps :current="current" size="small">
          <a-step title="数据源"/>
          <a-step title="配置"/>
          <a-step title="完成"/>
        </a-steps>
      </div>
      <a-result v-if="state" status="warning" title="系统已安装" sub-title="如需重新安装, 请删除 Mercury 根目录下的 Yaml 配置文件">
        <template #extra>
          <a-button type="primary" @click="$router.push('/home')">欢迎使用</a-button>
        </template>
      </a-result>
      <div class="form py-20 pr-20 pl-10" v-else>
        <a-form :label-col="label" v-show="current === 0">
          <a-form-item label="类型">
            <a-select v-model:value="database.select" style="width: 100px" :disabled="test">
              <a-select-option value="sqlite">SQLite</a-select-option>
              <a-select-option value="mysql">MySQL 8</a-select-option>
            </a-select>
          </a-form-item>
          <template v-if="database.select==='sqlite'">
            <a-form-item label="存储文件" required style="width: 260px">
              <a-input v-model:value="database.sqlite.file" suffix=".db" allowClear placeholder="data"/>
            </a-form-item>
          </template>
          <template v-else-if="database.select==='mysql'">
            <a-form-item label="连接地址" required style="width: 260px">
              <a-input v-model:value="database.mysql.host" allowClear placeholder="127.0.0.1" :disabled="test"/>
            </a-form-item>
            <a-form-item label="端口" required style="width: 190px">
              <a-input type="number" v-model:value="database.mysql.port" allowClear placeholder="3306" :disabled="test"/>
            </a-form-item>
            <a-form-item label="数据库" required style="width: 190px">
              <a-input v-model:value="database.mysql.name" allowClear placeholder="mercury" :disabled="test"/>
            </a-form-item>
          </template>
          <a-form-item label="用户名" :required="database.select!=='sqlite'" allowClear style="width: 200px">
            <a-input v-model:value="form.dbUser" :disabled="test"/>
          </a-form-item>
          <a-form-item label="密码" :required="database.select!=='sqlite'" allowClear>
            <a-input v-model:value="form.dbPassword" :disabled="test"/>
          </a-form-item>
        </a-form>
        <a-form :label-col="label" v-show="current === 1">
          <a-form-item label="用户名" style="width: 220px">
            <a-input v-model:value="form.adminName" allowClear :disabled="loading" placeholder="admin"/>
          </a-form-item>
          <a-form-item label="昵称" style="width: 180px">
            <a-input v-model:value="form.adminNickname" allowClear :disabled="loading" placeholder="管理员"/>
          </a-form-item>
          <a-form-item label="密码" required>
            <a-input v-model:value="form.adminPassword" allowClear :disabled="loading" placeholder="8~24位大小写字符和数字"/>
          </a-form-item>
          <a-form-item label="网站标题">
            <a-input v-model:value="form.title" allowClear :disabled="loading" placeholder="2~12位英文或1~6位中文字符"/>
          </a-form-item>
        </a-form>
        <a-form :label-col="label" v-show="current === 2">
          <a-result status="success" title="安装完成">
            <template #extra>
              <a-button type="primary" @click="$router.push('/home')">欢迎使用</a-button>
              <a-button @click="$router.push('/console')">管理</a-button>
            </template>
          </a-result>
        </a-form>
        <div class="pl-10" v-show="current !== 2">
          <a-button v-if="current === 0" :disabled="database.select==='sqlite'" :loading="test" @click="testDb">
            {{ test ? '连接中' : '测试连接' }}
          </a-button>
          <a-button v-else @click="back" :disabled="loading">上一步</a-button>
          <a-button class="float-right" type="primary" v-if="current === 1" @click="submit" :loading="loading">
            {{ loading ? '安装中' : '开始安装' }}
          </a-button>
          <a-button class="float-right" type="primary" v-else @click="next" :disabled="test">下一步</a-button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'Install',
  data: () => ({
    loading: false,
    state: false,
    test: false,
    version: '',
    current: 0,
    label: {style: {width: '80px'}},
    database: {
      select: 'sqlite',
      sqlite: {
        file: ''
      },
      mysql: {
        host: '',
        port: '',
        name: ''
      }
    },
    form: {
      dbUrl: '',
      dbDriver: '',
      dbUser: '',
      dbPassword: '',
      adminName: '',
      adminNickname: '',
      adminPassword: '',
      title: ''
    }
  }),
  methods: {
    init() {
      this.$api.system.init().then(res => {
        this.state = res.state;
        this.version = res.data.version;
      }).catch(() => {
        this.state = true;
      })
    },
    back() {
      this.current = this.current - 1
    },
    next() {
      if (this.current === 0) this.buildDbUrl();
      this.current = this.current + 1;
    },
    buildDbUrl() {
      let url;
      let driver;
      let db = this.database;
      if (db.select === 'sqlite') {
        if (!db.sqlite.file) return this.showWarn('存储文件名称不能为空');
        url = 'jdbc:sqlite:' + db.sqlite.file + '.db';
        driver = 'org.sqlite.JDBC';
      } else if (db.select === 'mysql') {
        if (!db.mysql.host) return this.showWarn('连接地址不能为空');
        if (!db.mysql.port) return this.showWarn('端口不能为空');
        if (!db.mysql.name) return this.showWarn('数据库名称不能为空');
        if (!this.form.dbUser) return this.showWarn('数据库用户名不能为空');
        if (!this.form.dbPassword) return this.showWarn('数据库密码名称不能为空');
        url = 'jdbc:mysql://' + db.mysql.host + ':' + db.mysql.port + '/' + db.mysql.name + '?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=GMT&allowPublicKeyRetrieval=true';
        driver = 'com.mysql.cj.jdbc.Driver';
      }
      this.form.dbUrl = url;
      this.form.dbDriver = driver;
    },
    submit() {
      if (!this.form.title) this.form.title = 'Mercury';
      if (!this.form.adminName) this.form.adminName = 'admin';
      if (!this.form.adminNickname) this.form.adminNickname = '管理员';
      if (!this.form.adminPassword) return this.showWarn('管理员密码不能为空');
      this.$modal.confirm({
        title: '确认要开始安装吗?',
        content: '安装程序将根据您提供的数据源信息连接并初始化数据库, 安装完成后将在 Mercury 根目录自动生成配置文件, 是否开始?',
        okText: '开始安装',
        cancelText: '取消',
        onOk: () => {
          this.loading = true;
          this.$api.system.install(this.form).catch(() => {
            this.showWarn('网络异常, 无法连接到服务器');
            this.loading = false;
          });
          let time = setInterval(() => {
            this.$api.system.init().then(res => {
              if (res.state) {
                this.current = 2;
                clearInterval(time);
              }
            })
          }, 3000);
        },
      })
    },
    showWarn(txt) {
      this.$message.warn(txt);
      return false;
    },
    testDb() {
      this.buildDbUrl();
      this.test = true;
      this.$api.system.testDb(this.form).then(res => setTimeout(() => {
        this.test = false;
        if (res.state) this.$message.success('数据库连接成功');
        else this.showWarn(res.message ? res.message : '数据库连接失败');
      }, 300)).catch(() => {
        this.showWarn('网络异常, 无法连接到服务器');
        this.test = false;
      });
    }
  },
  mounted() {
    this.init();
  }
}
</script>
<style scoped>
.box {
  max-width: 360px;
  padding: 20px 0;
  margin: 0 auto;
}

.card {
  box-shadow: 0 2px 10px 0 rgba(0, 0, 0, .1);
  border: 1px solid #eeeeee;
  background-color: #fff;
  border-radius: 8px;
}
</style>