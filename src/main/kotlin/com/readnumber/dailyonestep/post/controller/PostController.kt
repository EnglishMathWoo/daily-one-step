package com.readnumber.dailyonestep.post.controller

import com.readnumber.dailyonestep.comment.dto.response.MultipleCommentWrapperDto
import com.readnumber.dailyonestep.post.dto.request.PostCreateDto
import com.readnumber.dailyonestep.post.dto.request.PostModifyDto
import com.readnumber.dailyonestep.post.dto.request.PostSearchQueryParameter
import com.readnumber.dailyonestep.post.dto.response.PostWrapperDto
import com.readnumber.dailyonestep.post.dto.response.MultiplePostWrapperDto
import org.springframework.data.domain.Pageable

interface PostController {
    fun createPost(
        dto: PostCreateDto
    ): PostWrapperDto

    fun getPost(
        id: Long
    ): PostWrapperDto

    fun getMyPosts(
        userId: Long
    ): MultiplePostWrapperDto

    fun searchPosts(
        queryParameter: PostSearchQueryParameter,
        pageable: Pageable,
    ): MultiplePostWrapperDto

    fun modifyPost(
        id: Long,
        dto: PostModifyDto
    ): PostWrapperDto

    fun deletePost(
        id: Long
    ): Boolean
}