package com.readnumber.dailyonestep.user

import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash

@RedisHash(value = "user_tokens")
class UserToken(
    @Id
    val refreshToken: String,
    var accessToken: String,
    val userId: Long,
)