package com.readnumber.dailyonestep.notice.controller

import com.readnumber.dailyonestep.common.binding_annotation.ValidUserIdFromAccessToken
import com.readnumber.dailyonestep.notice.dto.request.NoticeCreateDto
import com.readnumber.dailyonestep.notice.dto.request.NoticeModifyDto
import com.readnumber.dailyonestep.notice.dto.request.NoticeSearchQueryParameter
import com.readnumber.dailyonestep.notice.dto.response.NoticeWrapperDto
import com.readnumber.dailyonestep.notice.dto.response.MultipleNoticeWrapperDto
import com.readnumber.dailyonestep.notice.service.NoticeService
import jakarta.validation.Valid
import org.springframework.data.domain.Pageable
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
        @ValidUserIdFromAccessToken
        userId: Long
    ): NoticeWrapperDto {
        return NoticeWrapperDto.from(noticeService.getNotice(noticeId, userId))
    }

    @GetMapping("/notices/me")
    override fun getMyNotices(
        @ValidUserIdFromAccessToken
        userId: Long,
    ): MultipleNoticeWrapperDto {
        return noticeService.getMyNotices(userId)
    }


    @GetMapping("/notices")
    override fun searchNotices(
        queryParameter: NoticeSearchQueryParameter,
        pageable: Pageable
    ): MultipleNoticeWrapperDto {
        return noticeService.findNotices(queryParameter, pageable)
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
