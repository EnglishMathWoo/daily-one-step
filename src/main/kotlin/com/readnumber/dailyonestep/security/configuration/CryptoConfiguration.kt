package com.readnumber.dailyonestep.security.configuration

import com.readnumber.dailyonestep.security.crypto.AES256
import com.readnumber.dailyonestep.security.crypto.SymmetricCrypto
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component

@Component
class CryptoConfiguration {

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    fun symmetricCrypto(
        @Value("\${security.crypto.aes.secret-key}")
        secretKey: String
    ): SymmetricCrypto {
        return AES256(secretKey)
    }
}