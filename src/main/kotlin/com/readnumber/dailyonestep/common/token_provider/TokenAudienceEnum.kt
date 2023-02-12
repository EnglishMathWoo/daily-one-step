package com.readnumber.dailyonestep.common.token_provider

enum class TokenAudienceEnum(
    val content: String
) {
    USER_ACCESS_TOKEN("userAccessToken"),
    USER_REFRESH_TOKEN("userRefreshToken"),
    ADMIN_ACCESS_TOKEN("adminAccessToken"),
    ADMIN_REFRESH_TOKEN("adminRefreshToken"),
    ;
}