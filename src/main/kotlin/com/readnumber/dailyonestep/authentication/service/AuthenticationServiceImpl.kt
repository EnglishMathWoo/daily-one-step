package com.readnumber.dailyonestep.authentication.service

import com.readnumber.dailyonestep.authentication.UserToken
import com.readnumber.dailyonestep.authentication.UserTokenRepository
import com.readnumber.dailyonestep.authentication.dto.UserSignInRequestDto
import com.readnumber.dailyonestep.authentication.dto.TokenResponseDto
import com.readnumber.dailyonestep.common.entity.AuthorTypeEnum
import com.readnumber.dailyonestep.common.error.exception.*
import com.readnumber.dailyonestep.common.repository.AuthorRepository
import com.readnumber.dailyonestep.common.token_provider.TokenSubjectEnum.*
import com.readnumber.dailyonestep.common.token_provider.UserAccessTokenProvider
import com.readnumber.dailyonestep.common.token_provider.UserRefreshTokenProvider
import com.readnumber.dailyonestep.user.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AuthenticationServiceImpl(
        private val passwordEncoder: PasswordEncoder,
        private val authorRepository: AuthorRepository,
        private val accessTokenProvider: UserAccessTokenProvider,
        private val refreshTokenProvider: UserRefreshTokenProvider,
        private val userRepository: UserRepository,
        private val tokenRepository: UserTokenRepository,
) : AuthenticationService {
    @Transactional
    override fun signIn(dto: UserSignInRequestDto): TokenResponseDto {
        val user = userRepository.findByUsername(dto.username)
            ?: throw NotFoundResourceException("username이 일치하는 어드민을 찾지 못했습니다.")

        if (!passwordEncoder.matches(dto.password, user.encryptedPassword)) {
            throw IncorrectPasswordException("비밀번호가 일치하지 않습니다.")
        }

        val author = authorRepository.findByAuthorTypeAndAssociatedId(
            authorType = AuthorTypeEnum.USER,
            associatedId = user.id!!,
        ) ?: throw InternalServerException("일치하는 author가 존재하지 않습니다.")

        val accessToken = accessTokenProvider.generateToken(
            id = user.id!!, authorId = author.id!!, subject = SIGN_IN
        )
        val refreshToken = refreshTokenProvider.generateToken(
            id = user.id!!, authorId = author.id!!, subject = SIGN_IN
        )

        if (tokenRepository.findByRefreshToken(refreshToken) != null) {
            throw DuplicatedResourceException("중복되는 토큰이 존재합니다.")
        }

        val newUserToken = UserToken(
            accessToken = accessToken,
            refreshToken = refreshToken,
            userId = user.id ?: throw InternalServerException("유저 id 값이 올바르지 않습니다.")
        )

        tokenRepository.save(newUserToken)

        return TokenResponseDto(accessToken = accessToken, refreshToken = refreshToken)
    }

    @Transactional
    override fun refreshAccessToken(refreshToken: String, latelyAccessToken: String): String {
        val userId = refreshTokenProvider.getAuthenticationIdFromToken(refreshToken)

        var userToken = tokenRepository.findByRefreshToken(refreshToken)
            ?: throw NotFoundResourceException("refresh token을 찾을 수 없습니다.")

        if (userToken.accessToken != latelyAccessToken) {
            throw InvalidTokenException("유효한 액세스 토큰이 아닙니다.")
        }

        val author = authorRepository.findByAuthorTypeAndAssociatedId(
            authorType = AuthorTypeEnum.USER,
            associatedId = userId,
        ) ?: throw InternalServerException("일치하는 author가 존재하지 않습니다.")

        val newAccessToken = accessTokenProvider.generateToken(
            id = userId,
            authorId = author.id!!,
            subject = REFRESH_ACCESS_TOKEN,
        )

        userToken.accessToken = newAccessToken
        tokenRepository.save(userToken)

        return newAccessToken
    }

    @Transactional
    override fun releaseRefreshToken(refreshToken: String) {
        val userToken = tokenRepository.findByRefreshToken(refreshToken)
            ?: throw NotFoundResourceException("일치하는 refresh token을 찾지 못했습니다.")

        try {
            tokenRepository.delete(userToken)
        } catch (e: java.lang.Exception) {
            throw InternalServerException("토큰 삭제에 실패했습니다.")
        }
    }
}