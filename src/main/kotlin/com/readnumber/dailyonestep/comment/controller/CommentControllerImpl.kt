package com.readnumber.dailyonestep.comment.controller

import com.readnumber.dailyonestep.comment.dto.request.CommentCreateDto
import com.readnumber.dailyonestep.comment.dto.request.CommentModifyDto
import com.readnumber.dailyonestep.comment.dto.response.CommentWrapperDto
import com.readnumber.dailyonestep.comment.dto.response.MultipleCommentWrapperDto
import com.readnumber.dailyonestep.comment.service.CommentService
import com.readnumber.dailyonestep.common.binding_annotation.ValidUserIdFromAccessToken
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.*

@RequestMapping
@RestController
class CommentControllerImpl(
    private val commentService: CommentService
) : CommentController {

    @PostMapping("/comments")
    override fun createComment(
        @Valid @RequestBody
        dto: CommentCreateDto
    ): CommentWrapperDto {
        return CommentWrapperDto.from(
            commentService.createComment(dto)
        )
    }

    @GetMapping("/notices/{id}/comments")
    override fun getComments(
        @PathVariable(value = "id")
        id: Long
    ): MultipleCommentWrapperDto {
        return commentService.getComments(id)
    }

    @GetMapping("/comments/me")
    override fun getMyComments(
        @ValidUserIdFromAccessToken
        userId: Long,
    ): MultipleCommentWrapperDto{
        return commentService.getMyComments(userId)
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
