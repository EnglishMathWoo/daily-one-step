package com.readnumber.dailyonestep.favorite.service

import com.readnumber.dailyonestep.notice.dto.response.MultipleNoticeWrapperDto

interface FavoriteService {
    fun getFavoriteCount(): Long

    fun createFavorite(
        noticeId: Long,
        userId: Long
    ): Any?

    fun getMyFavoriteNotices(
        userId: Long
    ): MultipleNoticeWrapperDto
}
