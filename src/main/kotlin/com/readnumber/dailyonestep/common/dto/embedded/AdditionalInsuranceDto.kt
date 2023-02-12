package com.readnumber.dailyonestep.common.dto.embedded

import com.readnumber.dailyonestep.common.entity.embedded.AdditionalInsurance
import java.math.BigDecimal

data class AdditionalInsuranceDto(
    val healthInsurance: BigDecimal,
    val longTermCareInsurance: BigDecimal
) {
    companion object {
        fun from(entity: AdditionalInsurance?): AdditionalInsuranceDto? {
            if (entity == null) return null

            return AdditionalInsuranceDto(
                healthInsurance = entity.healthInsurance,
                longTermCareInsurance = entity.longTermCareInsurance
            )
        }
    }
    fun toEmbeddedEntity(): AdditionalInsurance {
        return AdditionalInsurance(
            healthInsurance = healthInsurance,
            longTermCareInsurance = longTermCareInsurance
        )
    }
}