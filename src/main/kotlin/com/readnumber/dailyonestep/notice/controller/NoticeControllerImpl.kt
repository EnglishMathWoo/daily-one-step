package com.readnumber.dailyonestep.notice.controller

import com.readnumber.dailyonestep.common.binding_annotation.ValidUsernameFromAccessToken
import com.readnumber.dailyonestep.notice.dto.request.NoticeCreateDto
import com.readnumber.dailyonestep.notice.dto.request.NoticeModifyDto
import com.readnumber.dailyonestep.notice.dto.response.NoticeWrapperDto
import com.readnumber.dailyonestep.notice.dto.response.MultipleNoticeWrapperDto
import com.readnumber.dailyonestep.notice.service.NoticeService
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.*

@RequestMapping
@RestController
class NoticeControllerImpl(
    private val noticeService: NoticeService,
) : NoticeController {

    @PostMapping("/notices")
    override fun createNotice(
        @Valid @RequestBody
        dto: NoticeCreateDto
    ): NoticeWrapperDto {
        return NoticeWrapperDto.from(
            noticeService.createNotice(dto)
        )
    }

    @GetMapping("/notices/{id}")
    override fun getNotice(
        @PathVariable(value = "id")
        noticeId: Long,
        @ValidUsernameFromAccessToken
        username: String
    ): NoticeWrapperDto {
        return NoticeWrapperDto.from(noticeService.getNotice(noticeId, username))
    }

    @GetMapping("/notices/me")
    override fun getMyNotices(
        @ValidUsernameFromAccessToken
        username: String
    ): MultipleNoticeWrapperDto {
        return noticeService.getMyNotices(username)
    }


    @GetMapping("/notices")
    override fun getNoticeList(): MultipleNoticeWrapperDto {
        return noticeService.getNoticeList()
    }

    @PatchMapping("/notices/{id}")
    override fun modifyNotice(
        @PathVariable(value = "id")
        id: Long,
        @Valid @RequestBody
        dto: NoticeModifyDto
    ): NoticeWrapperDto {
        return NoticeWrapperDto.from(
            noticeService.modifyNotice(id, dto)
        )
    }

    @DeleteMapping("/notices/{id}")
    override fun deleteNotice(
        @PathVariable(value = "id")
        id: Long,
    ): Boolean {
        noticeService.deleteNotice(id)
        return true
    }

}
