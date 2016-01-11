package com.autolab.api;

import lombok.Getter;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by zhao on 15/10/22.
 */
@Configuration
public class ApiConfig {

    private static Logger logger = Logger.getLogger(ApiConfig.class);

    @Getter
    @Value("${autolab.debug}")
    private boolean debug;

    //为了解决Spring boot Post请求出现乱码的bug.
    @Bean
    public FilterRegistrationBean filterRegistrationBean() {

        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
        registrationBean.setFilter(characterEncodingFilter);
        characterEncodingFilter.setEncoding("UTF-8");
        characterEncodingFilter.setForceEncoding(true);
        registrationBean.setOrder(Integer.MIN_VALUE);
        registrationBean.addUrlPatterns("/*");
        return registrationBean;
    }


    /**
     * 允许跨站请求，方便我们的调试
     */
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("*")
                        .allowedMethods("POST", "GET")
                        .allowedHeaders("Authorization", "X-Request-With", "Accept", "Content-Type")
                        .exposedHeaders("Access-Control-Allow-Origin", "Access-Control-Allow-Methods")
                        .allowCredentials(false).maxAge(3600);
            }
        };
    }


}
