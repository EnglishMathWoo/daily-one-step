package com.readnumber.dailyonestep.user.dto.response

import com.readnumber.dailyonestep.common.error.exception.InternalServerException
import com.readnumber.dailyonestep.user.User
import java.time.LocalDateTime

data class UserDto(
    val id: Long,
    val username: String,
    val name: String?,
    val phone: String?,
    val email: String?,
    val createdAt: LocalDateTime?,
    val updatedAt: LocalDateTime?
) {
    companion object {
        fun from(entity: User): UserDto {
            return UserDto(
                id = entity.id ?: throw InternalServerException("entity는 id값이 존재해야한다."),
                username = entity.username,
                name = entity.name,
                phone = entity.phone,
                email = entity.email,
                createdAt = entity.createdAt,
                updatedAt = entity.updatedAt
            )
        }
    }
}