package com.readnumber.dailyonestep.user.dto.request

data class AccessTokenRefreshRequestDto(
    val accessToken: String,
    // NOTE: dto에 1개의 파라미터만 있으면 http 422 에러 발생함.
    val _unknown: String?
)