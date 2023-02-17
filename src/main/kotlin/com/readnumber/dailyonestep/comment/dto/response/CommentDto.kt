package com.readnumber.dailyonestep.comment.dto.response

import com.readnumber.dailyonestep.comment.Comment
import com.readnumber.dailyonestep.common.error.exception.InternalServerException
import com.readnumber.dailyonestep.user.User
import com.readnumber.dailyonestep.user.dto.response.UserSimpleDto
import java.time.LocalDateTime
import java.util.*

data class CommentDto(
    val id: Long,
    val content: String,
    val createdAt: LocalDateTime?,
    val updatedAt: LocalDateTime?,
    val createdBy: UserSimpleDto?,
    val updatedBy: UserSimpleDto?,
) {
    companion object {
        fun from(
            entity: Comment?,
            createdBy: User? = null,
            updatedBy: User? = null,
        ): CommentDto {
            return CommentDto(
                id = entity?.id ?: throw InternalServerException("entity는 id 값이 있어야 합니다."),
                content = entity.content,
                createdAt = entity.createdAt,
                updatedAt = entity.updatedAt,
                createdBy = UserSimpleDto.from(createdBy),
                updatedBy = UserSimpleDto.from(updatedBy)
            )
        }
    }
}