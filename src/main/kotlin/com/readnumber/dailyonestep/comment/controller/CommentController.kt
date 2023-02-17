package com.readnumber.dailyonestep.comment.controller

import com.readnumber.dailyonestep.comment.dto.request.CommentCreateDto
import com.readnumber.dailyonestep.comment.dto.request.CommentModifyDto
import com.readnumber.dailyonestep.comment.dto.response.CommentWrapperDto
import com.readnumber.dailyonestep.comment.dto.response.MultipleCommentWrapperDto

interface CommentController {
    fun createComment(
        dto: CommentCreateDto
    ): CommentWrapperDto

    fun getComments(
        id: Long
    ): MultipleCommentWrapperDto

    fun getMyComments(
        userId: Long
    ): MultipleCommentWrapperDto

    fun modifyComment(
        id: Long,
        dto: CommentModifyDto
    ): CommentWrapperDto

    fun deleteComment(
        id: Long
    ): Boolean
}