package com.readnumber.dailyonestep.comment.dto.request

import com.readnumber.dailyonestep.comment.Comment
import com.readnumber.dailyonestep.notice.Notice

class CommentCreateDto (
    val content: String,
    val noticeId: Long
) {
    fun toEntity(
        notice: Notice,
    ): Comment {
        return Comment(
            content,
            notice
        )
    }
}
