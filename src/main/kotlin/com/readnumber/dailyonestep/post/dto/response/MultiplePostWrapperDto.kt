package com.readnumber.dailyonestep.post.dto.response


class MultiplePostWrapperDto(
    val totalCount: Int,
    val posts: List<PostDto>?
)