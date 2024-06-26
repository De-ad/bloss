package com.brigada.bloss.config;

import com.atomikos.icatch.jta.TransactionSynchronizationRegistryImp;
import com.atomikos.icatch.jta.UserTransactionImp;
import com.atomikos.icatch.jta.UserTransactionManager;
import com.atomikos.spring.AtomikosDataSourceBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.transaction.jta.JtaTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;

import static org.springframework.transaction.TransactionDefinition.*;

@Configuration
@EnableRetry
public class AtomikosConfig {

    @Bean(name = "transactionTemplateRepeatableRead")
    public TransactionTemplate transactionTemplate() {
        TransactionTemplate template = new TransactionTemplate(transactionManager());
        template.setIsolationLevel(ISOLATION_REPEATABLE_READ);
        template.setPropagationBehavior(PROPAGATION_REQUIRES_NEW);
        return template;
    }

    @Bean(name = "transactionTemplateRepeatableReadSupports")
    public TransactionTemplate supportingTransactionTemplate() {
        TransactionTemplate template = new TransactionTemplate(transactionManager());
        template.setIsolationLevel(ISOLATION_REPEATABLE_READ);
        template.setPropagationBehavior(PROPAGATION_SUPPORTS);
        return template;
    }

    @Bean(name = "transactionTemplateReadCommitted")
    public TransactionTemplate readOnlyTransactionTemplate() {
        TransactionTemplate template = new TransactionTemplate(transactionManager());
        template.setIsolationLevel(ISOLATION_READ_COMMITTED);
        template.setPropagationBehavior(PROPAGATION_REQUIRES_NEW);
        return template;
    }

    @Bean
    public JtaTransactionManager transactionManager() {
        JtaTransactionManager jtaTransactionManager = new JtaTransactionManager();
        jtaTransactionManager.setUserTransaction(new UserTransactionImp());
        jtaTransactionManager.setTransactionManager(new UserTransactionManager());
        jtaTransactionManager.setTransactionSynchronizationRegistry(new TransactionSynchronizationRegistryImp());
        jtaTransactionManager.setAllowCustomIsolationLevels(true);
        return jtaTransactionManager;
    }

    @Bean
    public DataSource dataSource() {
        return new AtomikosDataSourceBean();
    }

}
