package com.readnumber.dailyonestep.common.dto.embedded

import com.readnumber.dailyonestep.common.entity.embedded.TwoMajorInsurance
import java.math.BigDecimal

data class TwoMajorInsuranceDto(
    val nationalPension: BigDecimal,
    val employmentInsurance: BigDecimal
) {
    companion object {
        fun from(entity: TwoMajorInsurance?): TwoMajorInsuranceDto? {
            if (entity == null) return null

            return TwoMajorInsuranceDto(
                nationalPension = entity.nationalPension,
                employmentInsurance = entity.employmentInsurance
            )
        }
    }
    fun toEmbeddedEntity(): TwoMajorInsurance {
        return TwoMajorInsurance(
            nationalPension = nationalPension,
            employmentInsurance = employmentInsurance
        )
    }
}