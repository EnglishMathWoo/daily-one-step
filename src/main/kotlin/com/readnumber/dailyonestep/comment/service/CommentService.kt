package com.readnumber.dailyonestep.comment.service

import com.readnumber.dailyonestep.comment.dto.request.CommentCreateDto
import com.readnumber.dailyonestep.comment.dto.request.CommentModifyDto
import com.readnumber.dailyonestep.comment.dto.response.CommentDto
import com.readnumber.dailyonestep.comment.dto.response.MultipleCommentWrapperDto

interface CommentService {
    fun createComment(
        dto: CommentCreateDto,
        noticeId: Long
    ): CommentDto

    fun getMyComments(
        username: String
    ): MultipleCommentWrapperDto

    fun modifyComment(
        id: Long,
        dto: CommentModifyDto
    ): CommentDto

    fun deleteComment(id: Long)
}
