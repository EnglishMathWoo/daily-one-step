package com.readnumber.dailyonestep.common.configuration

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.security.SecurityRequirement
import io.swagger.v3.oas.models.security.SecurityScheme
import io.swagger.v3.oas.models.servers.Server
import org.springdoc.core.models.GroupedOpenApi
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component

@Component
class OpenAPIConfiguration {

    @Bean
    fun userAPIGroup(): GroupedOpenApi {
        return GroupedOpenApi.builder()
            .group("daily-one-step-api")
            .pathsToMatch("/**")
            .displayName("API Group")
            .build()
    }

    @Bean
    fun openAPI(): OpenAPI {
        return OpenAPI()
            .info(
                Info()
                    .version("1.0.0")
                    .description("API Documentation")
                    .title("DAILY_ONE_STEP")
            )
            .components(
                Components()
                    .addSecuritySchemes(
                        "BearerAuth",
                        SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("Bearer").bearerFormat("JWT")
                            .`in`(SecurityScheme.In.HEADER).name("Authorization")
                    )
            )
            .security(
                listOf(
                    SecurityRequirement().addList("BearerAuth")
                )
            )
            .servers(
                listOf(
                    Server().url("http://localhost").description("local"),
                )
            )
    }
}