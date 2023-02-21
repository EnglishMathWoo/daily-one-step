package com.readnumber.dailyonestep.notice

import org.springframework.data.jpa.repository.EntityGraph
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

    @EntityGraph(attributePaths = ["comments"])
    fun findWithCommentsById(id: Long): Notice?

//    @Query(value = "SELECT notice " +
//            "FROM Notice notice " +
//            "INNER JOIN User user on notice.createdBy = user.id " +
//            "WHERE notice.id = :#{#id}")
//    override fun findById(
//        @Param("id")
//        id: Long
//    ): Optional<Notice>
//
//    @Query(value = "SELECT notice " +
//            "FROM Notice notice " +
//            "INNER JOIN User user on notice.createdBy = user.id " +
//            "WHERE notice.id = :#{#id}")
//    override fun findAll(
//            @Param("id")
//            id: Long
//    ): Optional<Notice>
}
