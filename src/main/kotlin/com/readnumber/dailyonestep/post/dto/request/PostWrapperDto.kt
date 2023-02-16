package com.readnumber.dailyonestep.post.dto.request

import com.readnumber.dailyonestep.post.dto.response.PostDto

class PostWrapperDto(
    val post: PostDto
) {
    companion object {
        fun from(dto: PostDto): PostWrapperDto {
            return PostWrapperDto(dto)
        }
    }
}