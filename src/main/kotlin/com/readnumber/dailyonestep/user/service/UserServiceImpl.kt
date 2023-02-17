package com.readnumber.dailyonestep.user.service

import com.readnumber.dailyonestep.common.error.exception.*
import com.readnumber.dailyonestep.common.token_provider.TokenSubjectEnum
import com.readnumber.dailyonestep.common.token_provider.UserAccessTokenProvider
import com.readnumber.dailyonestep.common.token_provider.UserRefreshTokenProvider
import com.readnumber.dailyonestep.user.User
import com.readnumber.dailyonestep.user.UserRepository
import com.readnumber.dailyonestep.user.UserToken
import com.readnumber.dailyonestep.user.UserTokenRepository
import com.readnumber.dailyonestep.user.dto.request.UserChangePasswordDto
import com.readnumber.dailyonestep.user.dto.request.UserModifyDto
import com.readnumber.dailyonestep.user.dto.request.UserSignInRequestDto
import com.readnumber.dailyonestep.user.dto.request.UserSignUpDto
import com.readnumber.dailyonestep.user.dto.response.TokenResponseDto
import com.readnumber.dailyonestep.user.dto.response.UserDto
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserServiceImpl(
        private val passwordEncoder: PasswordEncoder,
        private val accessTokenProvider: UserAccessTokenProvider,
        private val refreshTokenProvider: UserRefreshTokenProvider,
        private val userRepository: UserRepository,
        private val tokenRepository: UserTokenRepository,
) : UserService {

    @Transactional
    override fun signUp(dto: UserSignUpDto): UserDto {
        if (userRepository.existsByUsername(dto.username)) {
            throw DuplicatedResourceException("중복된 유저가 존재합니다.")
        }
        val encPassword = encryptPassword(dto.password)
        val newUser = dto.toEntity(encPassword)

        userRepository.save(newUser)

        return UserDto.from(newUser)
    }

    @Transactional
    override fun changePassword(id: Long, dto: UserChangePasswordDto): UserDto {
        val user = getUserAndThrowExIfNotExisted(id)
        if (!matchPassword(dto.currentPassword, user.encryptedPassword)) {
            throw IncorrectPasswordException("비밀번호가 일치하지 않습니다.")
        }
        user.encryptedPassword = encryptPassword(dto.newPassword)
        return UserDto.from(user)
    }

    @Transactional
    override fun modifyUser(
        id: Long,
        dto: UserModifyDto
    ): UserDto {
        val user = getUserAndThrowExIfNotExisted(id)
        dto.modifyEntity(user)
        return UserDto.from(user)
    }

    @Transactional
    override fun deleteUser(
        id: Long,
        refreshToken: String
    ) {
        try {
            val userToken = tokenRepository.findByRefreshToken(refreshToken)
                    ?: throw NotFoundResourceException("일치하는 refresh token을 찾지 못했습니다.")

            userRepository.deleteById(id)
            tokenRepository.delete(userToken)
        } catch (e: Exception) {
            when (e) {
                is EmptyResultDataAccessException ->
                    throw NotFoundResourceException("존재하지 않는 유저 입니다.")
            }
            throw InternalServerException("알 수 없는 원인으로 유저 엔티티 삭제에 실패했습니다.")
        }
    }

    @Transactional(readOnly = true)
    override fun getOne(id: Long): UserDto {
        val user = getUserAndThrowExIfNotExisted(id)
        return UserDto.from(user)
    }

    @Transactional(readOnly = true)
    override fun count(): Long {
        return userRepository.count()
    }

    @Transactional
    override fun signIn(dto: UserSignInRequestDto): TokenResponseDto {
        val user = userRepository.findByUsername(dto.username)
            ?: throw NotFoundResourceException("username이 일치하는 어드민을 찾지 못했습니다.")

        if (!passwordEncoder.matches(dto.password, user.encryptedPassword)) {
            throw IncorrectPasswordException("비밀번호가 일치하지 않습니다.")
        }

        val accessToken = accessTokenProvider.generateToken(
            id = user.id!!, subject = TokenSubjectEnum.SIGN_IN
        )
        val refreshToken = refreshTokenProvider.generateToken(
            id = user.id!!, subject = TokenSubjectEnum.SIGN_IN
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

        val userToken = tokenRepository.findByRefreshToken(refreshToken)
            ?: throw NotFoundResourceException("refresh token을 찾을 수 없습니다.")

        if (userToken.accessToken != latelyAccessToken) {
            throw InvalidTokenException("유효한 액세스 토큰이 아닙니다.")
        }

        val newAccessToken = accessTokenProvider.generateToken(
            id = userId,
            subject = TokenSubjectEnum.REFRESH_ACCESS_TOKEN,
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

    private fun matchPassword(rawPassword: String, encPassword: String): Boolean {
        return passwordEncoder.matches(rawPassword, encPassword)
    }

    private fun encryptPassword(rawPassword: String): String {
        return passwordEncoder.encode(rawPassword)
    }

    private fun getUserAndThrowExIfNotExisted(id: Long): User {
        return userRepository.findById(id)
            .orElseThrow { NotFoundResourceException("일치하는 유저를 찾을 수 없습니다.") }
    }
}