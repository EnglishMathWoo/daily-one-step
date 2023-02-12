package com.readnumber.dailyonestep.common.dto.embedded

import com.readnumber.dailyonestep.common.entity.embedded.WithHoldingTax
import java.math.BigDecimal

data class WithHoldingTaxDto(
    val incomeTax: BigDecimal,
    val localIncomeTax: BigDecimal
) {
    companion object {
        fun from(entity: WithHoldingTax?): WithHoldingTaxDto? {
            if (entity == null) return null

            return WithHoldingTaxDto(
                incomeTax = entity.incomeTax,
                localIncomeTax = entity.localIncomeTax
            )
        }
    }
    fun toEmbeddedEntity(): WithHoldingTax {
        return WithHoldingTax(
            incomeTax = incomeTax,
            localIncomeTax = localIncomeTax
        )
    }
}