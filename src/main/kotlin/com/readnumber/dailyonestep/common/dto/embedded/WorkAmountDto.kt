package com.readnumber.dailyonestep.common.dto.embedded

import com.readnumber.dailyonestep.common.entity.embedded.WorkAmount
import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "근로량")
data class WorkAmountDto(
    @Schema(description = "근로일")
    val workingDay: Int?,
    @Schema(description = "근로시간")
    val workingHour: Int?
) {
    companion object {
        fun from(entity: WorkAmount?): WorkAmountDto? {
            if (entity == null) return null

            return WorkAmountDto(
                workingDay = entity.workingDay,
                workingHour = entity.workingHour
            )
        }
    }
    fun toEmbeddedEntity(): WorkAmount {
        return WorkAmount(
            workingDay = workingDay,
            workingHour = workingHour
        )
    }
}