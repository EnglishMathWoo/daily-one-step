package com.readnumber.dailyonestep.favorite.service

import com.readnumber.dailyonestep.notice.dto.response.MultipleNoticeWrapperDto

interface FavoriteService {
    fun getFavoriteCount(): Long

    fun createFavorite(
        noticeId: Long,
        username: String
    ): Boolean?

    fun getMyFavoriteNotices(
        username: String
    ): MultipleNoticeWrapperDto
}
