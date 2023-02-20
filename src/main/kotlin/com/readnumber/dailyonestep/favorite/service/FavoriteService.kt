package com.readnumber.dailyonestep.favorite.service

import com.readnumber.dailyonestep.notice.dto.response.MultipleNoticeWrapperDto

interface FavoriteService {
    fun getFavoriteCount(): Long

    fun createFavorite(
        noticeId: Long,
        userId: Long
    ): Boolean?

    fun getMyFavoriteNotices(
        userId: Long
    ): MultipleNoticeWrapperDto

    fun getFavoriteStatus(
        noticeId: Long,
        userId: Long
    ): Boolean?
}
