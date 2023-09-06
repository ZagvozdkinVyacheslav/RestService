package com.example.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(
                title = "\n" +
                        "A system for working with citizens",
                description = "This system can send requests get, post, put, delete", version = "1.0.0",
                contact = @Contact(
                        name = "Zagvozdkin Vyacheslav",
                        email = "zagvozdkin12@gmail.com"
                )
        )
)
@Configuration
public class OpenApiConfig {
}
