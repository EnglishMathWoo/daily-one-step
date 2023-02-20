package com.readnumber.dailyonestep.user.controller

import com.readnumber.dailyonestep.common.binding_annotation.ValidJwtHeader
import com.readnumber.dailyonestep.common.binding_annotation.ValidUserIdFromAccessToken
import com.readnumber.dailyonestep.common.binding_annotation.ValidUserIdFromRefreshToken
import com.readnumber.dailyonestep.user.dto.request.*
import com.readnumber.dailyonestep.user.dto.response.UserWrapperDto
import com.readnumber.dailyonestep.user.service.UserService
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.*

@RequestMapping("/users")
@RestController
class UserControllerImpl(
    private val userService: UserService
) : UserController {

    @PostMapping
    override fun createUser(
        @Valid @RequestBody
        dto: UserSignUpDto
    ): UserWrapperDto {
        val user = userService.signUp(dto)
        return UserWrapperDto.from(user)
    }

    @PatchMapping
    override fun modifyUser(
        @ValidUserIdFromAccessToken
        userId: Long,
        @Valid @RequestBody
        dto: UserModifyDto
    ): UserWrapperDto {
        val user = userService.modifyUser(userId, dto)
        return UserWrapperDto.from(user)
    }

    @DeleteMapping
    override fun deleteUser(
        @ValidUserIdFromRefreshToken
        id: Long,
        @ValidJwtHeader
        refreshToken: String
    ): Boolean {
        userService.deleteUser(id, refreshToken)
        return true
    }

    @GetMapping("/me")
    override fun getMe(
        @ValidUserIdFromAccessToken
        userId: Long
    ): UserWrapperDto {
        val user = userService.getOne(userId)
        return UserWrapperDto.from(user)
    }

    @PostMapping("/sign-in")
    override fun signIn(
        @RequestBody
        dto: UserSignInRequestDto
    ): Any {
        return userService.signIn(dto)
    }

    @PostMapping("/access-tokens/refresh")
    override fun refreshAccessToken(
        @ValidJwtHeader
        refreshToken: String,
        @RequestBody
        dto: AccessTokenRefreshRequestDto
    ): Any {
        return mapOf(
            "accessToken" to userService.refreshAccessToken(refreshToken = refreshToken, dto.accessToken)
        )
    }

    @PostMapping("/sign-out")
    override fun signOut(
        @ValidJwtHeader
        refreshToken: String
    ): Any {
        userService.releaseRefreshToken(refreshToken)
        return true
    }
}
