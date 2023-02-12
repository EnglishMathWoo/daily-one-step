package com.readnumber.dailyonestep.security.configuration

import com.readnumber.dailyonestep.common.token_provider.AdminAccessTokenProvider
import com.readnumber.dailyonestep.common.token_provider.AdminRefreshTokenProvider
import com.readnumber.dailyonestep.security.common.Authority
import com.readnumber.dailyonestep.security.common.DelegatedAccessDeniedHandler
import com.readnumber.dailyonestep.security.common.DelegatedAuthenticationEntryPoint
import com.readnumber.dailyonestep.security.crypto.SymmetricCrypto
import com.readnumber.dailyonestep.security.filter.AdminAccessTokenFilter
import com.readnumber.dailyonestep.security.filter.AdminRefreshTokenFilter
import com.readnumber.dailyonestep.security.filter.GlobalBaseExceptionFilter
import com.readnumber.dailyonestep.security.handler.AdminAuthHandler
import io.jsonwebtoken.JwtParser
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
        adminAuthHandler: AdminAuthHandler,
        // 클라 유형별로 토큰을 파싱하는 모듈
        symmetricCrypto: SymmetricCrypto,
        jwtParser: JwtParser,
        // security exception handler (GlobalExceptionHandler 와 일괄 처리)
        accessDeniedHandler: DelegatedAccessDeniedHandler,
        authenticationEntryPoint: DelegatedAuthenticationEntryPoint,
        // Temp
        adminAccessTokenProvider: AdminAccessTokenProvider,
        adminRefreshTokenProvider: AdminRefreshTokenProvider,
    ): SecurityFilterChain {

        http
            .authorizeHttpRequests()
            .requestMatchers("/user-api/auth/**").permitAll()
            .requestMatchers("/user-api/**").hasAnyRole(Authority.USER)
            .requestMatchers("/admin-api/**").hasAnyRole(Authority.ADMIN)
            .requestMatchers("/**").permitAll()
            .and()
            .addFilterBefore(
                GlobalBaseExceptionFilter(),
                UsernamePasswordAuthenticationFilter::class.java
            )
            .addFilterBefore(
                AdminAccessTokenFilter(adminAccessTokenProvider),
                UsernamePasswordAuthenticationFilter::class.java
            )
            .addFilterBefore(
                AdminRefreshTokenFilter(adminRefreshTokenProvider),
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
            "http://localhost:[*]"
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