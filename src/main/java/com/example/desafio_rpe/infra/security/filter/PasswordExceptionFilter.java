package com.example.desafio_rpe.infra.security.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class PasswordExceptionFilter extends OncePerRequestFilter {

    private final Logger log = LoggerFactory.getLogger(PasswordExceptionFilter.class);
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (IllegalArgumentException illegalArgumentException) {
            log.error(illegalArgumentException.getMessage());
            response.setCharacterEncoding("UTF-8");
            response.setStatus(HttpStatus.FORBIDDEN.value());
            response.getWriter().write(illegalArgumentException.getMessage());
        }
    }
}
