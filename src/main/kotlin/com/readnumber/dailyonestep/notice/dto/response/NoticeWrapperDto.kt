package com.readnumber.dailyonestep.notice.dto.response

class NoticeWrapperDto(
    val notice: NoticeDto
) {
    companion object {
        fun from(dto: NoticeDto): NoticeWrapperDto {
            return NoticeWrapperDto(dto)
        }
    }
}
