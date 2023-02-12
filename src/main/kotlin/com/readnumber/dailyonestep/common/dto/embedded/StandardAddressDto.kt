package com.readnumber.dailyonestep.common.dto.embedded

import com.readnumber.dailyonestep.common.entity.embedded.StandardAddress

data class StandardAddressDto(
    val roadAddress: String,
    val addressDetail: String,
    val zipCode: String,
    val landNumberAddress: String?,
) {
    companion object {
        fun from(entity: StandardAddress?): StandardAddressDto? {
            if (entity == null) return null

            return StandardAddressDto(
                roadAddress = entity.roadAddress,
                addressDetail = entity.addressDetail,
                zipCode = entity.zipCode,
                landNumberAddress = entity.landNumberAddress
            )
        }
    }
    fun toEmbeddedEntity(): StandardAddress {
        return StandardAddress(
            roadAddress = roadAddress,
            addressDetail = addressDetail,
            zipCode = zipCode,
            landNumberAddress = landNumberAddress,
        )
    }
}