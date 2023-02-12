package com.readnumber.dailyonestep.security.crypto

class FakeSymmetricCryptoImpl : SymmetricCrypto {
    override fun encrypt(plainText: String): String {
        return ""
    }

    override fun decrypt(cipherText: String?): String {
        return ""
    }
}