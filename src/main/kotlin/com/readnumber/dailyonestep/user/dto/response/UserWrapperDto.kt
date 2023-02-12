package com.readnumber.dailyonestep.user.dto.response

data class UserWrapperDto(
    val user: UserDto?
) {
    companion object {
        fun from(user: UserDto?): UserWrapperDto {
            return UserWrapperDto(user)
        }
    }
}