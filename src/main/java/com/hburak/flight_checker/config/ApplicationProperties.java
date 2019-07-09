package com.hburak.flight_checker.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationProperties {
    @Value("${flights.cheap}")
    private String cheapFlightsUrl;

    @Value("${flights.business}")
    private String businessFlightsUrl;

    @Value("${spring.data.rest.default-page-size}")
    private int defaultPageSize;

    @Value("${hello.message}")
    private String helloMessage;

    public String getCheapFlightsUrl() {
        return cheapFlightsUrl;
    }

    public String getBusinessFlightsUrl() {
        return businessFlightsUrl;
    }

    public int getDefaultPageSize() {
        return defaultPageSize;
    }

    public String getHelloMessage() {
        return helloMessage;
    }
}
