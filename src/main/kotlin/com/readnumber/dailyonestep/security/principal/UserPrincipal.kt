package com.readnumber.dailyonestep.security.principal

class UserPrincipal(
    private val userId: Long,
    private val authorId: Long,
    private val userName: String
) : Principal {

    override fun getId(): Long {
        return userId
    }
    override fun getAuthorId(): Long {
        return authorId
    }

    override fun getType(): PrincipalType {
        return PrincipalType.USER
    }

    override fun getUsername(): String {
        return userName
    }
}