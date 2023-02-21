package com.readnumber.dailyonestep.notice

import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.*

interface NoticeRepository : JpaRepository<Notice, Long> {
    @Query(value = "SELECT notice " +
            "FROM Notice notice " +
            "WHERE notice.createdBy = :#{#username} " +
            "ORDER BY notice.id DESC")
    fun findAllByUserId(
        @Param("username")
        username: String
    ): List<Notice>?

    @EntityGraph(attributePaths = ["comments"])
    fun findWithCommentsById(id: Long): Notice?
}
