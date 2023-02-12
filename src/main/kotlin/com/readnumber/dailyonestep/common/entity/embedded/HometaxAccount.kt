package com.readnumber.dailyonestep.common.entity.embedded

import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import org.hibernate.annotations.Comment

@Embeddable
class HometaxAccount(
    @Column
    @Comment("홈택스 username")
    val hometaxUsername: String,
    @Column
    @Comment("홈택스 password")
    val hometaxPassword: String,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as HometaxAccount

        if (hometaxUsername != other.hometaxUsername) return false
        if (hometaxPassword != other.hometaxPassword) return false

        return true
    }

    override fun hashCode(): Int {
        var result = hometaxUsername.hashCode()
        result = 31 * result + hometaxPassword.hashCode()
        return result
    }
}