package com.readnumber.dailyonestep.security.principal

class AdminPrincipal(
    private val adminId: Long,
    private val authorId: Long,
    private val userName: String
) : Principal {

    override fun getId(): Long {
        return adminId
    }
    override fun getAuthorId(): Long {
        return authorId
    }

    override fun getType(): PrincipalType {
        return PrincipalType.ADMIN
    }

    override fun getUsername(): String {
        return userName
    }
}