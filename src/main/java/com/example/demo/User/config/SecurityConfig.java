package com.example.demo.user.config;


import com.example.demo.reservation.service.ReservationService;
import com.example.demo.roomreservation.roomreservationservice.RoomReservationService;
import com.example.demo.user.authentication.AdminAuthorizationFilter;
import com.example.demo.user.authentication.ApiJsonWebTokenFilter;
import com.example.demo.user.authentication.ValidReservationFilter;
import com.example.demo.user.authentication.ValidStatusUpdateFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SecurityConfig {
    @Bean
    public FilterRegistrationBean<ValidStatusUpdateFilter> validStatusUpdateFilter(ReservationService reservationService, RoomReservationService roomReservationService) {
        FilterRegistrationBean<ValidStatusUpdateFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new ValidStatusUpdateFilter(reservationService,roomReservationService));
        registrationBean.addUrlPatterns("/api/reservation/status/*");
        return registrationBean;
    }


    @Bean
    public FilterRegistrationBean<ApiJsonWebTokenFilter> apiJsonWebTokenFilterRegistrationBean() {
        FilterRegistrationBean<ApiJsonWebTokenFilter> registrationBean = new FilterRegistrationBean<>();
        ApiJsonWebTokenFilter apiJsonWebTokenFilter = new ApiJsonWebTokenFilter();
        registrationBean.setFilter(apiJsonWebTokenFilter);
        registrationBean.addUrlPatterns("/api/*");
        registrationBean.addInitParameter("excludedPaths", "/api/login,/api/user/create,/api/reservation/status/");

        return registrationBean;
    }

    @Bean
    public FilterRegistrationBean<AdminAuthorizationFilter> adminAuthorizationFilter() {
        FilterRegistrationBean<AdminAuthorizationFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new AdminAuthorizationFilter());
        registrationBean.addUrlPatterns("/api/room/create");
        registrationBean.addUrlPatterns("/api/user/update/*");
        registrationBean.addUrlPatterns("/api/room/top-rented");
        return registrationBean;
    }

    @Bean
    public FilterRegistrationBean<ValidReservationFilter> validReservationFilter(ReservationService reservationService) {
        FilterRegistrationBean<ValidReservationFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new ValidReservationFilter(reservationService));
        registrationBean.addUrlPatterns("/api/reservation/invite/*");
        return registrationBean;
    }

}
