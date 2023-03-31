package com.example.demo.user.authentication;

import io.jsonwebtoken.Claims;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class AdminAuthorizationFilter implements Filter {

    private static final String SECRET_KEY = "ssdjfjfjfjrfffffssdjfjfjfjrfffffssdjfjfjff3422"; // The key used to sign the JWT

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String jwt = httpRequest.getHeader("Authorization");
        if (jwt != null && jwt.startsWith("Bearer ")) {
            jwt = jwt.substring(7); // Remove the "Bearer " prefix
        }

        String userRole = null;
        if (jwt != null) {
            Claims claims = LoginController.decodeJWT(jwt);
            userRole = claims.getAudience();
        }

        if (userRole != null && userRole.equalsIgnoreCase("admin")) {
            chain.doFilter(request, response);
        } else {
            httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN, "You do not have permission to perform this action.");
        }
    }
}
