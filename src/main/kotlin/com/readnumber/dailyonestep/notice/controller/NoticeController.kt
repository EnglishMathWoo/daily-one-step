package com.readnumber.dailyonestep.notice.controller

import com.readnumber.dailyonestep.notice.dto.request.NoticeCreateDto
import com.readnumber.dailyonestep.notice.dto.request.NoticeModifyDto
import com.readnumber.dailyonestep.notice.dto.request.NoticeSearchQueryParameter
import com.readnumber.dailyonestep.notice.dto.response.NoticeWrapperDto
import com.readnumber.dailyonestep.notice.dto.response.MultipleNoticeWrapperDto
import org.springframework.data.domain.Pageable

interface NoticeController {
    fun createNotice(
        dto: NoticeCreateDto
    ): NoticeWrapperDto

    fun getNotice(
        noticeId: Long,
        userId: Long
    ): NoticeWrapperDto

    fun getMyNotices(
        userId: Long
    ): MultipleNoticeWrapperDto

    fun searchNotices(
        queryParameter: NoticeSearchQueryParameter,
        pageable: Pageable,
    ): MultipleNoticeWrapperDto

    fun modifyNotice(
        id: Long,
        dto: NoticeModifyDto
    ): NoticeWrapperDto

    fun deleteNotice(
        id: Long
    ): Boolean
}
