package com.readnumber.dailyonestep.comment.service

import com.readnumber.dailyonestep.comment.Comment
import com.readnumber.dailyonestep.comment.CommentRepository
import com.readnumber.dailyonestep.comment.dto.request.CommentCreateDto
import com.readnumber.dailyonestep.comment.dto.request.CommentModifyDto
import com.readnumber.dailyonestep.comment.dto.response.CommentDto
import com.readnumber.dailyonestep.comment.dto.response.MultipleCommentWrapperDto
import com.readnumber.dailyonestep.common.error.exception.InternalServerException
import com.readnumber.dailyonestep.common.error.exception.NotFoundResourceException
import com.readnumber.dailyonestep.notice.Notice
import com.readnumber.dailyonestep.notice.NoticeRepository
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CommentServiceImpl(
    private val noticeRepository: NoticeRepository,
    private val commentRepository: CommentRepository
) : CommentService {
    @Transactional
    override fun createComment(
        dto: CommentCreateDto,
        noticeId: Long
    ): CommentDto {
        val notice = innerGetNotice(noticeId)
        val comment = commentRepository.save(
            dto.toEntity(notice)
        )

        return CommentDto.from(comment)
    }

    @Transactional(readOnly = true)
    override fun getMyComments(
        username: String
    ): MultipleCommentWrapperDto {
        val comments = innerGetMyComments(username)

        return MultipleCommentWrapperDto(
            totalCount = comments?.size ?: 0,
            comments = comments?.map { CommentDto.from(it) }
        )
    }

    @Transactional
    override fun modifyComment(
        id: Long,
        dto: CommentModifyDto
    ): CommentDto {
        val modifiedComment = dto.modifyEntity(innerGetComment(id))

        return CommentDto.from(modifiedComment)
    }

    @Transactional
    override fun deleteComment(id: Long) {
        try {
            commentRepository.deleteById(id)
        } catch (e: Exception) {
            when (e) {
                is EmptyResultDataAccessException ->
                    throw NotFoundResourceException("???????????? ?????? ?????? ?????????.")
            }
            throw InternalServerException("??? ??? ?????? ???????????? ?????? ????????? ????????? ??????????????????.")
        }
    }

    private fun innerGetNotice(noticeId: Long): Notice {
        return noticeRepository.findById(noticeId)
            .orElseThrow { throw NotFoundResourceException("???????????? ???????????? ?????? ??? ????????????.") }
    }

    private fun innerGetComment(id: Long): Comment {
        return commentRepository.findById(id)
            .orElseThrow { throw NotFoundResourceException("???????????? ????????? ?????? ??? ????????????.") }
    }

    private fun innerGetMyComments(username: String): List<Comment>? {
        return commentRepository.findAllByUsername(username)
    }
}
