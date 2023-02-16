package com.readnumber.dailyonestep.post.dto.request

import com.readnumber.dailyonestep.post.Post

class PostModifyDto (
    val title: String? = null,
    val content: String? = null
) {
    fun modifyEntity(post: Post): Post {
        post.title = title ?: post.title
        post.content = content ?: post.content

        return post
    }
}