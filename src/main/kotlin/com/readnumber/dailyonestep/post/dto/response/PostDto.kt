package com.readnumber.dailyonestep.post.dto.response

import com.readnumber.dailyonestep.comment.Comment
import com.readnumber.dailyonestep.common.error.exception.InternalServerException
import com.readnumber.dailyonestep.post.Post
import com.readnumber.dailyonestep.user.User
import com.readnumber.dailyonestep.user.dto.response.UserSimpleDto
import java.time.LocalDateTime

data class PostDto(
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
            entity: Post,
            favorite: Boolean? = false,
            createdBy: User? = null,
            updatedBy: User? = null,
        ): PostDto {
            return PostDto(
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