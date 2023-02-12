package com.readnumber.dailyonestep.security.filter

import com.readnumber.dailyonestep.security.common.AuthHandler
import com.readnumber.dailyonestep.security.token.AdminAuthToken
import io.jsonwebtoken.JwtParser

class AdminAuthFilter(
    private val pattern: String,
    private val parser: JwtParser,
    private val authHandler: AuthHandler<AdminAuthToken>,
) :
    AuthenticationFilter<AdminAuthToken>(
        pattern = pattern,
        authHeaderName = "Authorization",
        authHandler = authHandler,
    ) {

    override fun toAuthToken(value: String): AdminAuthToken {
        return AdminAuthToken(parser, value)
    }
}