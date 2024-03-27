package com.blogsystem.openapi.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI().info(buildInfo())
                .externalDocs(new ExternalDocumentation()
                        .description("Github")
                        .url("https://github.com/ngohoang211020"))
                .addSecurityItem(new SecurityRequirement()
                        .addList("Bearer Token"))
                .components(
                        new Components()
                                .addSecuritySchemes("Bearer Token",
                                        new SecurityScheme()
                                                .name("Bearer Token")
                                                .type(SecurityScheme.Type.HTTP)
                                                .scheme("bearer")
                                                .bearerFormat("JWT")
                                )
                ).addServersItem(new Server().url("https://api-blogsystem.com")
                        .description("Blog System API"))
                .addServersItem(new Server().url("https://blogsystem.com")
                        .description("Blog System WEB"));
    }

    private Info buildInfo() {
        var contact = new Contact()
                .email("hoanggg2110@gmail.com")
                .name("Developer: Ngo Hoang")
                .url("https://www.facebook.com/hoangngo2110");
        var license = new License()
                .name("Apache 2.0")
                .url("https://www.apache.org/licenses/LICENSE-2.0.html");

        return new Info()
                .title("Blog System REST API")
                .version("1.0")
                .description("This is a documents for Blog System Blog System REST API")
                .contact(contact)
                .license(license);
    }
}
