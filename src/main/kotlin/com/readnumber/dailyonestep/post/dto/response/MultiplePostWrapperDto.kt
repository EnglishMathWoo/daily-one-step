package com.readnumber.dailyonestep.post.dto.response

import io.swagger.v3.oas.annotations.media.Schema

class MultiplePostWrapperDto(
    val totalCount: Long,
    val posts: List<PostDto>
)