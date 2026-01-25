package ru.zhidev.lunchvotingsystem.app.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
//https://sabljakovich.medium.com/adding-basic-auth-authorization-option-to-openapi-swagger-documentation-java-spring-95abbede27e9
@SecurityScheme(
        name = "basicAuth",
        type = SecuritySchemeType.HTTP,
        scheme = "basic"
)
@OpenAPIDefinition(
        info = @Info(
                title = "Lunch Voting System API",
                version = "1.0",
                description = """
                        REST API for lunch voting with daily menus and time-based voting rules
                        <p><b>Тестовые креденшелы:</b><br>
                                                - user@yandex.ru / password<br>
                                                - admin@gmail.com / admin<br>
                                                - guest@gmail.com / guest</p>
                        """,
                contact = @Contact(name = "Ilya Zharinov", email = "zharinov_ilia@mail.ru")
        )
)
public class OpenApiConfig {

    @Bean
    public GroupedOpenApi api() {
        return GroupedOpenApi.builder()
                .group("REST API")
                .pathsToMatch("/api/**")
                .build();
    }
}