package com.example.desafio_rpe;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Desafio  RPE", version = "1", description = "Projeto desenvolvido para a vaga de Estágio Back-End"))
public class DesafioRpeApplication {

    public static void main(String[] args) {
        SpringApplication.run(DesafioRpeApplication.class, args);
    }

}
