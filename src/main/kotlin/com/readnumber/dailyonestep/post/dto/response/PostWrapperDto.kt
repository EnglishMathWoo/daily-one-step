package com.readnumber.dailyonestep.post.dto.response

class PostWrapperDto(
    val post: PostDto
) {
    companion object {
        fun from(dto: PostDto): PostWrapperDto {
            return PostWrapperDto(dto)
        }
    }
}