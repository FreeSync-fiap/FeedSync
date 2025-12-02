package br.com.feedsync.FeedSync.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI hospidata () {
        return new OpenAPI()
                .info(
                        new Info()
                                .title("FeedSync API")
                                .description("Projeto desenvolvido durante o Tech Challenger")
                                .version("v1.0.0")
                                .license(new License().name("Apache 2.0").url("https://github.com/FreeSync-fiap/FeedSync"))
                );
    }

}
