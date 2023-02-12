package com.readnumber.dailyonestep.authentication.controller

import com.readnumber.dailyonestep.authentication.service.AuthenticationService
import com.readnumber.dailyonestep.authentication.dto.AccessTokenRefreshRequestDto
import com.readnumber.dailyonestep.authentication.dto.UserSignInRequestDto
import com.readnumber.dailyonestep.common.binding_annotation.ValidJwtHeader
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class AuthenticationControllerImpl(
    private val authenticationService: AuthenticationService
) : AuthenticationController {
    @PostMapping("/auth/sign-in")
    override fun signIn(
        @RequestBody
        dto: UserSignInRequestDto
    ): Any {
        return authenticationService.signIn(dto)
    }

    @PostMapping("/auth/access-tokens/refresh")
    override fun refreshAccessToken(
        @ValidJwtHeader
        refreshToken: String,
        @RequestBody
        dto: AccessTokenRefreshRequestDto
    ): Any {
        return mapOf(
            "accessToken" to authenticationService.refreshAccessToken(refreshToken = refreshToken, dto.accessToken)
        )
    }

    @PostMapping("/auth/sign-out")
    override fun signOut(
        @ValidJwtHeader
        refreshToken: String
    ): Any {
        authenticationService.releaseRefreshToken(refreshToken)
        return true
    }
}