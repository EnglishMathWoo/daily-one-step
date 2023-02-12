package com.readnumber.dailyonestep.authentication.controller

import com.readnumber.dailyonestep.authentication.dto.AccessTokenRefreshRequestDto
import com.readnumber.dailyonestep.authentication.dto.UserSignInRequestDto

interface AuthenticationController {
    fun signIn(
        dto: UserSignInRequestDto
    ): Any

    fun refreshAccessToken(
        refreshToken: String,
        dto: AccessTokenRefreshRequestDto
    ): Any

    fun signOut(
        refreshToken: String
    ): Any
}