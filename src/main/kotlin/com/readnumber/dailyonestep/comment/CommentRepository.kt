package com.readnumber.dailyonestep.comment

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.*

interface CommentRepository : JpaRepository<Comment, Long> {
    @Query(value = "SELECT comment " +
        "FROM Comment comment " +
        "WHERE comment.createdBy = :#{#userId} "
    )
    fun findAllByUserId(
        @Param("userId")
        userId: Long
    ): Optional<List<Comment>>
}