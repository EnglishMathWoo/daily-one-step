package com.readnumber.dailyonestep.notice

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.*

interface NoticeRepository : JpaRepository<Notice, Long> {
    @Query(value = "SELECT notice " +
            "FROM Notice notice " +
            "WHERE notice.createdBy = :#{#userId} " +
            "ORDER BY notice.id DESC")
    fun findAllByUserId(
        @Param("userId")
        userId: Long
    ): List<Notice>?
}
