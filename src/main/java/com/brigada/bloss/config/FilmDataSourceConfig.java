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
        entityManagerFactoryRef = "filmDataSourceConfiguration",
        transactionManagerRef = "blossTransactionManager",
        basePackages = {"com.brigada.bloss.dao"}
)
public class FilmDataSourceConfig {

    private final JpaProperties jpaProperties;

    @Autowired
    public FilmDataSourceConfig(JpaProperties jpaProperties) {
        this.jpaProperties = jpaProperties;
    }

    @Bean(name = "filmEntityManagerFactoryBuilder")
    @Primary
    public EntityManagerFactoryBuilder filmEntityManagerFactoryBuilder() {
        return new EntityManagerFactoryBuilder(
                new HibernateJpaVendorAdapter(), jpaProperties.getProperties(), null
        );
    }

    @Bean(name = "filmDataSourceConfiguration")
    @Primary
    public LocalContainerEntityManagerFactoryBean filmEntityManager(
            @Qualifier("filmEntityManagerFactoryBuilder") EntityManagerFactoryBuilder filmEntityManagerFactoryBuilder,
            @Qualifier("filmDataSource") DataSource postgresDataSource
    ) {
        return filmEntityManagerFactoryBuilder
                .dataSource(postgresDataSource)
                .packages("com.brigada.bloss.entity")
                .persistenceUnit("postgres")
                .properties(jpaProperties.getProperties())
                .jta(true)
                .build();
    }

    @Bean("filmDataSourceProperties")
    @Primary
    @ConfigurationProperties("datasource.film")
    public DataSourceProperties filmDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean("filmDataSource")
    @Primary
    @ConfigurationProperties("datasource.film")
    public DataSource filmDataSource(@Qualifier("filmDataSourceProperties") DataSourceProperties filmDataSourceProperties) {
        PGXADataSource ds = new PGXADataSource();
        ds.setUrl(filmDataSourceProperties.getUrl());
        ds.setUser(filmDataSourceProperties.getUsername());
        ds.setPassword(filmDataSourceProperties.getPassword());

        AtomikosDataSourceBean xaDataSource = new AtomikosDataSourceBean();
        xaDataSource.setXaDataSource(ds);
        xaDataSource.setMaxPoolSize(15);
        xaDataSource.setUniqueResourceName("xa_film");
        return xaDataSource;
    }

}
