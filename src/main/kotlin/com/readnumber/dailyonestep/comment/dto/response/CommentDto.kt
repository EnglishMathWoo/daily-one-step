package com.readnumber.dailyonestep.comment.dto.response

import com.readnumber.dailyonestep.comment.Comment
import com.readnumber.dailyonestep.common.error.exception.InternalServerException
import com.readnumber.dailyonestep.user.User
import java.time.LocalDate

data class CommentDto(
    val id: Long,
    val content: String,
    val createdAt: LocalDate?,
    val updatedAt: LocalDate?,
    val createdBy: String?,
    val updatedBy: String?,
) {
    companion object {
        fun from(
            entity: Comment?
        ): CommentDto {
            return CommentDto(
                id = entity?.id ?: throw InternalServerException("entity는 id 값이 있어야 합니다."),
                content = entity.content,
                createdAt = entity.createdAt,
                updatedAt = entity.updatedAt,
                createdBy = entity.createdBy,
                updatedBy = entity.updatedBy
            )
        }
    }
}
