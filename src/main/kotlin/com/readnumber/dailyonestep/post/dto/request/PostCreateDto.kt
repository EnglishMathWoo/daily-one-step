package com.readnumber.dailyonestep.post.dto.request

import com.readnumber.dailyonestep.post.Post

class PostCreateDto (
    val title: String,
    val content: String
) {
    fun toEntity(): Post {
        return Post(
            title = title,
            content = content
        )
    }
}