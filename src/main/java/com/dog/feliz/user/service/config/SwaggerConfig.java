package com.dog.feliz.user.service.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.Components;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API - Sistema Abrigo Dog Feliz")
                        .version("1.0.0")
                        .description(
                                "Esta API disponibiliza os serviços e recursos do sistema Abrigo Dog Feliz, " +
                                        "permitindo o gerenciamento de adoções, cadastro de animais," +
                                        "voluntários e doações de forma integrada e eficiente."
                        )
                        .contact(new Contact()
                                .name("SPTech School | Projeto Abrigo Dog Feliz")
                                .email("abrigodogfeliz@gmail.com"))
                        .license(new License()
                                .name("Licença Apache 2.0")
                                .url("http://springdoc.org")))
                .addSecurityItem(new SecurityRequirement().addList("Token de autenticação"))
                .components(new Components()
                        .addSecuritySchemes("Token de autenticação", new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                                .description("Insira o token JWT gerado no login")));
    }
}