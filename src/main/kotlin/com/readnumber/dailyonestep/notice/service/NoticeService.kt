package com.readnumber.dailyonestep.notice.service

import com.readnumber.dailyonestep.notice.dto.response.NoticeDto
import com.readnumber.dailyonestep.notice.dto.request.NoticeCreateDto
import com.readnumber.dailyonestep.notice.dto.request.NoticeModifyDto
import com.readnumber.dailyonestep.notice.dto.response.MultipleNoticeWrapperDto

interface NoticeService {
    fun createNotice(
        dto: NoticeCreateDto
    ): NoticeDto

    fun getNotice(
        noticeId: Long,
        username: String
    ): NoticeDto

    fun getMyNotices(
        username: String
    ): MultipleNoticeWrapperDto

    fun getNoticeList(): MultipleNoticeWrapperDto

    fun modifyNotice(
        id: Long,
        dto: NoticeModifyDto
    ): NoticeDto

    fun deleteNotice(id: Long)
}
