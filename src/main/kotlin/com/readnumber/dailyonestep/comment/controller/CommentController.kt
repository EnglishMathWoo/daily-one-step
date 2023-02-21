package com.readnumber.dailyonestep.comment.controller

import com.readnumber.dailyonestep.comment.dto.request.CommentCreateDto
import com.readnumber.dailyonestep.comment.dto.request.CommentModifyDto
import com.readnumber.dailyonestep.comment.dto.response.CommentWrapperDto
import com.readnumber.dailyonestep.comment.dto.response.MultipleCommentWrapperDto

interface CommentController {
    fun createComment(
        dto: CommentCreateDto,
        noticeId: Long
    ): CommentWrapperDto

    fun getMyComments(
        username: String
    ): MultipleCommentWrapperDto

    fun modifyComment(
        id: Long,
        dto: CommentModifyDto
    ): CommentWrapperDto

    fun deleteComment(
        id: Long
    ): Boolean
}
