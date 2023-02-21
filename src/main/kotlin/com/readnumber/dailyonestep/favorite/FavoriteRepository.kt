package com.readnumber.dailyonestep.favorite

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.*

interface FavoriteRepository : JpaRepository<Favorite, Long> {
    @Query(value = "SELECT favorite " +
            "FROM Favorite favorite " +
            "WHERE favorite.createdBy = :#{#username}")
    fun findAllByUsername(
        @Param("username")
        username: String
    ): List<Favorite>?

    @Query(value = "SELECT favorite " +
            "FROM Favorite favorite " +
            "WHERE favorite.notice.id = :#{#noticeId} AND favorite.createdBy = :#{#username}")
    fun findByNoticeIdAndUsername(
        @Param("noticeId")
        noticeId: Long,
        @Param("username")
        username: String
    ): Favorite?
}
