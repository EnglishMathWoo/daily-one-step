package com.readnumber.dailyonestep.security.filter

import com.readnumber.dailyonestep.security.common.AuthHandler
import com.readnumber.dailyonestep.security.token.UserAuthToken
import io.jsonwebtoken.JwtParser

class UserAuthFilter(
        private val pattern: String,
        private val parser: JwtParser,
        private val authHandler: AuthHandler<UserAuthToken>,
) :
    AuthenticationFilter<UserAuthToken>(
        pattern = pattern,
        authHeaderName = "Authorization",
        authHandler = authHandler,
    ) {

    override fun toAuthToken(value: String): UserAuthToken {
        return UserAuthToken(parser, value)
    }
}