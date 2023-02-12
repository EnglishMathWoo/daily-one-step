package com.readnumber.dailyonestep.user.dto.request


data class UserChangePasswordDto(
    val currentPassword: String,
    val newPassword: String
)