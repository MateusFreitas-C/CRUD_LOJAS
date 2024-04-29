package com.example.desafio_rpe.infra.security.config;

import com.example.desafio_rpe.infra.security.filter.JwtAuthenticationFilter;
import com.example.desafio_rpe.infra.security.filter.PasswordExceptionFilter;
import com.example.desafio_rpe.infra.security.filter.TokenExceptionFilter;
import com.example.desafio_rpe.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.header.HeaderWriterFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;


@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final UserService userService;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter, UserService userService) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.userService = userService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http.csrf(AbstractHttpConfigurer::disable).
                authorizeHttpRequests(request -> request.requestMatchers("/auth/**", "swagger-ui/**", "/v3/api-docs/**")
                        .permitAll().anyRequest().authenticated())
                .sessionManagement(manager -> manager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .cors(corsCustomizer -> corsCustomizer.configurationSource(getCorsConfiguration()))

                .authenticationProvider(authenticationProvider()).addFilterBefore(
                        jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class
                )
                .addFilterAfter(new TokenExceptionFilter(), HeaderWriterFilter.class)
                .addFilterAfter(new PasswordExceptionFilter(), HeaderWriterFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userService.userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public CorsConfigurationSource getCorsConfiguration() {
        CorsConfigurationSource config = request -> {
            CorsConfiguration config1 = new CorsConfiguration();
            config1.setAllowedOrigins(Collections.singletonList("http://localhost:8080"));
            config1.setAllowedMethods(Collections.singletonList("*"));
            config1.setAllowCredentials(true);
            config1.setAllowedHeaders(Collections.singletonList("*"));
            config1.setExposedHeaders(Arrays.asList("Authorization"));
            config1.setMaxAge(3600L);
            return config1;
        };
        return config;
    }
}
