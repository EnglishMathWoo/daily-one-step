package com.readnumber.dailyonestep.user.service

import com.readnumber.dailyonestep.user.dto.request.UserChangePasswordDto
import com.readnumber.dailyonestep.user.dto.request.UserModifyDto
import com.readnumber.dailyonestep.user.dto.request.UserSignInRequestDto
import com.readnumber.dailyonestep.user.dto.request.UserSignUpDto
import com.readnumber.dailyonestep.user.dto.response.TokenResponseDto
import com.readnumber.dailyonestep.user.dto.response.UserDto

interface UserService {
    fun signUp(
        dto: UserSignUpDto
    ): UserDto

    fun changePassword(
        id: Long,
        dto: UserChangePasswordDto
    ): UserDto

    fun modifyUser(
        id: Long,
        dto: UserModifyDto
    ): UserDto

    fun deleteUser(
        id: Long,
        refreshToken: String
    )

    fun getOne(
        id: Long
    ): UserDto

    fun count(): Long

    fun signIn(
        dto: UserSignInRequestDto
    ): TokenResponseDto

    fun refreshAccessToken(
        refreshToken: String,
        latelyAccessToken: String
    ): String

    fun releaseRefreshToken(
        refreshToken: String
    )
}