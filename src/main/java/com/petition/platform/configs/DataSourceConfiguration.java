package com.petition.platform.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.util.Objects;

/**
 * Configuration class for creating a DataSource bean.
 */
@Configuration
public class DataSourceConfiguration {
    /**
     * Default constructor for DataSourceConfiguration.
     */
    public DataSourceConfiguration() {}

    /**
     * Environment object to retrieve properties.
     */
    @Autowired
    Environment env;

    /**
     * Creates and configures a DataSource bean using properties from the environment.
     *
     * @return Configured DataSource bean.
     */
    @Bean
    public DataSource dataSource(){
        // Creating a new DriverManagerDataSource object
        final DriverManagerDataSource dataSource = new DriverManagerDataSource();
        // Setting driver class name retrieved from environment properties
        dataSource.setDriverClassName(Objects.requireNonNull(env.getProperty("driverClassName")));
        // Setting database URL retrieved from environment properties
        dataSource.setUrl(Objects.requireNonNull(env.getProperty("url")));
        // Returning the configured DataSource
        return dataSource;
    }
}