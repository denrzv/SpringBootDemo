package io.github.denrzv.springbootdemo.config;

import io.github.denrzv.springbootdemo.profiles.*;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class JavaConfig {
    @ConditionalOnProperty("netology.profile.dev")
    @Bean
    public SystemProfile devProfile() {
        return new DevProfile();
    }

    @ConditionalOnProperty("netology.profile.production")
    @Bean
    public SystemProfile prodProfile() {
        return new ProductionProfile();
    }
}