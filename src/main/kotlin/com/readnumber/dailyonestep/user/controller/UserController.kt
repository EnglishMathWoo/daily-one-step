package com.readnumber.dailyonestep.user.controller

import com.readnumber.dailyonestep.user.dto.request.*
import com.readnumber.dailyonestep.user.dto.response.UserWrapperDto

interface UserController {

    fun createUser(
        dto: UserSignUpDto
    ): UserWrapperDto

    fun getMe(
        userId: Long
    ): UserWrapperDto

    fun modifyUser(
        userId: Long,
        dto: UserModifyDto
    ): UserWrapperDto

    fun deleteUser(
        id: Long,
        refreshToken: String
    ): Boolean

    fun signIn(
        dto: UserSignInRequestDto
    ): Any

    fun refreshAccessToken(
        refreshToken: String,
        dto: AccessTokenRefreshRequestDto
    ): Any

    fun signOut(
        refreshToken: String
    ): Any
}
