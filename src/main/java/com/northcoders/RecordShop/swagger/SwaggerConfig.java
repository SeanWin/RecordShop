package com.northcoders.RecordShop.swagger;

import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI(@Value("v1") String appVersion) {
        return new OpenAPI()
                .info(new Info()
                        .title("💿Record Shop API")
                        .version(appVersion)
                        .description("API documentation for the Record Shop project"));
    }
}
