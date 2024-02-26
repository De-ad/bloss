package com.brigada.bloss.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import com.atomikos.jdbc.AtomikosDataSourceBean;

import jakarta.persistence.EntityManagerFactory;

@Configuration
@EnableJpaRepositories(basePackages = "com.brigada.bloss.entity", entityManagerFactoryRef = "userEntityManager", transactionManagerRef = "transactionManager")
public class UserConfig {
    
    @Bean(initMethod = "init", destroyMethod = "close")
    public AtomikosDataSourceBean userDataSource() {
        AtomikosDataSourceBean dataSource = new AtomikosDataSourceBean();
        //   dataSource can be cofigured
        return dataSource;
    }

    @Bean
    public EntityManagerFactory userEntityManager() {
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setJpaVendorAdapter(vendorAdapter);
        // factory can be cofigured
        return factory.getObject();
    }

}
