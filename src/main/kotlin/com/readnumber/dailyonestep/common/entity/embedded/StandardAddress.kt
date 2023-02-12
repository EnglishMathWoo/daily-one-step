package com.readnumber.dailyonestep.common.entity.embedded

import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import org.hibernate.annotations.Comment

@Embeddable
class StandardAddress(
    @Column
    @Comment("도로명 주소")
    val roadAddress: String,
    @Column
    @Comment("주소 상세")
    val addressDetail: String,
    @Column
    @Comment("우편 번호")
    val zipCode: String,
    @Column
    @Comment("지번 주소")
    val landNumberAddress: String?,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as StandardAddress

        if (roadAddress != other.roadAddress) return false
        if (addressDetail != other.addressDetail) return false
        if (zipCode != other.zipCode) return false
        if (landNumberAddress != other.landNumberAddress) return false

        return true
    }

    override fun hashCode(): Int {
        var result = roadAddress.hashCode()
        result = 31 * result + addressDetail.hashCode()
        result = 31 * result + zipCode.hashCode()
        result = 31 * result + (landNumberAddress?.hashCode() ?: 0)
        return result
    }
}