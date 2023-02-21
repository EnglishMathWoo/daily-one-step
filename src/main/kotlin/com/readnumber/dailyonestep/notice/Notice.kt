package com.readnumber.dailyonestep.notice

import com.readnumber.dailyonestep.common.entity.BaseEntity
import jakarta.persistence.*
import org.hibernate.annotations.Comment
import com.readnumber.dailyonestep.comment.Comment as CommentEntity

@Entity
@Table(name = "NOTICE")
class Notice(
    @Column
    @Comment(value = "제목")
    var title: String,

    @Column(columnDefinition = "TEXT")
    @Comment(value = "내용")
    var content: String,

    @OneToMany(mappedBy = "notice", cascade = [CascadeType.REMOVE])
    var comments: MutableList<CommentEntity> = mutableListOf()
) : BaseEntity()
