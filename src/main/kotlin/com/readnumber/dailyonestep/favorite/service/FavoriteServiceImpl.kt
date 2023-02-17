package com.readnumber.dailyonestep.favorite.service

import com.readnumber.dailyonestep.common.error.exception.NotFoundResourceException
import com.readnumber.dailyonestep.favorite.Favorite
import com.readnumber.dailyonestep.favorite.FavoriteRepository
import com.readnumber.dailyonestep.favorite.dto.FavoriteDto
import com.readnumber.dailyonestep.post.Post
import com.readnumber.dailyonestep.post.PostRepository
import com.readnumber.dailyonestep.post.dto.response.MultiplePostWrapperDto
import com.readnumber.dailyonestep.post.dto.response.PostDto
import com.readnumber.dailyonestep.user.User
import com.readnumber.dailyonestep.user.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class FavoriteServiceImpl(
    private val userRepository: UserRepository,
    private val postRepository: PostRepository,
    private val favoriteRepository: FavoriteRepository
) : FavoriteService {
    @Transactional(readOnly = true)
    override fun getFavoriteCount(): Long {
        return favoriteRepository.count()
    }

    @Transactional
    override fun createFavorite(
        postId: Long,
        userId: Long
    ): Any? {
        if(innerCheckExistingFavorite(postId, userId)) {
            val post = innerGetPost(postId)
            val favoriteEntity = Favorite(post)
            favoriteRepository.save(favoriteEntity)

            return true
        }
        return false
    }

    @Transactional(readOnly = true)
    override fun getMyFavoritePosts(
        userId: Long
    ): MultiplePostWrapperDto {
        val favorite = innerGetMyFavorites(userId)

        return MultiplePostWrapperDto(
            totalCount = favorite?.size ?: 0,
            posts = favorite?.map { PostDto.from(
                it.post!!,
                createdBy = innerGetUser(it.post!!.createdBy!!),
                updatedBy = innerGetUser(it.post!!.updatedBy!!)
            )
            }
        )
    }

    private fun innerGetUser(id: Long): User {
        return userRepository.findById(id)
            .orElseThrow { throw NotFoundResourceException("일치하는 유저를 찾을 수 없습니다.") }
    }

    private fun innerGetPost(postId: Long): Post {
        return postRepository.findById(postId)
            .orElseThrow { throw NotFoundResourceException("일치하는 게시글을 찾을 수 없습니다.") }
    }

    private fun innerGetMyFavorites(userId: Long): List<Favorite>? {
        return favoriteRepository.findAllByUserId(userId)
    }

    private fun innerCheckExistingFavorite(postId: Long, userId: Long): Boolean {
        val favorite = favoriteRepository.findByPostIdAndUserId(postId, userId)

        if(favorite != null) {
            favoriteRepository.deleteById(favorite.id!!)
            return false
        }
        return true
    }
}