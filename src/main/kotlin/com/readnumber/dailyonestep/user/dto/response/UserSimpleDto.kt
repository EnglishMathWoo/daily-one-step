package com.readnumber.dailyonestep.user.dto.response

import com.readnumber.dailyonestep.user.User
import com.readnumber.dailyonestep.common.error.exception.InternalServerException

data class UserSimpleDto(
    val id: Long,
    val username: String,
    val name: String?,
) {
    companion object {
        fun from(entity: User?): UserSimpleDto? {
            if (entity == null) {
                return null
            }

            return UserSimpleDto(
                id = entity.id ?: throw InternalServerException("entity의 id(pk)값은 존재해야 합니다."),
                username = entity.username,
                name = entity.name,
            )
        }
    }
}