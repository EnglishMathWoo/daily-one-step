package com.readnumber.dailyonestep.common.entity.embedded

import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import org.hibernate.annotations.Comment
import java.math.BigDecimal

@Embeddable
class WithHoldingTax(
    @Column
    @Comment("소득세")
    val incomeTax: BigDecimal,

    @Column
    @Comment("지방소득세")
    val localIncomeTax: BigDecimal
) {
    constructor(incomeTax: BigDecimal) : this(
        incomeTax = incomeTax,
        localIncomeTax = incomeTax * BigDecimal(0.1),
    )

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as WithHoldingTax

        if (incomeTax != other.incomeTax) return false
        if (localIncomeTax != other.localIncomeTax) return false

        return true
    }

    override fun hashCode(): Int {
        var result = incomeTax.hashCode()
        result = 31 * result + localIncomeTax.hashCode()
        return result
    }
}