package com.readnumber.dailyonestep.common.attribute_converter

import com.readnumber.dailyonestep.security.crypto.SymmetricCrypto
import jakarta.persistence.AttributeConverter

class SymmetricCryptoConverter(
    private val symmetricCrypto: SymmetricCrypto
) : AttributeConverter<String?, String?> {
    override fun convertToDatabaseColumn(plain: String?): String? {
        if (plain == null) return null
        return symmetricCrypto.encrypt(plain)
    }

    override fun convertToEntityAttribute(enc: String?): String? {
        if (enc == null) return null
        return symmetricCrypto.decrypt(enc)
    }
}