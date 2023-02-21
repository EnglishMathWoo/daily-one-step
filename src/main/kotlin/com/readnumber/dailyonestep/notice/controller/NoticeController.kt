package com.readnumber.dailyonestep.notice.controller

import com.readnumber.dailyonestep.notice.dto.request.NoticeCreateDto
import com.readnumber.dailyonestep.notice.dto.request.NoticeModifyDto
import com.readnumber.dailyonestep.notice.dto.response.NoticeWrapperDto
import com.readnumber.dailyonestep.notice.dto.response.MultipleNoticeWrapperDto

interface NoticeController {
    fun createNotice(
        dto: NoticeCreateDto
    ): NoticeWrapperDto

    fun getNotice(
        noticeId: Long,
        username: String,
    ): NoticeWrapperDto

    fun getMyNotices(
        username: String
    ): MultipleNoticeWrapperDto

    fun getNoticeList(): MultipleNoticeWrapperDto

    fun modifyNotice(
        id: Long,
        dto: NoticeModifyDto
    ): NoticeWrapperDto

    fun deleteNotice(
        id: Long
    ): Boolean
}
