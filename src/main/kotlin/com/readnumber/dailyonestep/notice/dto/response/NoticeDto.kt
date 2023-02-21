package com.readnumber.dailyonestep.notice.dto.response

import com.readnumber.dailyonestep.comment.dto.response.CommentDto
import com.readnumber.dailyonestep.common.error.exception.InternalServerException
import com.readnumber.dailyonestep.notice.Notice
import com.readnumber.dailyonestep.user.User
import com.readnumber.dailyonestep.user.dto.response.UserSimpleDto
import java.time.LocalDate
import javax.xml.stream.events.Comment

data class NoticeDto(
    val id: Long,
    val title: String,
    val content: String,
    val favorite: Boolean?,
    val comments: List<CommentDto>?,
    val createdAt: LocalDate?,
    val updatedAt: LocalDate?,
    val createdBy: String?,
    val updatedBy: String?,
) {
    companion object {
        fun from(
            entity: Notice,
            favorite: Boolean? = false
        ): NoticeDto {
            return NoticeDto(
                id = entity.id ?: throw InternalServerException("entity는 id 값이 있어야 합니다."),
                title = entity.title,
                content = entity.content,
                favorite = favorite,
                comments = entity.comments.map { CommentDto.from(it) },
                createdAt = entity.createdAt,
                updatedAt = entity.updatedAt,
                createdBy = entity.createdBy,
                updatedBy = entity.updatedBy
            )
        }
    }
}
