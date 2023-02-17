package com.readnumber.dailyonestep.favorite.controller

import com.readnumber.dailyonestep.post.dto.response.MultiplePostWrapperDto

interface FavoriteController {
    fun createFavorite(
        postId: Long,
        userId: Long
    ): Any?


    fun getMyFavoritePosts(
        userId: Long
    ): MultiplePostWrapperDto
}