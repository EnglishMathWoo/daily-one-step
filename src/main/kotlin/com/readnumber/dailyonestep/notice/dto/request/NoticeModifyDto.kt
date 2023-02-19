package com.readnumber.dailyonestep.notice.dto.request

import com.readnumber.dailyonestep.notice.Notice

class NoticeModifyDto (
    val title: String? = null,
    val content: String? = null
) {
    fun modifyEntity(notice: Notice): Notice {
        notice.title = title ?: notice.title
        notice.content = content ?: notice.content

        return notice
    }
}
