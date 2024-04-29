package com.example.desafio_rpe.controller;

import com.example.desafio_rpe.dto.SignInDto;
import com.example.desafio_rpe.dto.SignUpDto;
import com.example.desafio_rpe.dto.TokenResponseDao;
import com.example.desafio_rpe.infra.security.service.AuthenticationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    private final Logger log = LoggerFactory.getLogger(AuthenticationController.class);

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/signup")
    public ResponseEntity<TokenResponseDao> signup(@RequestBody SignUpDto request) {
        log.info("Sign-up executed by {}", request.email());
        return ResponseEntity.status(HttpStatus.CREATED).body(authenticationService.signup(request));
    }

    @PostMapping("/signin")
    public ResponseEntity<TokenResponseDao> signin(@RequestBody SignInDto request) {
        TokenResponseDao response = authenticationService.signin(request);

        log.info("Sign-in executed by {}", request.email());

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
