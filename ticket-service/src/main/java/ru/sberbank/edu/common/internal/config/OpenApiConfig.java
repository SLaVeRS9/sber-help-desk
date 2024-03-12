package ru.sberbank.edu.common.internal.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
        info = @Info(
                title = "Sber help desk System Api",
                description = "Help desk System", version = "1.0.0",
                contact = @Contact(
                        name = "Lopusov Viacheslav",
                        email = "vlop@vlop.dev",
                        url = "https://vlop.vlop.dev"
                )
        )
)
public class OpenApiConfig {

}
