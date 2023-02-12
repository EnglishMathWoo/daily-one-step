package com.readnumber.dailyonestep.common.entity.embedded

import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import org.hibernate.annotations.Comment

@Embeddable
class WorkAmount(
    @Column
    @Comment("근무일수")
    val workingDay: Int? = null,

    @Column
    @Comment("근무시간")
    val workingHour: Int? = null,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as WorkAmount

        if (workingDay != other.workingDay) return false
        if (workingHour != other.workingHour) return false

        return true
    }

    override fun hashCode(): Int {
        var result = workingDay ?: 0
        result = 31 * result + (workingHour ?: 0)
        return result
    }
}