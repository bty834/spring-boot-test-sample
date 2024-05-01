package io.github.bty834.domain.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SampleConfig {


    @Bean
    public String hello(){
        return "hello";
    }
}
