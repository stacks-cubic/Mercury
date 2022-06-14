package cc.stacks.mercury.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

//@Configuration
public class DataSourceConfig {
//
//    @Value("${mercury.init}")
//    private Boolean initState;
//    @Value("${spring.datasource.url}")
//    private String dbUrl;
//    @Value("${spring.datasource.driver-class-name}")
//    private String dbDriver;
//    @Value("${spring.datasource.username}")
//    private String dbUser;
//    @Value("${spring.datasource.password}")
//    private String dbPassword;
//
//    @Bean
//    public DataSource getDataSource() {
//        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
//        // 判断是否完成初始化
//        if(initState) {
//            dataSourceBuilder.url(dbDriver);
//            dataSourceBuilder.driverClassName(dbUrl);
//            dataSourceBuilder.username(dbUser);
//            dataSourceBuilder.password(dbPassword);
//        }else{
//            dataSourceBuilder.url("jdbc:h2:mem:temp");
//            dataSourceBuilder.driverClassName("org.h2.Driver");
//            dataSourceBuilder.username("SA");
//            dataSourceBuilder.password("");
//        }
//        return dataSourceBuilder.build();
//    }

}