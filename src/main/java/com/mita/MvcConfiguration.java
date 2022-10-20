package com.mita;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class MvcConfiguration implements WebMvcConfigurer {
    
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("forward:/home/index");
        registry.addViewController("/home").setViewName("forward:/home/index");
        registry.addViewController("/product").setViewName("forward:/product/list");
        registry.addViewController("/cart").setViewName("forward:/cart/list");
        registry.addViewController("/report").setViewName("forward:/report/list");
        registry.addViewController("/transactions").setViewName("forward:/transactions/list");
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
