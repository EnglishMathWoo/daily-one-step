package com.readnumber.dailyonestep.notice

import com.readnumber.dailyonestep.common.entity.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table
import org.hibernate.annotations.Comment

@Entity
@Table(name = "Notice")
class Notice(
    @Column
    @Comment(value = "제목")
    var title: String,

    @Column(columnDefinition = "TEXT")
    @Comment(value = "내용")
    var content: String,
) : BaseEntity()
