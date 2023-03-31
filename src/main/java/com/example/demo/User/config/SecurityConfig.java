package com.example.demo.user.config;

import com.example.demo.user.authentication.AdminAuthorizationFilter;
import com.example.demo.user.authentication.ApiJsonWebTokenFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SecurityConfig {

    @Bean
    public FilterRegistrationBean<ApiJsonWebTokenFilter> apiJsonWebTokenFilterRegistrationBean() {
        FilterRegistrationBean<ApiJsonWebTokenFilter> registrationBean = new FilterRegistrationBean<>();
        ApiJsonWebTokenFilter apiJsonWebTokenFilter = new ApiJsonWebTokenFilter();
        registrationBean.setFilter(apiJsonWebTokenFilter);
        registrationBean.addUrlPatterns("/api/*");
        registrationBean.addInitParameter("excludedPaths", "/api/login,/api/create/user"); // Exclude the login endpoint

        return registrationBean;
    }
    @Bean
    public FilterRegistrationBean<AdminAuthorizationFilter> adminAuthorizationFilter() {
        FilterRegistrationBean<AdminAuthorizationFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new AdminAuthorizationFilter());
        registrationBean.addUrlPatterns("/api/room");
        registrationBean.addUrlPatterns("/api/user/*");
        return registrationBean;
    }
}
