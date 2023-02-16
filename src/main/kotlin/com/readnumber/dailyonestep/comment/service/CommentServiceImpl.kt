package com.readnumber.dailyonestep.comment.service

import com.readnumber.dailyonestep.comment.Comment
import com.readnumber.dailyonestep.comment.CommentRepository
import com.readnumber.dailyonestep.comment.dto.request.CommentCreateDto
import com.readnumber.dailyonestep.comment.dto.request.CommentModifyDto
import com.readnumber.dailyonestep.comment.dto.response.CommentDto
import com.readnumber.dailyonestep.comment.dto.response.MultipleCommentWrapperDto
import com.readnumber.dailyonestep.common.error.exception.InternalServerException
import com.readnumber.dailyonestep.common.error.exception.NotFoundResourceException
import com.readnumber.dailyonestep.post.Post
import com.readnumber.dailyonestep.post.PostRepository
import com.readnumber.dailyonestep.user.User
import com.readnumber.dailyonestep.user.UserRepository
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CommentServiceImpl(
    private val userRepository: UserRepository,
    private val postRepository: PostRepository,
    private val commentRepository: CommentRepository
) : CommentService {
    @Transactional(readOnly = true)
    override fun getCommentCount(): Long {
        return commentRepository.count()
    }

    @Transactional
    override fun createComment(
        dto: CommentCreateDto
    ): CommentDto {
        val post = innerGetPost(dto.postId)
        val comment = commentRepository.save(
            dto.toEntity(post)
        )
        val createdBy = innerGetUser(comment.createdBy!!)

        return CommentDto.from(comment, createdBy)
    }

    @Transactional(readOnly = true)
    override fun getMyComments(
        userId: Long
    ): MultipleCommentWrapperDto {
        val comments = innerGetMyComments(userId)

        return MultipleCommentWrapperDto(
            totalCount = comments.size,
            comments = comments.map{ CommentDto.from(it, innerGetUser(it.createdBy!!), innerGetUser(it.updatedBy!!)) }
        )
    }

    @Transactional
    override fun modifyComment(
        id: Long,
        dto: CommentModifyDto
    ): CommentDto {
        val modifiedComment = dto.modifyEntity(innerGetComment(id))
        val comment = commentRepository.save(modifiedComment)
        val createdBy = innerGetUser(comment.createdBy!!)
        val updatedBy = innerGetUser(comment.updatedBy!!)

        return CommentDto.from(comment , createdBy, updatedBy)
    }

    @Transactional
    override fun deleteComment(id: Long) {
        try {
            commentRepository.deleteById(id)
        } catch (e: Exception) {
            when (e) {
                is EmptyResultDataAccessException ->
                    throw NotFoundResourceException("존재하지 않는 댓글입니다.")
            }
            throw InternalServerException("알 수 없는 원인으로 댓글 엔티티 삭제에 실패했습니다.")
        }
    }

    fun innerGetUser(id: Long): User {
        return userRepository.findById(id)
            .orElseThrow { throw NotFoundResourceException("일치하는 유저를 찾을 수 없습니다.") }
    }

    fun innerGetPost(postId: Long): Post {
        return postRepository.findById(postId)
            .orElseThrow { throw NotFoundResourceException("일치하는 게시글을 찾을 수 없습니다.") }
    }

    fun innerGetComment(id: Long): Comment {
        return commentRepository.findById(id)
            .orElseThrow { throw NotFoundResourceException("일치하는 댓글을 찾을 수 없습니다.") }
    }

    fun innerGetMyComments(userId: Long): List<Comment> {
        return commentRepository.findAllByUserId(userId)
            .orElseThrow { throw NotFoundResourceException("일치하는 내 댓글을 찾을 수 없습니다.") }
    }
}