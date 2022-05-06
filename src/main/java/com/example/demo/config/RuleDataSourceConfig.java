package com.example.demo.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Configuration
@EnableJpaRepositories(
        basePackages = "com.cathaypacific.aesrefundsvc.repository",
        entityManagerFactoryRef = "ruleEntityManager",
        transactionManagerRef = "ruleTransactionManager")
public class RuleDataSourceConfig {

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.rule")
    public DataSource ruleDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.jpa.rule")
    public Properties ruleJpaProperties() {
        return new Properties();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean ruleEntityManager() {
        LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        localContainerEntityManagerFactoryBean.setDataSource(ruleDataSource());
        localContainerEntityManagerFactoryBean.setPackagesToScan("com.cathaypacific.aesrefundsvc.entity");
        localContainerEntityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        localContainerEntityManagerFactoryBean.setJpaPropertyMap(new HashMap<String, String>((Map) ruleJpaProperties()));
        return localContainerEntityManagerFactoryBean;
    }

    @Bean
    public PlatformTransactionManager ruleTransactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(ruleEntityManager().getObject());
        return transactionManager;
    }

}
