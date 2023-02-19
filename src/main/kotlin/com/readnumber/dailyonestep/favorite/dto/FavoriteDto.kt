package com.readnumber.dailyonestep.favorite.dto

import com.readnumber.dailyonestep.common.error.exception.InternalServerException
import com.readnumber.dailyonestep.favorite.Favorite
import com.readnumber.dailyonestep.user.User
import com.readnumber.dailyonestep.user.dto.response.UserSimpleDto
import java.time.LocalDate

data class FavoriteDto(
    val id: Long,
    val createdAt: LocalDate?,
    val updatedAt: LocalDate?,
    val createdBy: UserSimpleDto?,
    val updatedBy: UserSimpleDto?,
) {
    companion object {
        fun from(
            entity: Favorite?,
            createdBy: User? = null,
            updatedBy: User? = null,
        ): FavoriteDto {
            return FavoriteDto(
                id = entity?.id ?: throw InternalServerException("entity는 id 값이 있어야 합니다."),
                createdAt = entity.createdAt,
                updatedAt = entity.updatedAt,
                createdBy = UserSimpleDto.from(createdBy),
                updatedBy = UserSimpleDto.from(updatedBy)
            )
        }
    }
}
