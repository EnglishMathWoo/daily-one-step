package com.readnumber.dailyonestep.comment.dto.response

class CommentWrapperDto(
    val comment: CommentDto
) {
    companion object {
        fun from(dto: CommentDto): CommentWrapperDto {
            return CommentWrapperDto(dto)
        }
    }
}