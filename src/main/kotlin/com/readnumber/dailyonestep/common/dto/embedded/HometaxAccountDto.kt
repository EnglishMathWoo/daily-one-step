package com.readnumber.dailyonestep.common.dto.embedded

import com.readnumber.dailyonestep.common.entity.embedded.HometaxAccount

data class HometaxAccountDto(
    val hometaxUsername: String,
    val hometaxPassword: String
) {
    companion object {
        fun from(entity: HometaxAccount?): HometaxAccountDto? {
            if (entity == null) return null

            return HometaxAccountDto(
                hometaxUsername = entity.hometaxUsername,
                hometaxPassword = entity.hometaxPassword
            )
        }
    }
    fun toEmbeddedEntity(): HometaxAccount {
        return HometaxAccount(
            hometaxUsername = hometaxUsername,
            hometaxPassword = hometaxPassword
        )
    }
}