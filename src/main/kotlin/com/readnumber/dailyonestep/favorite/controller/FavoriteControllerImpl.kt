package com.readnumber.dailyonestep.favorite.controller

import com.readnumber.dailyonestep.common.binding_annotation.ValidUserIdFromAccessToken
import com.readnumber.dailyonestep.favorite.service.FavoriteService
import com.readnumber.dailyonestep.post.dto.response.MultiplePostWrapperDto
import org.springframework.web.bind.annotation.*

@RequestMapping("/favorites")
@RestController
class FavoriteControllerImpl(
    private val favoriteService: FavoriteService
) : FavoriteController {
    @PostMapping("/{id}")
    override fun createFavorite(
        @PathVariable(value = "id")
        postId: Long,
        @ValidUserIdFromAccessToken
        userId: Long
    ): Any? {
        return favoriteService.createFavorite(postId, userId)
    }

    @GetMapping("/me")
    override fun getMyFavoritePosts(
        @ValidUserIdFromAccessToken
        userId: Long
    ): MultiplePostWrapperDto{
        return favoriteService.getMyFavoritePosts(userId)
    }
}
