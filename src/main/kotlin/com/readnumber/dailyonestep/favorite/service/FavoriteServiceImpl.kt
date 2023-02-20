package com.readnumber.dailyonestep.favorite.service

import com.readnumber.dailyonestep.common.error.exception.NotFoundResourceException
import com.readnumber.dailyonestep.favorite.Favorite
import com.readnumber.dailyonestep.favorite.FavoriteRepository
import com.readnumber.dailyonestep.notice.NoticeRepository
import com.readnumber.dailyonestep.notice.Notice
import com.readnumber.dailyonestep.notice.dto.response.MultipleNoticeWrapperDto
import com.readnumber.dailyonestep.notice.dto.response.NoticeDto
import com.readnumber.dailyonestep.user.User
import com.readnumber.dailyonestep.user.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class FavoriteServiceImpl(
    private val userRepository: UserRepository,
    private val noticeRepository: NoticeRepository,
    private val favoriteRepository: FavoriteRepository
) : FavoriteService {
    @Transactional(readOnly = true)
    override fun getFavoriteCount(): Long {
        return favoriteRepository.count()
    }

    @Transactional
    override fun createFavorite(
        noticeId: Long,
        userId: Long
    ): Boolean? {
        if(innerCheckExistingFavorite(noticeId, userId)) {
            val notice = innerGetNotice(noticeId)
            val favoriteEntity = Favorite(notice)
            favoriteRepository.save(favoriteEntity)

            return true
        }
        return false
    }

    @Transactional(readOnly = true)
    override fun getMyFavoriteNotices(
        userId: Long
    ): MultipleNoticeWrapperDto {
        val favorite = innerGetMyFavorites(userId)

        return MultipleNoticeWrapperDto(
            totalCount = favorite?.size ?: 0,
            notices = favorite?.map { NoticeDto.from(
                it.notice!!,
                createdBy = innerGetUser(it.notice!!.createdBy!!),
                updatedBy = innerGetUser(it.notice!!.updatedBy!!)
            )
            }
        )
    }

    @Transactional(readOnly = true)
    override fun getFavoriteStatus(
        noticeId: Long,
        userId: Long
    ): Boolean? {
        return innerCheckExistingFavorite(noticeId, userId)
    }

    private fun innerGetUser(id: Long): User {
        return userRepository.findById(id)
            .orElseThrow { throw NotFoundResourceException("일치하는 유저를 찾을 수 없습니다.") }
    }

    private fun innerGetNotice(noticeId: Long): Notice {
        return noticeRepository.findById(noticeId)
            .orElseThrow { throw NotFoundResourceException("일치하는 게시글을 찾을 수 없습니다.") }
    }

    private fun innerGetMyFavorites(userId: Long): List<Favorite>? {
        return favoriteRepository.findAllByUserId(userId)
    }

    private fun innerCheckExistingFavorite(noticeId: Long, userId: Long): Boolean {
        val favorite = favoriteRepository.findByNoticeIdAndUserId(noticeId, userId)

        if(favorite != null) {
            favoriteRepository.deleteById(favorite.id!!)
            return false
        }
        return true
    }
}
