# 系统初始化

## 判定逻辑

基于SpringBoot启动时对外部配置文件优先读取的逻辑, 系统启动时会检查配置文件是否存在标识字段, 不存在就意味着系统尚未初始化.

那么接下来就需要执行初始化程序:
1. 访问初始化接口
2. 传入数据库配置
3. 传入系统基础信息
4. 执行初始化脚本
   1. 创建数据库
   2. 创建数据表和数据
   3. 创建外部配置文件
5. 重启系统,完成初始化