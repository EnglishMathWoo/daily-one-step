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
import com.readnumber.dailyonestep.user.User
import com.readnumber.dailyonestep.user.UserRepository
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class NoticeServiceImpl(
    private val userRepository: UserRepository,
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
        val createdBy = innerGetUser(notice.createdBy!!)

        return NoticeDto.from(
            notice,
            createdBy = createdBy
        )
    }

    @Transactional(readOnly = true)
    override fun getNotice(
        noticeId: Long,
        userId: Long
    ): NoticeDto {
        val notice = innerGetNoticeWithComment(noticeId)
        val favorite = innerCheckExistingFavorite(noticeId, userId)
        val createdBy = innerGetUser(notice.createdBy!!)
        val updatedBy = innerGetUser(notice.updatedBy!!)

        return NoticeDto.from(
            notice,
            favorite = favorite,
            createdBy = createdBy,
            updatedBy = updatedBy
        )
    }

    @Transactional(readOnly = true)
    override fun getMyNotices(
        userId: Long
    ): MultipleNoticeWrapperDto {
        val notices = innerGetMyNotices(userId)

        return MultipleNoticeWrapperDto(
            totalCount = notices?.size ?: 0,
            notices = notices?.map { NoticeDto.from(
                it,
                createdBy = innerGetUser(it.createdBy!!),
                updatedBy = innerGetUser(it.updatedBy!!)
            )
            }
        )
    }

    @Transactional(readOnly = true)
    override fun getNoticeList(): MultipleNoticeWrapperDto {
        val noticeList = noticeRepository.findAll()

        return MultipleNoticeWrapperDto(
            totalCount = noticeList.size,
            notices = noticeList.map { NoticeDto.from(
                it,
                createdBy = innerGetUser(it.createdBy!!),
                updatedBy = innerGetUser(it.updatedBy!!)
            )
            }
        )
    }

    @Transactional
    override fun modifyNotice(
        id: Long,
        dto: NoticeModifyDto
    ): NoticeDto {
        val modifiedNotice = dto.modifyEntity(innerGetNotice(id))
        val createdBy = innerGetUser(modifiedNotice.createdBy!!)
        val updatedBy = innerGetUser(modifiedNotice.updatedBy!!)

        return NoticeDto.from(
            modifiedNotice,
            createdBy = createdBy,
            updatedBy = updatedBy
        )
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

    private fun innerGetUser(id: Long): User {
        return userRepository.findById(id)
            .orElseThrow { throw NotFoundResourceException("일치하는 유저를 찾을 수 없습니다.") }
    }

    private fun innerGetNotice(id: Long): Notice {
        return noticeRepository.findById(id)
            .orElseThrow { throw NotFoundResourceException("일치하는 게시글을 찾을 수 없습니다.") }
    }

    private fun innerGetNoticeWithComment(id: Long): Notice {
        return noticeRepository.findWithCommentsById(id)
            ?: throw NotFoundResourceException("일치하는 게시글을 찾을 수 없습니다.")
    }

    private fun innerGetMyNotices(userId: Long): List<Notice>? {
        return noticeRepository.findAllByUserId(userId)
    }

    private fun innerCheckExistingFavorite(noticeId: Long, userId: Long): Boolean {
        val favorite = favoriteRepository.findByNoticeIdAndUserId(noticeId, userId)
        if(favorite != null) return true

        return false
    }
}
