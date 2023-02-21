package com.readnumber.dailyonestep.comment

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.*

interface CommentRepository : JpaRepository<Comment, Long> {
    @Query(value = "SELECT comment " +
        "FROM Comment comment " +
        "WHERE comment.createdBy = :#{#username} "
    )
    fun findAllByUsername(
        @Param("username")
        username: String
    ): List<Comment>?

    @Query(value = "SELECT comment " +
            "FROM Comment comment " +
            "WHERE comment.notice.id = :#{#noticeId} "
    )
    fun findAllByNoticeId(
        @Param("noticeId")
        noticeId: Long
    ): List<Comment>?
}
