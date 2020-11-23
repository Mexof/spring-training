package ru.mexof.spring.training.batch;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class H2Config {
    @Bean
    public DataSource h2DataSource() {
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName("org.h2.Driver");
        dataSourceBuilder.url("jdbc:h2:~/test");
        dataSourceBuilder.username("SA");
        dataSourceBuilder.password("");

        return dataSourceBuilder.build();
    }
}
