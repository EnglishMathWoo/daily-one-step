package com.readnumber.dailyonestep.comment

import com.readnumber.dailyonestep.common.entity.BaseEntity
import com.readnumber.dailyonestep.post.Post
import jakarta.persistence.*
import org.hibernate.annotations.Comment

@Entity
@Table(name = "COMMENT")
class Comment(
    @Column
    @Comment(value = "내용")
    var content: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", foreignKey = ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
    var post: Post? = null
) : BaseEntity()