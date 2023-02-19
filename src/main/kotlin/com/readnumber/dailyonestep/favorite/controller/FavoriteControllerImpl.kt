package com.readnumber.dailyonestep.favorite.controller

import com.readnumber.dailyonestep.common.binding_annotation.ValidUserIdFromAccessToken
import com.readnumber.dailyonestep.favorite.service.FavoriteService
import com.readnumber.dailyonestep.notice.dto.response.MultipleNoticeWrapperDto
import org.springframework.web.bind.annotation.*

@RequestMapping
@RestController
class FavoriteControllerImpl(
    private val favoriteService: FavoriteService
) : FavoriteController {
    @PostMapping("/notices/{id}/favorites")
    override fun createFavorite(
        @PathVariable(value = "id")
        noticeId: Long,
        @ValidUserIdFromAccessToken
        userId: Long
    ): Any? {
        return favoriteService.createFavorite(noticeId, userId)
    }

    @GetMapping("/favorites/me")
    override fun getMyFavoriteNotices(
        @ValidUserIdFromAccessToken
        userId: Long
    ): MultipleNoticeWrapperDto{
        return favoriteService.getMyFavoriteNotices(userId)
    }
}
