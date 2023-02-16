package com.readnumber.dailyonestep.comment

import com.readnumber.dailyonestep.common.entity.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table
import org.hibernate.annotations.Comment

@Entity
@Table(name = "COMMENT")
class Comment(
    @Column
    @Comment(value = "내용")
    var content: String

) : BaseEntity()