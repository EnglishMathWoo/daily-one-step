package com.readnumber.dailyonestep.user.dto.request

import com.readnumber.dailyonestep.user.User
import com.readnumber.dailyonestep.common.validator_annotation.PhoneFormat
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotNull

data class UserModifyDto(
    val name: String? = null,
    @PhoneFormat
    val phone: String? = null,
    @Email
    val email: String? = null
) {
    fun modifyEntity(user: User): User {
        user.name = name ?: user.name
        user.phone = phone ?: user.phone
        user.email = email ?: user.email

        return user
    }
}