package com.readnumber.dailyonestep.security.common

interface AuthToken<V> {
    fun getValue(): V?
    fun isValid(): Boolean
}