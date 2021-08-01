package com.cvs.cdc.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.batch.core.configuration.annotation.DefaultBatchConfigurer;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
@EnableBatchProcessing
public class SpringBatchConfig extends DefaultBatchConfigurer {

    @Override
    public void setDataSource(DataSource dataSource) {
        // override to do not set datasource even if a datasource exist.
        // initialize will use a Map based JobRepository (instead of database)
        System.out.println(" datasource value is " + dataSource);
    }


    @Bean
    public DataSource dataSource() {
        HikariDataSource hikariDataSource = new HikariDataSource();
        hikariDataSource.setDriverClassName("com.teradata.jdbc.TeraDriver");
        hikariDataSource.setJdbcUrl("jdbc:teradata://TIDWDEV1");
        hikariDataSource.setUsername("IDW_STG_CPL");
        hikariDataSource.setPassword("rFh_9ghA");
        hikariDataSource.setMaximumPoolSize(10);
        return hikariDataSource;
    }

}