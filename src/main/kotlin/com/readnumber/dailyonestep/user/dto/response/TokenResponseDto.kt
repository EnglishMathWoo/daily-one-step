package com.readnumber.dailyonestep.user.dto.response

data class TokenResponseDto(
    val accessToken: String,
    val refreshToken: String
)