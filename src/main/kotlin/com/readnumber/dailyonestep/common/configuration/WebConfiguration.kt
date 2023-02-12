package com.readnumber.dailyonestep.common.configuration

import com.readnumber.dailyonestep.common.binding_annotation.ValidUserIdFromAccessTokenArgumentResolver
import com.readnumber.dailyonestep.common.binding_annotation.ValidUserIdFromRefreshTokenArgumentResolver
import com.readnumber.dailyonestep.common.binding_annotation.ValidJwtHeaderArgumentResolver
import org.springframework.context.annotation.Configuration
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebConfiguration(
    // A) Custom Resolver
        private val jwtHeaderResolver: ValidJwtHeaderArgumentResolver,
        private val userIdAccessResolver: ValidUserIdFromAccessTokenArgumentResolver,
        private val userIdRefreshResolver: ValidUserIdFromRefreshTokenArgumentResolver
) : WebMvcConfigurer {

    override fun addArgumentResolvers(resolvers: MutableList<HandlerMethodArgumentResolver>) {
        super.addArgumentResolvers(resolvers)
        resolvers.add(jwtHeaderResolver)
        resolvers.add(userIdAccessResolver)
        resolvers.add(userIdRefreshResolver)
    }
}