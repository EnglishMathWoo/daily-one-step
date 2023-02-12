package com.readnumber.dailyonestep.security.principal

class AnonymousPrincipal : Principal {

    override fun getId(): Long {
        return -1L
    }
    override fun getAuthorId(): Long {
        return -1
    }

    override fun getType(): PrincipalType {
        return PrincipalType.ANONYMOUS
    }

    override fun getUsername(): String {
        return "ANONYMOUS"
    }
}