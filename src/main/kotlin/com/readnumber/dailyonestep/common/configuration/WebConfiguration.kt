package com.readnumber.dailyonestep.common.configuration

import com.readnumber.dailyonestep.common.binding_annotation.ValidAdminIdFromAccessTokenArgumentResolver
import com.readnumber.dailyonestep.common.binding_annotation.ValidAdminIdFromRefreshTokenArgumentResolver
import com.readnumber.dailyonestep.common.binding_annotation.ValidJwtHeaderArgumentResolver
import org.springframework.context.annotation.Configuration
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.servlet.HandlerInterceptor
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebConfiguration(
    // A) Custom Resolver
    private val jwtHeaderResolver: ValidJwtHeaderArgumentResolver,
    private val userIdAccessResolver: ValidAdminIdFromAccessTokenArgumentResolver,
    private val userIdRefreshResolver: ValidAdminIdFromRefreshTokenArgumentResolver,

    // B) Interceptor
    private val commonLoggingInterceptor: HandlerInterceptor
) : WebMvcConfigurer {

    override fun addArgumentResolvers(resolvers: MutableList<HandlerMethodArgumentResolver>) {
        super.addArgumentResolvers(resolvers)
        resolvers.add(jwtHeaderResolver)
        resolvers.add(userIdAccessResolver)
        resolvers.add(userIdRefreshResolver)
    }

    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(commonLoggingInterceptor)
            .addPathPatterns("/user-api/**")
            .addPathPatterns("/admin-api/**")
    }
}