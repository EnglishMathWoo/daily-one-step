package com.readnumber.dailyonestep.notice.service

import com.readnumber.dailyonestep.notice.dto.response.NoticeDto
import com.readnumber.dailyonestep.notice.dto.request.NoticeCreateDto
import com.readnumber.dailyonestep.notice.dto.request.NoticeModifyDto
import com.readnumber.dailyonestep.notice.dto.response.MultipleNoticeWrapperDto
import org.springframework.data.domain.Pageable

interface NoticeService {
    fun createNotice(
        dto: NoticeCreateDto
    ): NoticeDto

    fun getNotice(
        noticeId: Long,
        userId: Long
    ): NoticeDto

    fun getMyNotices(
        userId: Long
    ): MultipleNoticeWrapperDto

    fun getNoticeList(): MultipleNoticeWrapperDto

    fun modifyNotice(
        id: Long,
        dto: NoticeModifyDto
    ): NoticeDto

    fun deleteNotice(id: Long)
}
