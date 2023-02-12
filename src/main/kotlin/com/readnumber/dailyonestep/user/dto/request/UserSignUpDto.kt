package com.readnumber.dailyonestep.user.dto.request

import com.readnumber.dailyonestep.user.User
import com.readnumber.dailyonestep.common.validator_annotation.PhoneFormat
import jakarta.validation.constraints.Email

data class UserSignUpDto(
    val username: String,
    val password: String,
    val name: String,
    @PhoneFormat
    val phone: String?,
    @Email
    val email: String?
) {
    fun toEntity(encryptedPassword: String): User = User(
        username = username,
        encryptedPassword = encryptedPassword,
        name = name,
        email = email,
        phone = phone
    )
}