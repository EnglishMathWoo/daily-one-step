package com.readnumber.dailyonestep.notice.dto.response

import com.readnumber.dailyonestep.common.error.exception.InternalServerException
import com.readnumber.dailyonestep.notice.Notice
import com.readnumber.dailyonestep.user.User
import com.readnumber.dailyonestep.user.dto.response.UserSimpleDto
import java.time.LocalDateTime

data class NoticeDto(
    val id: Long,
    val title: String,
    val content: String,
    val favorite: Boolean?,
    val createdAt: LocalDateTime?,
    val updatedAt: LocalDateTime?,
    val createdBy: UserSimpleDto?,
    val updatedBy: UserSimpleDto?,
) {
    companion object {
        fun from(
            entity: Notice,
            favorite: Boolean? = false,
            createdBy: User? = null,
            updatedBy: User? = null,
        ): NoticeDto {
            return NoticeDto(
                id = entity.id ?: throw InternalServerException("entity는 id 값이 있어야 합니다."),
                title = entity.title,
                content = entity.content,
                favorite = favorite,
                createdAt = entity.createdAt,
                updatedAt = entity.updatedAt,
                createdBy = UserSimpleDto.from(createdBy),
                updatedBy = UserSimpleDto.from(updatedBy)
            )
        }
    }
}
