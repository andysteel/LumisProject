package com.gmail.andersoninfonet.manageuser.config;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.google.common.base.Preconditions;

@Configuration
@EnableTransactionManagement
@PropertySource({ "classpath:application.properties" })
@ComponentScan({ "com.gmail.andersoninfonet.manageuser"})
@EnableJpaRepositories(basePackages = "com.gmail.andersoninfonet.manageuser.repository.jpa")
public class DataSourceJPAConfig {
	
	@Autowired
    private Environment env;
	
    @Bean(name = "entityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean() {
    	
        final LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource());
        em.setPackagesToScan(new String[] { "com.gmail.andersoninfonet.manageuser.model" });

        final HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        em.setJpaProperties(additionalProperties());

        return em;
    }
    
    @Bean(name = "dataSourceJPA")
    public DataSource dataSource() {
    	
        final DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(Preconditions.checkNotNull(env.getProperty("spring.datasource.driver-class-name")));
        dataSource.setUrl(Preconditions.checkNotNull(env.getProperty("spring.datasource.url")));
        dataSource.setUsername(Preconditions.checkNotNull(env.getProperty("spring.datasource.username")));
        dataSource.setPassword(Preconditions.checkNotNull(env.getProperty("spring.datasource.password")));

        return dataSource;
    }
    

    @Bean
    public PlatformTransactionManager transactionManager(final EntityManagerFactory emf) {
    	
        final JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(emf);
        return transactionManager;
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
        return new PersistenceExceptionTranslationPostProcessor();
    }
    
    final Properties additionalProperties() {
    	
        final Properties hibernateProperties = new Properties();   
        hibernateProperties.setProperty("hibernate.dialect", env.getProperty("spring.jpa.database-platform"));
        hibernateProperties.setProperty("hibernate.cache.use_second_level_cache", "false");
           
        return hibernateProperties;
    }
}
