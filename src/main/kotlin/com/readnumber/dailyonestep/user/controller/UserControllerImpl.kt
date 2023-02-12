package com.readnumber.dailyonestep.user.controller

import com.readnumber.dailyonestep.common.binding_annotation.ValidUserIdFromAccessToken
import com.readnumber.dailyonestep.user.dto.request.UserChangePasswordDto
import com.readnumber.dailyonestep.user.dto.request.UserModifyDto
import com.readnumber.dailyonestep.user.dto.request.UserSignUpDto
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
        val user = userService.modify(userId, dto)
        return UserWrapperDto.from(user)
    }

    @PutMapping("/passwords")
    override fun changeUserPassword(
        @ValidUserIdFromAccessToken
        userId: Long,
        @Valid @RequestBody
        dto: UserChangePasswordDto
    ): UserWrapperDto {
        val user = userService.changePassword(userId, dto)
        return UserWrapperDto.from(user)
    }

    @GetMapping("/me")
    override fun getMe(
        @ValidUserIdFromAccessToken
        userId: Long
    ): UserWrapperDto {
        val user = userService.getOne(userId)
        return UserWrapperDto.from(user)
    }
}