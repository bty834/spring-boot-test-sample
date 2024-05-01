package io.github.bty834.infrastructure.repository.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import javax.sql.DataSource;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.support.JdbcTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

@Configuration
public class DataSourceConfig {

    @Value("${ds.username:root}")
    private String username;
    @Value("${ds.password:12345678}")
    private String password;
    @Value("${ds.jdbcUrl:jdbc:mysql://localhost:3306/test?useSSL=false&serverTimezone=Asia/Shanghai}")
    private String jdbcUrl;
    @Value("${ds.driverClassName:com.mysql.cj.jdbc.Driver}")
    private String driverClassName;


    @Bean
    public DataSource dataSource(HikariConfig config){
        return new HikariDataSource(config);
    }

    @Bean
    public HikariConfig hikariConfig(){
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setUsername(username);
        hikariConfig.setPassword(password);
        hikariConfig.setJdbcUrl(jdbcUrl);
        hikariConfig.setDriverClassName(driverClassName);
        return hikariConfig;
    }

    @Bean
    public PlatformTransactionManager transactionManager(DataSource dataSource){
        return new JdbcTransactionManager(dataSource);
    }

    @Bean
    public TransactionTemplate transactionTemplate(PlatformTransactionManager transactionManager){
        return new TransactionTemplate(transactionManager);
    }

    @Bean
    public SqlSessionFactoryBean sqlSessionFactoryBean(DataSource dataSource){
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        return sqlSessionFactoryBean;
    }



}
