package com.readnumber.dailyonestep.security.handler

import com.readnumber.dailyonestep.security.common.AuthHandler
import com.readnumber.dailyonestep.security.common.Authority
import com.readnumber.dailyonestep.security.principal.AdminPrincipal
import com.readnumber.dailyonestep.security.principal.AnonymousPrincipal
import com.readnumber.dailyonestep.security.token.AdminAuthToken
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.stereotype.Component

@Component
class AdminAuthHandler : AuthHandler<AdminAuthToken> {

    override fun authenticate(authToken: AdminAuthToken): Authentication {
        if (authToken.isValid()) {
            val claims = authToken.getValue()
            val id = claims.id.toLong()
            val authorId = claims["authorId"].toString().toLong()

            val principal = AdminPrincipal(
                adminId = id,
                authorId = authorId,
                userName = "admin:$id"
            )
            val authorities = AuthorityUtils.commaSeparatedStringToAuthorityList(Authority.ROLE_ADMIN)
            return UsernamePasswordAuthenticationToken(principal, "", authorities)
        } else {
            // 인가가 불허된 principal
            return UsernamePasswordAuthenticationToken(AnonymousPrincipal(), "", AuthorityUtils.NO_AUTHORITIES)
        }
    }
}