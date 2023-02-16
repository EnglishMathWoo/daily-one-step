package com.readnumber.dailyonestep.post

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.*

interface PostRepository : JpaRepository<Post, Long> {
    @Query(value = "SELECT post " +
            "FROM Post post " +
            "WHERE post.createdBy = :#{#userId}")
    fun findAllByUserId(
        @Param("userId")
        userId: Long
    ): List<Post>?
}