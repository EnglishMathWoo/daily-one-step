package com.readnumber.dailyonestep.favorite.service

import com.readnumber.dailyonestep.common.error.exception.NotFoundResourceException
import com.readnumber.dailyonestep.favorite.Favorite
import com.readnumber.dailyonestep.favorite.FavoriteRepository
import com.readnumber.dailyonestep.notice.NoticeRepository
import com.readnumber.dailyonestep.notice.Notice
import com.readnumber.dailyonestep.notice.dto.response.MultipleNoticeWrapperDto
import com.readnumber.dailyonestep.notice.dto.response.NoticeDto
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class FavoriteServiceImpl(
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
        username: String
    ): Boolean? {
        if(innerCheckExistingFavorite(noticeId, username)) {
            val notice = innerGetNotice(noticeId)
            val favoriteEntity = Favorite(notice)
            favoriteRepository.save(favoriteEntity)

            return true
        }
        return false
    }

    @Transactional(readOnly = true)
    override fun getMyFavoriteNotices(
        username: String
    ): MultipleNoticeWrapperDto {
        val favorite = innerGetMyFavorites(username)

        return MultipleNoticeWrapperDto(
            totalCount = favorite?.size ?: 0,
            notices = favorite?.map { NoticeDto.from( it.notice!!) }
        )
    }

    private fun innerGetNotice(noticeId: Long): Notice {
        return noticeRepository.findById(noticeId)
            .orElseThrow { throw NotFoundResourceException("일치하는 게시글을 찾을 수 없습니다.") }
    }

    private fun innerGetMyFavorites(username: String): List<Favorite>? {
        return favoriteRepository.findAllByUsername(username)
    }

    private fun innerCheckExistingFavorite(noticeId: Long, username: String): Boolean {
        val favorite = favoriteRepository.findByNoticeIdAndUsername(noticeId, username)

        if(favorite != null) {
            favoriteRepository.deleteById(favorite.id!!)
            return false
        }
        return true
    }
}
