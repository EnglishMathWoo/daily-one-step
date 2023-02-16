package com.readnumber.dailyonestep.comment.dto.request

import com.readnumber.dailyonestep.comment.Comment

class CommentModifyDto (
    val content: String? = null
) {
    fun modifyEntity(comment: Comment): Comment {
        comment.content = content ?: comment.content

        return comment
    }
}