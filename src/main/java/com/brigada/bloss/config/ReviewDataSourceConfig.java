package com.brigada.bloss.config;

import javax.sql.DataSource;

import org.postgresql.xa.PGXADataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import com.atomikos.jdbc.AtomikosDataSourceBean;

@Configuration
@EnableJpaRepositories(
        entityManagerFactoryRef = "reviewDataSourceConfiguration",
        transactionManagerRef = "blossTransactionManager"
)
public class ReviewDataSourceConfig {

    private final JpaProperties jpaProperties;

    @Autowired
    public ReviewDataSourceConfig(JpaProperties jpaProperties) {
        this.jpaProperties = jpaProperties;
    }

    @Bean(name = "reviewEntityManagerFactoryBuilder")
    @Primary
    public EntityManagerFactoryBuilder reviewEntityManagerFactoryBuilder() {
        return new EntityManagerFactoryBuilder(
                new HibernateJpaVendorAdapter(), jpaProperties.getProperties(), null
        );
    }

    @Bean(name = "reviewDataSourceConfiguration")
    @Primary
    public LocalContainerEntityManagerFactoryBean reviewEntityManager(
            @Qualifier("reviewEntityManagerFactoryBuilder") EntityManagerFactoryBuilder reviewEntityManagerFactoryBuilder,
            @Qualifier("reviewDataSource") DataSource postgresDataSource
    ) {
        return reviewEntityManagerFactoryBuilder
                .dataSource(postgresDataSource)
                .packages("com.brigada.bloss.entity")
                .persistenceUnit("postgres")
                .properties(jpaProperties.getProperties())
                .jta(true)
                .build();
    }

    @Bean("reviewDataSourceProperties")
    @Primary
    @ConfigurationProperties("datasource.review")
    public DataSourceProperties reviewDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean("reviewDataSource")
    @Primary
    @ConfigurationProperties("datasource.review")
    public DataSource reviewDataSource(@Qualifier("reviewDataSourceProperties") DataSourceProperties reviewDataSourceProperties) {
        PGXADataSource ds = new PGXADataSource();
        ds.setUrl(reviewDataSourceProperties.getUrl());
        ds.setUser(reviewDataSourceProperties.getUsername());
        ds.setPassword(reviewDataSourceProperties.getPassword());

        AtomikosDataSourceBean xaDataSource = new AtomikosDataSourceBean();
        xaDataSource.setXaDataSource(ds);
        xaDataSource.setMaxPoolSize(15);
        xaDataSource.setUniqueResourceName("xa_review");
        return xaDataSource;
    }

}
