package com.readnumber.dailyonestep.authentication.service

import com.readnumber.dailyonestep.authentication.dto.UserSignInRequestDto
import com.readnumber.dailyonestep.authentication.dto.TokenResponseDto

interface AuthenticationService {
    fun signIn(dto: UserSignInRequestDto): TokenResponseDto
    fun refreshAccessToken(refreshToken: String, latelyAccessToken: String): String
    fun releaseRefreshToken(refreshToken: String)
}