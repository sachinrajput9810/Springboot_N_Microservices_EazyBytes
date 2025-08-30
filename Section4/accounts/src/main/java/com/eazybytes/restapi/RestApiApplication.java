package com.eazybytes.restapi;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
@OpenAPIDefinition(
        info = @Info(
                title = "Accounts Microservice Rest API Documentation",
                version = "1.0",
                description = "EazyBank Accounts Microservice Rest API Documentation" ,
                license = @License(name = "EazyBank", url = "https://eazybank.com") ,
                contact = @Contact(
                        name = "EazyBank" ,
                        email = "eazybank@gmail.com" ,
                        url = "eazybank.com"
                )
        ) ,
        externalDocs = @ExternalDocumentation(
                description = "EazyBank Accounts Microservice Rest API Documentation" ,
                url = "https://eazybank.com"
        )
)
public class RestApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(RestApiApplication.class, args);
    }

}
