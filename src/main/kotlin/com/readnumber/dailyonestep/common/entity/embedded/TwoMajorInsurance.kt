package com.readnumber.dailyonestep.common.entity.embedded

import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import org.hibernate.annotations.Comment
import java.math.BigDecimal

@Embeddable
class TwoMajorInsurance(
    @Column
    @Comment("국민연금")
    val nationalPension: BigDecimal,

    @Column
    @Comment("고용보험")
    val employmentInsurance: BigDecimal
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as TwoMajorInsurance

        if (nationalPension != other.nationalPension) return false
        if (employmentInsurance != other.employmentInsurance) return false

        return true
    }

    override fun hashCode(): Int {
        var result = nationalPension.hashCode()
        result = 31 * result + employmentInsurance.hashCode()
        return result
    }
}