/*
package com.cvs.cdc.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableTransactionManagement
@PropertySource({"classpath:application.properties"})
@EnableJpaRepositories(
        entityManagerFactoryRef = "entityManagerFactory",
        transactionManagerRef = "transactionManager",
        basePackages = "com.cvs.cdc.repo"
)
public class H2DbConfig {

    @Primary
    @Bean(name = "H2DataSource")
    @ConfigurationProperties(prefix = "h2.datasource")
    public DataSource h2DataSource(){
        return DataSourceBuilder.create().build();
    }




    @Primary
    @Bean(name="entityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean(EntityManagerFactoryBuilder builder, @Qualifier("H2DataDataSource") DataSource h2DataSource){
        Map<String,String> properties = new HashMap<>();
        properties.put("hibnerate.hbm2ddl.auto","validate");
        properties.put("hibernate.dialect","org.hibernate.dialect.TeradataDialect");
        return builder.dataSource(h2DataSource).properties(properties).packages("com.cvs.cdc.model").persistenceUnit("cdc").build();
    }

    @Primary
    @Bean(name="transactionManager")
    public PlatformTransactionManager transactionManager(@Qualifier("entityManagerFactory") EntityManagerFactory entityManagerFactory ){
        return new JpaTransactionManager(entityManagerFactory);
    }
}
*/
