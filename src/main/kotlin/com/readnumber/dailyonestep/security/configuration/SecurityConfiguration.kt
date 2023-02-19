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
            "http://127.0.0.1:8080"
        )
    }

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource? {
        val configSource = UrlBasedCorsConfigurationSource()
        val corsConfig = CorsConfiguration()
        corsConfig.allowCredentials = true // js 요청에서 credentials가 include일 때 응답하기 위해 true로 설정
        corsConfig.allowedOriginPatterns = environmentalAllowedOriginPatterns() // 요청을 허용할 ip 설정
        corsConfig.exposedHeaders = listOf() // client가 읽을 수 있는 헤더 값 리스트 설정
        corsConfig.addAllowedHeader("*") // 모든 header 요청 허용
        corsConfig.addAllowedMethod("*") // 모든 post,get,put,delete,patch 요청 허용
        configSource.registerCorsConfiguration("/**", corsConfig) // 모든 요청에 대해 corsConfig 적용
        return configSource
    }
}
