package com.readnumber.dailyonestep.notice.service

import com.readnumber.dailyonestep.common.error.exception.InternalServerException
import com.readnumber.dailyonestep.common.error.exception.NotFoundResourceException
import com.readnumber.dailyonestep.favorite.FavoriteRepository
import com.readnumber.dailyonestep.notice.Notice
import com.readnumber.dailyonestep.notice.NoticeRepository
import com.readnumber.dailyonestep.notice.dto.response.NoticeDto
import com.readnumber.dailyonestep.notice.dto.request.NoticeCreateDto
import com.readnumber.dailyonestep.notice.dto.request.NoticeModifyDto
import com.readnumber.dailyonestep.notice.dto.response.MultipleNoticeWrapperDto
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class NoticeServiceImpl(
    private val noticeRepository: NoticeRepository,
    private  val favoriteRepository: FavoriteRepository
) : NoticeService {
    @Transactional
    override fun createNotice(
        dto: NoticeCreateDto
    ): NoticeDto {
        val notice = noticeRepository.save(
            dto.toEntity()
        )

        return NoticeDto.from(notice)
    }

    @Transactional(readOnly = true)
    override fun getNotice(
        noticeId: Long,
        username: String
    ): NoticeDto {
        val notice = innerGetNoticeWithComment(noticeId)
        val favorite = innerCheckExistingFavorite(noticeId, username)

        return NoticeDto.from(notice, favorite)
    }

    @Transactional(readOnly = true)
    override fun getMyNotices(
        username: String
    ): MultipleNoticeWrapperDto {
        val notices = innerGetMyNotices(username)

        return MultipleNoticeWrapperDto(
            totalCount = notices?.size ?: 0,
            notices = notices?.map { NoticeDto.from(it) }
        )
    }

    @Transactional(readOnly = true)
    override fun getNoticeList(): MultipleNoticeWrapperDto {
        val noticeList = noticeRepository.findAll()

        return MultipleNoticeWrapperDto(
            totalCount = noticeList.size,
            notices = noticeList.map { NoticeDto.from(it) }
        )
    }

    @Transactional
    override fun modifyNotice(
        id: Long,
        dto: NoticeModifyDto
    ): NoticeDto {
        val modifiedNotice = dto.modifyEntity(innerGetNotice(id))

        return NoticeDto.from(modifiedNotice)
    }

    @Transactional
    override fun deleteNotice(id: Long) {
        try {
            noticeRepository.deleteById(id)
        } catch (e: Exception) {
            when (e) {
                is EmptyResultDataAccessException ->
                    throw NotFoundResourceException("존재하지 않는 게시글 입니다.")
            }
            throw InternalServerException("알 수 없는 원인으로 게시글 엔티티 삭제에 실패했습니다.")
        }
    }


    private fun innerGetNotice(id: Long): Notice {
        return noticeRepository.findById(id)
            .orElseThrow { throw NotFoundResourceException("일치하는 게시글을 찾을 수 없습니다.") }
    }

    private fun innerGetNoticeWithComment(id: Long): Notice {
        return noticeRepository.findWithCommentsById(id)
            ?: throw NotFoundResourceException("일치하는 게시글을 찾을 수 없습니다.")
    }

    private fun innerGetMyNotices(username: String): List<Notice>? {
        return noticeRepository.findAllByUserId(username)
    }

    private fun innerCheckExistingFavorite(noticeId: Long, username: String): Boolean {
        val favorite = favoriteRepository.findByNoticeIdAndUsername(noticeId, username)
        if(favorite != null) return true

        return false
    }
}
