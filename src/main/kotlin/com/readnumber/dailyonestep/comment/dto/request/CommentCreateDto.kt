package com.readnumber.dailyonestep.comment.dto.request

import com.readnumber.dailyonestep.comment.Comment
import com.readnumber.dailyonestep.post.Post

class CommentCreateDto (
    val content: String,
    val postId: Long
) {
    fun toEntity(
        post: Post,
    ): Comment {
        return Comment(
            content,
            post
        )
    }
}