package com.example.miniproject.config;

import com.example.miniproject.domain.order.service.OrderService;
import com.example.miniproject.domain.order.service.OrderServiceFacade;
import com.zaxxer.hikari.HikariDataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class LockDataSourceConfig {

    private final OrderService orderService;

    @Bean
    @ConfigurationProperties("lock.datasource.hikari")
    public HikariDataSource lockDataSource() {
        return DataSourceBuilder.create()
            .type(HikariDataSource.class)
            .build();
    }

    @Bean
    public OrderServiceFacade orderServiceFacade() {
        return new OrderServiceFacade(orderService, lockDataSource());
    }
}
