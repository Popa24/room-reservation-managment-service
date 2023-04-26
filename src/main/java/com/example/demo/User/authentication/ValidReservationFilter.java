package com.example.demo.user.authentication;

import com.example.demo.reservation.service.ReservationDomainObject;
import com.example.demo.reservation.service.ReservationService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class ValidReservationFilter implements Filter {


    private final ReservationService reservationService;


    public ValidReservationFilter(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String jwt = httpRequest.getHeader("Authorization");
        if (jwt != null && jwt.startsWith("Bearer ")) {
            jwt = jwt.substring(7);
        }

        Integer userId = null;
        if (jwt != null) {
            Claims claims = LoginController.decodeJWT(jwt);
            userId = Integer.parseInt(claims.getId());
        }


        if (userId != null) {
            String path = httpRequest.getRequestURI();
            Long id = Long.parseLong(path.substring(path.lastIndexOf('/') + 1));
            ReservationDomainObject reservationDomainObject = reservationService.findById(id);
            if (reservationDomainObject.getUserId().equals(userId)) {
                chain.doFilter(request, response);
            } else {
                httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN, "You do not have a valid reservation.");
            }
        } else {
            httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Missing or invalid Authorization header");
        }
    }
}
