package com.readnumber.dailyonestep.favorite.controller

import com.readnumber.dailyonestep.common.binding_annotation.ValidUsernameFromAccessToken
import com.readnumber.dailyonestep.favorite.service.FavoriteService
import com.readnumber.dailyonestep.notice.dto.response.MultipleNoticeWrapperDto
import org.springframework.web.bind.annotation.*

@RequestMapping
@RestController
class FavoriteControllerImpl(
    private val favoriteService: FavoriteService
) : FavoriteController {
    @PostMapping("/notices/{noticeId}/favorites")
    override fun createFavorite(
        @PathVariable(value = "noticeId")
        noticeId: Long,
        @ValidUsernameFromAccessToken
        username: String
    ): Boolean? {
        return favoriteService.createFavorite(noticeId, username)
    }

    @GetMapping("/favorites/me")
    override fun getMyFavoriteNotices(
        @ValidUsernameFromAccessToken
        username: String
    ): MultipleNoticeWrapperDto{
        return favoriteService.getMyFavoriteNotices(username)
    }
}
