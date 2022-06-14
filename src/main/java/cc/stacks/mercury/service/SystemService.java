package cc.stacks.mercury.service;

import cc.stacks.mercury.MercuryApplication;
import cc.stacks.mercury.util.LogUtil;
import cc.stacks.mercury.util.TextUtil;
import cc.stacks.mercury.util.Transit;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.*;
import java.sql.*;

@Service
public class SystemService {

    @Value(value = "classpath:data/sqlite.sql")
    private Resource sqliteSQL;
    @Value(value = "classpath:data/mysql.sql")
    private Resource mysqlSQL;

    /**
     * 完成初始化
     *
     * @param dbUrl         数据库地址
     * @param dbDriver      数据库驱动
     * @param dbUser        数据库用户名
     * @param dbPassword    数据库密码
     * @param adminName     管理员用户名
     * @param adminNickname 管理员昵称
     * @param adminPassword 管理员密码
     * @param title         系统标题
     * @return 初始化响应
     */
    public Transit<Object> completeInit(String dbUrl, String dbDriver, String dbUser, String dbPassword, String adminName, String adminNickname, String adminPassword, String title) {
        try {
            String path = new ApplicationHome().getDir().getPath();
            File file = new File(path);
            Transit<Object> transit = Transit.auto(file.exists());
            if (!transit.isState()) transit = Transit.auto(file.mkdirs());

            String config = "server:\n  port: 20200\n  compression:\n    enabled: true\n    mime-types: text/plain,application/json,text/css,application/javascript,text/javascript,image/png,image/jpeg\n    min-response-size: 1KB\n  undertow:\n    threads:\n      io: 4\n      worker: 32\nspring:\n  jackson:\n    default-property-inclusion: non_null\n  servlet:\n    multipart:\n      max-request-size: 1024MB\n      max-file-size: 1024MB\n  datasource:\n    url: ${dbUrl}\n    driver-class-name: ${dbDriver}\n    username: ${dbUser}\n    password: ${dbPassword}\nmercury:\n  init: true\n  name: ${title}\n  version: 1.0.0";
            config = config.replace("${dbUrl}", dbUrl)
                    .replace("${dbDriver}", dbDriver)
                    .replace("${dbUser}", TextUtil.isNull(dbUser) ? "" : dbUser)
                    .replace("${dbPassword}", TextUtil.isNull(dbPassword) ? "" : dbPassword)
                    .replace("${title}", title);

            if (transit.isState()) transit = initDatabase(dbUrl, dbUser, dbPassword);
            if (transit.isState()) transit = writerConfigFile(path, config);
            if (transit.isState()) MercuryApplication.restart();
            return transit;
        } catch (Exception e) {
            return Transit.failure();
        }
    }

    /**
     * 写入配置文件
     *
     * @param config 配置内容
     * @return 写入状态
     * @throws IOException 数据流异常
     */
    private Transit<Object> writerConfigFile(String path, String config) throws IOException {
        FileWriter writer = null;
        try {
            File file = new File(path + "/application.yaml");
            LogUtil.info("Create config: " + file.getPath());
            if (!file.exists()) {
                File parentFile = file.getParentFile();
                if (parentFile.exists()) file.createNewFile();
                else {
                    parentFile.mkdirs();
                    file.createNewFile();
                }
            }
            writer = new FileWriter(file, false);
            writer.write(config);
            writer.close();
            writer = null;
            return Transit.success();
        } catch (Exception e) {
            if (writer != null) {
                writer.close();
                writer = null;
            }
            LogUtil.unexpected(10003, "Config creation failure", e);
            return Transit.failure(10003, e.getMessage());
        } finally {
            if (writer != null) writer.close();
        }
    }

    /**
     * 初始化数据库
     *
     * @param url 数据库地址
     * @return 初始化状态
     */
    private Transit<Object> initDatabase(String url, String user, String password) {
        try (Connection conn = TextUtil.isNull(user) ? DriverManager.getConnection(url) : DriverManager.getConnection(url, user, password)) {
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                Thread.sleep(1000);
                LogUtil.info("Connection database: " + meta.getURL());
                return initData(url, conn);
            }
            return Transit.failure(10005);
        } catch (Exception e) {
            return Transit.failure(10003, e.getMessage());
        }
    }

    /**
     * 初始化数据
     *
     * @param conn 数据库连接
     * @return 初始化状态
     */
    private Transit<Object> initData(String url, Connection conn) {
        try {
            StringBuilder sql = new StringBuilder();
            Statement statement = conn.createStatement();
            LogUtil.info("Create initial data...");
            InputStream input = null;
            if (url.contains("mysql")) input = mysqlSQL.getInputStream();
            else if (url.contains("sqlite")) input = sqliteSQL.getInputStream();
            if (input == null) return Transit.failure(10007, "Database not supported");
            try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(input))) {
                String temp;
                while ((temp = bufferedReader.readLine()) != null) {
                    if (!temp.startsWith("--")) sql.append(temp);
                    if (temp.endsWith(";")) {
                        statement.execute(sql.toString());
                        sql = new StringBuilder();
                    }
                }
                LogUtil.info("Create initial data complete");
                return Transit.success();
            } catch (Exception ex) {
                LogUtil.error(10006, "Create initial data fail: " + ex.getMessage());
                return Transit.failure(10006, ex.getMessage());
            }
        } catch (Exception e) {
            LogUtil.error(10005, "Create initial data fail: " + e.getMessage());
            return Transit.failure(10005, e.getMessage());
        }
    }

}