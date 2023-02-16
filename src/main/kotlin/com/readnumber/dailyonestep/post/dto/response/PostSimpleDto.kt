package com.readnumber.dailyonestep.post.dto.response

import com.readnumber.dailyonestep.common.error.exception.InternalServerException
import com.readnumber.dailyonestep.post.Post

data class PostSimpleDto(
    val id: Long,
    val title: String,
    val content: String,

) {
    companion object {
        fun from(
            entity: Post?
        ): PostSimpleDto? {
            if (entity == null) {
                return null
            }

            return PostSimpleDto(
                id = entity.id ?: throw InternalServerException("entity는 id 값이 있어야 합니다."),
                title = entity.title,
                content = entity.content
            )
        }
    }
}