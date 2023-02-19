package com.readnumber.dailyonestep.user.dto.response

import com.readnumber.dailyonestep.common.error.exception.InternalServerException
import com.readnumber.dailyonestep.user.User
import java.time.LocalDate

data class UserDto(
    val id: Long,
    val username: String,
    val name: String?,
    val phone: String?,
    val createdAt: LocalDate?,
    val updatedAt: LocalDate?
) {
    companion object {
        fun from(entity: User): UserDto {
            return UserDto(
                id = entity.id ?: throw InternalServerException("entity는 id 값이 있어야 합니다."),
                username = entity.username,
                name = entity.name,
                phone = entity.phone,
                createdAt = entity.createdAt,
                updatedAt = entity.updatedAt
            )
        }
    }
}
