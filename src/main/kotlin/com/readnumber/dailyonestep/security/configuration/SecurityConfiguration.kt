package com.readnumber.dailyonestep.security.configuration

import com.readnumber.dailyonestep.common.token_provider.UserAccessTokenProvider
import com.readnumber.dailyonestep.common.token_provider.UserRefreshTokenProvider
import com.readnumber.dailyonestep.security.common.DelegatedAccessDeniedHandler
import com.readnumber.dailyonestep.security.common.DelegatedAuthenticationEntryPoint
import com.readnumber.dailyonestep.security.filter.UserAccessTokenFilter
import com.readnumber.dailyonestep.security.filter.UserRefreshTokenFilter
import com.readnumber.dailyonestep.security.filter.GlobalBaseExceptionFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@Configuration
@EnableWebSecurity
class SecurityConfiguration {

    @Bean
    @Throws(Exception::class)
    fun filterChain(
        http: HttpSecurity,
        accessDeniedHandler: DelegatedAccessDeniedHandler,
        authenticationEntryPoint: DelegatedAuthenticationEntryPoint,
        userAccessTokenProvider: UserAccessTokenProvider,
        userRefreshTokenProvider: UserRefreshTokenProvider,
    ): SecurityFilterChain {

        http.addFilterBefore(
                GlobalBaseExceptionFilter(),
                UsernamePasswordAuthenticationFilter::class.java
            )
            .addFilterBefore(
                UserAccessTokenFilter(userAccessTokenProvider),
                UsernamePasswordAuthenticationFilter::class.java
            )
            .addFilterBefore(
                UserRefreshTokenFilter(userRefreshTokenProvider),
                UsernamePasswordAuthenticationFilter::class.java
            )
            .exceptionHandling()
            .accessDeniedHandler(accessDeniedHandler)
            .authenticationEntryPoint(authenticationEntryPoint)

        http
            .csrf().disable()
            .httpBasic().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .cors().configurationSource(corsConfigurationSource())

        return http.build()
    }

    fun environmentalAllowedOriginPatterns(): List<String>? {
        return listOf(
            "http://localhost:[*]",
        )
    }

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource? {
        val configSource = UrlBasedCorsConfigurationSource()
        val corsConfig = CorsConfiguration()
        corsConfig.allowCredentials = true // js ???????????? credentials??? include??? ??? ???????????? ?????? true??? ??????
        corsConfig.allowedOriginPatterns = environmentalAllowedOriginPatterns() // ????????? ????????? ip ??????
        corsConfig.exposedHeaders = listOf() // client??? ?????? ??? ?????? ?????? ??? ????????? ??????
        corsConfig.addAllowedHeader("*") // ?????? header ?????? ??????
        corsConfig.addAllowedMethod("*") // ?????? post,get,put,delete,patch ?????? ??????
        configSource.registerCorsConfiguration("/**", corsConfig) // ?????? ????????? ?????? corsConfig ??????
        return configSource
    }
}
