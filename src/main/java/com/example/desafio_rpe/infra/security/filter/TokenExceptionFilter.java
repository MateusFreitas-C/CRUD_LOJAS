package com.example.desafio_rpe.infra.security.filter;

import java.io.IOException;

import com.example.desafio_rpe.infra.security.exception.InvalidTokenException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class TokenExceptionFilter extends OncePerRequestFilter {

    private final Logger log = LoggerFactory.getLogger(TokenExceptionFilter.class);
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (InvalidTokenException invalidTokenException) {
            log.error(invalidTokenException.getMessage());
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(invalidTokenException.getMessage());
        }
    }
}
