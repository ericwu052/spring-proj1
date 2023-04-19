package com.example.demo;

import com.example.demo.filters.JWTFilter;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

/**
 * Note on SecurityScheme
 * We want to specify which API needs authorization.
 */
@SpringBootApplication
@SecurityScheme(name = "BearerJWT", type = SecuritySchemeType.HTTP, scheme = "bearer", bearerFormat = "JWT",
        description = "Bearer token for the project")
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    /**
     * Protect identity resource with JWTFilter.
     */
    @Bean
    public FilterRegistrationBean<JWTFilter> filterRegistrationBean() {
        FilterRegistrationBean<JWTFilter> registrationBean = new FilterRegistrationBean<>();
        JWTFilter authFilter = new JWTFilter();
        registrationBean.setFilter(authFilter);
        registrationBean.addUrlPatterns("/api/user/*");
        return registrationBean;
    }
}
