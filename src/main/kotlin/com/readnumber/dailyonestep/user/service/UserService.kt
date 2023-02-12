package com.readnumber.dailyonestep.user.service

import com.readnumber.dailyonestep.user.dto.request.UserChangePasswordDto
import com.readnumber.dailyonestep.user.dto.request.UserModifyDto
import com.readnumber.dailyonestep.user.dto.request.UserSignUpDto
import com.readnumber.dailyonestep.user.dto.response.UserDto

interface UserService {
    fun signUp(dto: UserSignUpDto): UserDto
    fun changePassword(id: Long, dto: UserChangePasswordDto): UserDto
    fun modify(id: Long, dto: UserModifyDto): UserDto
    fun getOne(id: Long): UserDto
    fun count(): Long
}