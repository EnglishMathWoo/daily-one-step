package com.readnumber.dailyonestep.notice.dto.request

import com.readnumber.dailyonestep.notice.Notice

class NoticeCreateDto (
    val title: String,
    val content: String
) {
    fun toEntity(): Notice {
        return Notice(
            title = title,
            content = content
        )
    }
}
