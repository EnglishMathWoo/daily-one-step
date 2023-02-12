package com.readnumber.dailyonestep.common.token_provider

import com.readnumber.dailyonestep.common.token_provider.TokenAudienceEnum.ADMIN_REFRESH_TOKEN
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component

@Component
class AdminRefreshTokenProvider : BaseTokenProvider() {
    @Value("\${authentication.issuer}")
    private lateinit var issuer: String

    @Value("\${authentication.admin.refresh-token.secret-key}")
    private lateinit var tokenSecretKey: String

    @Value("\${authentication.admin.refresh-token.expired-seconds}")
    private lateinit var tokenExpiredSeconds: String

    override fun getExpiredSeconds(): Long {
        return tokenExpiredSeconds.toLong()
    }

    fun generateToken(id: Long, authorId: Long, subject: TokenSubjectEnum): String {
        return super.generateToken(
            issuer = issuer,
            secretKey = tokenSecretKey,
            expiredSeconds = getExpiredSeconds(),
            id = id,
            audience = ADMIN_REFRESH_TOKEN.content,
            subject = subject.content,
            authorId = authorId
        )
    }

    // FIXME: 프론트엔드 로직 확인후 삭제 필요
    fun generateShortToken(id: Long, authorId: Long, subject: TokenSubjectEnum): String {
        return super.generateToken(
            issuer = issuer,
            secretKey = tokenSecretKey,
            expiredSeconds = 60,
            id = id,
            audience = ADMIN_REFRESH_TOKEN.content,
            subject = subject.content,
            authorId = authorId
        )
    }

    fun getAuthenticationFromToken(token: String): Authentication {
        return super.getAuthenticationFromToken(
            secretKey = tokenSecretKey,
            token = token,
            validAudience = ADMIN_REFRESH_TOKEN.content
        )
    }

    fun getAuthenticationIdFromToken(token: String): Long {
        return super.getAuthenticationIdFromToken(
            secretKey = tokenSecretKey,
            token = token,
            validAudience = ADMIN_REFRESH_TOKEN.content
        )
    }
}