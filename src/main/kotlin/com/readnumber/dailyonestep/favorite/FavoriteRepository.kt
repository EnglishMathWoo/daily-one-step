package com.readnumber.dailyonestep.favorite

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.*

interface FavoriteRepository : JpaRepository<Favorite, Long> {
    @Query(value = "SELECT favorite " +
            "FROM Favorite favorite " +
            "WHERE favorite.createdBy = :#{#userId} "
    )
    fun findAllByUserId(
        @Param("userId")
        userId: Long
    ): List<Favorite>?

    @Query(value = "SELECT favorite " +
            "FROM Favorite favorite " +
            "WHERE favorite.post.id = :#{#postId} "
    )
    fun findByPostId(
        @Param("postId")
        postId: Long
    ): Favorite?
}