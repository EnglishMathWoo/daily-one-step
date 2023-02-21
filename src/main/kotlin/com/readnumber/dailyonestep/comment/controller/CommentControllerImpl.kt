package com.readnumber.dailyonestep.comment.controller

import com.readnumber.dailyonestep.comment.dto.request.CommentCreateDto
import com.readnumber.dailyonestep.comment.dto.request.CommentModifyDto
import com.readnumber.dailyonestep.comment.dto.response.CommentWrapperDto
import com.readnumber.dailyonestep.comment.dto.response.MultipleCommentWrapperDto
import com.readnumber.dailyonestep.comment.service.CommentService
import com.readnumber.dailyonestep.common.binding_annotation.ValidUsernameFromAccessToken
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.*

@RequestMapping
@RestController
class CommentControllerImpl(
    private val commentService: CommentService
) : CommentController {

    @PostMapping("/notices/{noticeId}/comments")
    override fun createComment(
        @Valid @RequestBody
        dto: CommentCreateDto,
        @PathVariable(value = "noticeId")
        noticeId: Long
    ): CommentWrapperDto {
        return CommentWrapperDto.from(
            commentService.createComment(dto, noticeId)
        )
    }

    @GetMapping("/comments/me")
    override fun getMyComments(
        @ValidUsernameFromAccessToken
        username: String,
    ): MultipleCommentWrapperDto{
        return commentService.getMyComments(username)
    }

    @PatchMapping("/comments/{id}")
    override fun modifyComment(
        @PathVariable(value = "id")
        id: Long,
        @Valid @RequestBody
        dto: CommentModifyDto
    ): CommentWrapperDto {
        return CommentWrapperDto.from(
            commentService.modifyComment(id, dto)
        )
    }

    @DeleteMapping("/comments/{id}")
    override fun deleteComment(
        @PathVariable(value = "id")
        id: Long,
    ): Boolean {
        commentService.deleteComment(id)
        return true
    }

}
