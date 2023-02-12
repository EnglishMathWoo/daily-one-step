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
            .group("user-api")
            .pathsToMatch("/user-api/**")
            .displayName("Account API Group")
            .build()
    }

    @Bean
    fun adminAPIGroup(): GroupedOpenApi {
        return GroupedOpenApi.builder()
            .group("admin-api")
            .pathsToMatch("/admin-api/**")
            .displayName("Admin API Group")
            .build()
    }

    @Bean
    fun openAPI(): OpenAPI {
        return OpenAPI()
            .info(
                Info()
                    // front-end 에서 info 내 version이 명시되어야만 파싱이 가능하다고 하였습니다.
                    // 현재 버전에서는 Info 버전(1.0.0)을 고정합니다.
                    .version("1.0.0")
                    .description("신규 API Documentation")
                    .title("READNUMBER😎")
            )
            .components(
                Components()
                    .addSecuritySchemes(
                        "BearerAuth",
                        SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("Bearer").bearerFormat("JWT")
                            .`in`(SecurityScheme.In.HEADER).name("Authorization")
                    )
                    .addSecuritySchemes(
                        "User-Temp-Auth",
                        SecurityScheme().type(SecurityScheme.Type.APIKEY)
                            .`in`(SecurityScheme.In.HEADER).name("Authorization")
                    )
            )
            .security(
                listOf(
                    SecurityRequirement().addList("BearerAuth"),
                    SecurityRequirement().addList("User-Temp-Auth")
                )
            )
            .servers(
                listOf(
                    Server().url("http://localhost").description("local"),
                    Server().url("https://development-api.readnumber.tax").description("development"),
                    Server().url("https://staging-api.readnumber.tax").description("staging")

                )
            )
    }
}