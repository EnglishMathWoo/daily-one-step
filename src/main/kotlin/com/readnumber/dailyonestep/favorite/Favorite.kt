package com.readnumber.dailyonestep.favorite

import com.readnumber.dailyonestep.common.entity.BaseEntity
import com.readnumber.dailyonestep.post.Post
import jakarta.persistence.*

@Entity
@Table(name = "FAVORITE")
class Favorite(
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", foreignKey = ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
    var post: Post? = null
) : BaseEntity()