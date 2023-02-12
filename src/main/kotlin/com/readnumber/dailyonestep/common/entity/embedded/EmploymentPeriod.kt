package com.readnumber.dailyonestep.common.entity.embedded

import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import org.hibernate.annotations.Comment
import java.time.LocalDate

@Embeddable
class EmploymentPeriod(
    @Column
    @Comment("입사일")
    val joinedAt: LocalDate,

    @Column
    @Comment(value = "퇴사일")
    var resignedAt: LocalDate? = null,

    @Column
    @Comment(value = "퇴사사유")
    var resignReason: String? = null,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as EmploymentPeriod

        if (joinedAt != other.joinedAt) return false
        if (resignedAt != other.resignedAt) return false
        if (resignReason != other.resignReason) return false

        return true
    }

    override fun hashCode(): Int {
        var result = joinedAt.hashCode()
        result = 31 * result + (resignedAt?.hashCode() ?: 0)
        result = 31 * result + (resignReason?.hashCode() ?: 0)
        return result
    }
}