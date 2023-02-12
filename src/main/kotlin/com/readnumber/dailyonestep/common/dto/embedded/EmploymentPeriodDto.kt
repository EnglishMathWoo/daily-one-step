package com.readnumber.dailyonestep.common.dto.embedded

import com.readnumber.dailyonestep.common.entity.embedded.EmploymentPeriod
import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDate

data class EmploymentPeriodDto(
    @Schema(description = "입사일")
    val joinedAt: LocalDate,
    @Schema(description = "퇴사일")
    val resignedAt: LocalDate?,
    @Schema(description = "퇴사사유")
    val resignReason: String?,
) {
    companion object {
        fun from(entity: EmploymentPeriod?): EmploymentPeriodDto? {
            if (entity == null) return null

            return EmploymentPeriodDto(
                joinedAt = entity.joinedAt,
                resignedAt = entity.resignedAt,
                resignReason = entity.resignReason,
            )
        }
    }
    fun toEmbeddedEntity(): EmploymentPeriod {
        return EmploymentPeriod(
            joinedAt = joinedAt,
            resignedAt = resignedAt,
            resignReason = resignReason,
        )
    }
}