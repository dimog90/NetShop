package com.example.config;

import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Properties;

/**
 * Конфигурация Hibernate для связи с базой данных.
 */
// Класс помечен аннотацией Configuration, что бы Spring видел этот класс, и заинжектил его в необходимое место.
@Configuration
@EnableTransactionManagement
public class HibernateConfig {

    // метод возвращвет фабрику сессий, через которую устанавливается связь с БД, метод отмечен аннотацией @Bean
    // Spring заинжектит его в указанное место.
    @Bean
    public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource());
        sessionFactory.setPackagesToScan("com.example.entity");
        sessionFactory.setHibernateProperties(hibernateProperties());

        return sessionFactory;
    }

    // метод возвращает бин с настройками доступа к БД, и Spring инжектит его в необходимое место
    @Bean
    public BasicDataSource dataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/shop?useSSL=false&useLegacyDatetimeCode=false&serverTimezone=UTC");
        dataSource.setUsername("root");
        dataSource.setPassword("root");
        return dataSource;
    }

    @Bean
    public PlatformTransactionManager hibernateTransactionManager() {
        HibernateTransactionManager transactionManager = new HibernateTransactionManager();
        transactionManager.setSessionFactory(sessionFactory().getObject());
        return transactionManager;
    }

    // бин валидатор, инжектится в сервис валидации
    @Bean
    public Validator getValidator() {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        return validatorFactory.getValidator();
    }

    private final Properties hibernateProperties() {
        Properties hibernateProperties = new Properties();
        hibernateProperties.setProperty("hibernate.hbm2ddl.auto", "update");
        hibernateProperties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
        return hibernateProperties;
    }
}