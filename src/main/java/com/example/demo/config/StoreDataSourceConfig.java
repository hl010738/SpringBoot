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
        basePackages = "com.cathaypacific.aesrefundsvc.store.repository",
        entityManagerFactoryRef = "storeEntityManager",
        transactionManagerRef = "storeTransactionManager")
public class StoreDataSourceConfig {

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.store")
    public DataSource storeDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.jpa.store")
    public Properties storeJpaProperties() {
        return new Properties();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean storeEntityManager() {
        LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        localContainerEntityManagerFactoryBean.setDataSource(storeDataSource());
        localContainerEntityManagerFactoryBean.setPackagesToScan("com.cathaypacific.aesrefundsvc.store.entity");
        localContainerEntityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        localContainerEntityManagerFactoryBean.setJpaPropertyMap(new HashMap<String, String>((Map) storeJpaProperties()));
        return localContainerEntityManagerFactoryBean;
    }

    @Bean
    public PlatformTransactionManager storeTransactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(storeEntityManager().getObject());
        return transactionManager;
    }

}
