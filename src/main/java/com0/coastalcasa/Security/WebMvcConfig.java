package com0.coastalcasa.Security;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@ComponentScan(basePackages = "com0.coastalcasa.Security")
public class WebMvcConfig implements WebMvcConfigurer {

    // addPathPatterns: paths that should check whether contains JWT
    // excludePathPatterns: paths that should not be checked

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new UserInterceptor())
                .addPathPatterns("/listings/createlisting")
                .addPathPatterns("/listings/test")
                .excludePathPatterns("/landlord/**")
                .excludePathPatterns("/landlord/**");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedHeaders("*")
                .allowedHeaders("*")
                .allowCredentials(true)
                .maxAge(10000);
    }
}