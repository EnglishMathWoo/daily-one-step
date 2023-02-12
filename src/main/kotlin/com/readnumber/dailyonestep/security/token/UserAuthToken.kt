package com.readnumber.dailyonestep.security.token

import mu.KotlinLogging
import com.readnumber.dailyonestep.security.common.AuthToken
import com.readnumber.dailyonestep.security.crypto.CryptoException
import com.readnumber.dailyonestep.security.crypto.SymmetricCrypto

class UserAuthToken(
    private val symmetricCrypto: SymmetricCrypto,
    private val token: String
) : AuthToken<String> {

    private val logger = KotlinLogging.logger {}

    override fun getValue(): String {
        return symmetricCrypto.decrypt(token)
    }

    override fun isValid(): Boolean {
        try {
            symmetricCrypto.decrypt(token)
            return true
        } catch (e: CryptoException) {
            logger.info("인증에러", e)
        }
        return false
    }
}