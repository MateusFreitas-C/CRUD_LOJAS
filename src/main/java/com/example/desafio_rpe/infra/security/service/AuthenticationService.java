package com.example.desafio_rpe.infra.security.service;

import com.example.desafio_rpe.dto.SignInDto;
import com.example.desafio_rpe.dto.SignUpDto;
import com.example.desafio_rpe.dto.TokenResponseDao;
import com.example.desafio_rpe.model.User;
import com.example.desafio_rpe.service.UserService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    private final UserService userService;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    public AuthenticationService(UserService userService, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public TokenResponseDao signup(SignUpDto request) {
        if (userService.existsByEmail(request.email())) throw new IllegalArgumentException("User already exists!");

        String password = passwordEncoder.encode(request.password());

        User user = new User(request.name(), request.email(), password);

        userService.saveUser(user);

        String token = jwtService.generateToken(user);

        return TokenResponseDao.builder().token(token).build();
    }

    public TokenResponseDao signin(SignInDto request) {
        User user = (User) userService.userDetailsService().loadUserByUsername(request.email());

        if(passwordEncoder.matches(request.password(), user.getPassword())){
            new UsernamePasswordAuthenticationToken(request.email(), request.password());
        }else {
            throw new IllegalArgumentException("Invalid password");
        }

        String token = jwtService.generateToken(user);

        return TokenResponseDao.builder().token(token).build();
    }
}
