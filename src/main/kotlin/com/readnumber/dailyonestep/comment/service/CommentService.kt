package com.readnumber.dailyonestep.comment.service

import com.readnumber.dailyonestep.comment.dto.request.CommentCreateDto
import com.readnumber.dailyonestep.comment.dto.request.CommentModifyDto
import com.readnumber.dailyonestep.comment.dto.response.CommentDto
import com.readnumber.dailyonestep.comment.dto.response.MultipleCommentWrapperDto
import com.readnumber.dailyonestep.post.dto.response.PostDto
import org.springframework.transaction.annotation.Transactional

interface CommentService {
    fun getCommentCount(): Long

    fun createComment(
        dto: CommentCreateDto
    ): CommentDto

    fun getComments(
        id: Long
    ): MultipleCommentWrapperDto

    fun getMyComments(
        userId: Long
    ): MultipleCommentWrapperDto

    fun modifyComment(
        id: Long,
        dto: CommentModifyDto
    ): CommentDto

    fun deleteComment(id: Long)
}