package com.readnumber.dailyonestep.favorite.controller

import com.readnumber.dailyonestep.notice.dto.response.MultipleNoticeWrapperDto

interface FavoriteController {
    fun createFavorite(
        noticeId: Long,
        userId: Long
    ): Boolean?

    fun getMyFavoriteNotices(
        userId: Long
    ): MultipleNoticeWrapperDto
}
