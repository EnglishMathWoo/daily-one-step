package com.readnumber.dailyonestep.common.token_provider

enum class TokenSubjectEnum(
    val content: String
) {
    SIGN_IN("signIn"),
    REFRESH_ACCESS_TOKEN("refreshAccessToken")
    ;
}