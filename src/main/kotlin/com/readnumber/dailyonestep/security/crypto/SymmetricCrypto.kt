package com.readnumber.dailyonestep.security.crypto

interface SymmetricCrypto {
    fun encrypt(plainText: String): String
    fun decrypt(cipherText: String?): String
}