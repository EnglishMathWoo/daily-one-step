package com.readnumber.dailyonestep.authentication.dto

data class TokenResponseDto(
    val accessToken: String,
    val refreshToken: String
)