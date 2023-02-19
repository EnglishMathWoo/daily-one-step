package com.readnumber.dailyonestep.favorite

import com.readnumber.dailyonestep.common.entity.BaseEntity
import com.readnumber.dailyonestep.notice.Notice
import jakarta.persistence.*

@Entity
@Table(name = "FAVORITE")
class Favorite(
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "notice_id", foreignKey = ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
    var notice: Notice? = null
) : BaseEntity()
