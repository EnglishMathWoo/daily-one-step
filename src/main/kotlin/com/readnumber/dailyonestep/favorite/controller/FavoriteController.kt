package com.readnumber.dailyonestep.favorite.controller

import com.readnumber.dailyonestep.notice.dto.response.MultipleNoticeWrapperDto

interface FavoriteController {
    fun createFavorite(
        noticeId: Long,
        username: String
    ): Boolean?

    fun getMyFavoriteNotices(
        username: String
    ): MultipleNoticeWrapperDto
}
