package com.readnumber.dailyonestep.security.principal

interface Principal {
    fun getId(): Long
    fun getUsername(): String
}