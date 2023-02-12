package com.readnumber.dailyonestep.security.configuration

import io.jsonwebtoken.JwtParser
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.nio.charset.StandardCharsets

@Configuration
class JwtConfiguration {

    @Value("\${authentication.admin.access-token.secret-key}")
    private lateinit var tokenSecretKey: String

    @Bean
    fun parser(): JwtParser {
        val key = Keys.hmacShaKeyFor(tokenSecretKey.toByteArray(StandardCharsets.UTF_8))
        return Jwts.parserBuilder().setSigningKey(key).build()
    }
}