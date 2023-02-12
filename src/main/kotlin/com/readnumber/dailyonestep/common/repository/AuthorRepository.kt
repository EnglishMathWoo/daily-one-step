package com.readnumber.dailyonestep.common.repository

import com.readnumber.dailyonestep.common.entity.Author
import com.readnumber.dailyonestep.common.entity.AuthorTypeEnum
import org.springframework.data.jpa.repository.JpaRepository

interface AuthorRepository : JpaRepository<Author, Long> {
    fun findByAuthorTypeAndAssociatedId(authorType: AuthorTypeEnum, associatedId: Long): Author?
}