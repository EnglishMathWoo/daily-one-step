package com.readnumber.dailyonestep.security.principal

class AnonymousPrincipal : Principal {

    override fun getId(): Long {
        return -1L
    }

    override fun getUsername(): String {
        return "ANONYMOUS"
    }
}