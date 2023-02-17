package com.readnumber.dailyonestep.favorite.service

import com.readnumber.dailyonestep.post.dto.response.MultiplePostWrapperDto

interface FavoriteService {
    fun getFavoriteCount(): Long

    fun createFavorite(
        postId: Long,
        userId: Long
    ): Any?

    fun getMyFavoritePosts(
        userId: Long
    ): MultiplePostWrapperDto
}