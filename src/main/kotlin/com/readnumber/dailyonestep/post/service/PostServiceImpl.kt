package com.readnumber.dailyonestep.post.service

import com.readnumber.dailyonestep.common.error.exception.InternalServerException
import com.readnumber.dailyonestep.common.error.exception.NotFoundResourceException
import com.readnumber.dailyonestep.post.Post
import com.readnumber.dailyonestep.post.PostRepository
import com.readnumber.dailyonestep.post.PostRepositorySupport
import com.readnumber.dailyonestep.post.dto.response.PostDto
import com.readnumber.dailyonestep.post.dto.request.PostCreateDto
import com.readnumber.dailyonestep.post.dto.request.PostModifyDto
import com.readnumber.dailyonestep.post.dto.request.PostSearchQueryParameter
import com.readnumber.dailyonestep.post.dto.response.MultiplePostWrapperDto
import com.readnumber.dailyonestep.user.User
import com.readnumber.dailyonestep.user.UserRepository
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PostServiceImpl(
    private val userRepository: UserRepository,
    private val postRepository: PostRepository,
    private val postRepositorySupport: PostRepositorySupport
) : PostService {
    @Transactional(readOnly = true)
    override fun getPostCount(): Long {
        return postRepository.count()
    }

    @Transactional
    override fun createPost(
        dto: PostCreateDto
    ): PostDto {
        val post = postRepository.save(
            dto.toEntity()
        )
        val createdBy = innerGetUser(post.createdBy!!)

        return PostDto.from(post, createdBy)
    }

    @Transactional(readOnly = true)
    override fun getPost(
        id: Long
    ): PostDto {
        val post = innerGetPost(id)
        val createdBy = innerGetUser(post.createdBy!!)
        val updatedBy = innerGetUser(post.updatedBy!!)

        return PostDto.from(post, createdBy, updatedBy)
    }

    @Transactional(readOnly = true)
    override fun getMyPosts(
        userId: Long
    ): MultiplePostWrapperDto {
        val posts = innerGetMyPosts(userId)

        return MultiplePostWrapperDto(
            totalCount = posts?.size ?: 0,
            posts = posts?.map { PostDto.from(
                it,
                innerGetUser(it.createdBy!!),
                innerGetUser(it.updatedBy!!))
            }
        )
    }

    @Transactional(readOnly = true)
    override fun findPosts(
        queryParam: PostSearchQueryParameter,
        pageable: Pageable
    ): MultiplePostWrapperDto {
        val page = postRepositorySupport.searchAll(queryParam, pageable)

        return MultiplePostWrapperDto(
            totalCount = page.totalElements.toInt(),
            posts = page.content.map { PostDto.from(
                it,
                innerGetUser(it.createdBy!!),
                innerGetUser(it.updatedBy!!))
            }
        )
    }

    @Transactional
    override fun modifyPost(
        id: Long,
        dto: PostModifyDto
    ): PostDto {
        val modifiedPost = dto.modifyEntity(innerGetPost(id))
        val createdBy = innerGetUser(modifiedPost.createdBy!!)
        val updatedBy = innerGetUser(modifiedPost.updatedBy!!)

        return PostDto.from(modifiedPost, createdBy, updatedBy)
    }

    @Transactional
    override fun deletePost(id: Long) {
        try {
            postRepository.deleteById(id)
        } catch (e: Exception) {
            when (e) {
                is EmptyResultDataAccessException ->
                    throw NotFoundResourceException("존재하지 않는 게시글 입니다.")
            }
            throw InternalServerException("알 수 없는 원인으로 게시글 엔티티 삭제에 실패했습니다.")
        }
    }

    private fun innerGetUser(id: Long): User {
        return userRepository.findById(id)
            .orElseThrow { throw NotFoundResourceException("일치하는 유저를 찾을 수 없습니다.") }
    }

    private fun innerGetPost(id: Long): Post {
        return postRepository.findById(id)
            .orElseThrow { throw NotFoundResourceException("일치하는 게시글을 찾을 수 없습니다.") }
    }

    private fun innerGetMyPosts(userId: Long): List<Post>? {
        return postRepository.findAllByUserId(userId)
    }
}