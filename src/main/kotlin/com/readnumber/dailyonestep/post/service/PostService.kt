package com.readnumber.dailyonestep.post.service

import com.readnumber.dailyonestep.post.dto.response.PostDto
import com.readnumber.dailyonestep.post.dto.request.PostCreateDto
import com.readnumber.dailyonestep.post.dto.request.PostModifyDto
import com.readnumber.dailyonestep.post.dto.request.PostSearchQueryParameter
import com.readnumber.dailyonestep.post.dto.response.MultiplePostWrapperDto
import org.springframework.data.domain.Pageable

interface PostService {
    fun getPostCount(): Long

    fun createPost(
        dto: PostCreateDto
    ): PostDto

    fun getPost(
        id: Long
    ): PostDto

    fun getMyPosts(
        userId: Long
    ): MultiplePostWrapperDto

    fun findPosts(
        queryParam: PostSearchQueryParameter,
        pageable: Pageable,
    ): MultiplePostWrapperDto

    fun modifyPost(
        id: Long,
        dto: PostModifyDto
    ): PostDto

    fun deletePost(id: Long)
}