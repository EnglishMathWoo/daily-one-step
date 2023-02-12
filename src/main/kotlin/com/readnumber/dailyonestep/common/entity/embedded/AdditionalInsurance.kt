package com.readnumber.dailyonestep.common.entity.embedded

import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import org.hibernate.annotations.Comment
import java.math.BigDecimal

@Embeddable
class AdditionalInsurance(
    @Column
    @Comment("건강보험")
    val healthInsurance: BigDecimal,

    @Column
    @Comment("장기요양보험")
    val longTermCareInsurance: BigDecimal
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as AdditionalInsurance

        if (healthInsurance != other.healthInsurance) return false
        if (longTermCareInsurance != other.longTermCareInsurance) return false

        return true
    }

    override fun hashCode(): Int {
        var result = healthInsurance.hashCode()
        result = 31 * result + longTermCareInsurance.hashCode()
        return result
    }
}