package com.readnumber.dailyonestep.user.dto.request

import com.readnumber.dailyonestep.user.User
import com.readnumber.dailyonestep.common.validator_annotation.PhoneFormat
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotNull

data class UserModifyDto(
    @NotNull(message = "이름은 필수 입력값 입니다.")
    val name: String,
    @PhoneFormat
    val phone: String? = null,
    @Email
    val email: String? = null
) {
    fun modifyEntity(user: User): User {
        user.name = name
        user.phone = phone
        user.email = email
        return user
    }
}