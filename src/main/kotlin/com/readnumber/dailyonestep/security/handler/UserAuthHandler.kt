package com.readnumber.dailyonestep.security.handler

import com.readnumber.dailyonestep.security.common.AuthHandler
import com.readnumber.dailyonestep.security.common.Authority
import com.readnumber.dailyonestep.security.principal.UserPrincipal
import com.readnumber.dailyonestep.security.principal.AnonymousPrincipal
import com.readnumber.dailyonestep.security.token.UserAuthToken
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.stereotype.Component

@Component
class UserAuthHandler : AuthHandler<UserAuthToken> {

    override fun authenticate(authToken: UserAuthToken): Authentication {
        if (authToken.isValid()) {
            val claims = authToken.getValue()
            val id = claims.id.toLong()
            val authorId = claims["authorId"].toString().toLong()

            val principal = UserPrincipal(
                userId = id,
                authorId = authorId,
                userName = "user:$id"
            )
            val authorities = AuthorityUtils.commaSeparatedStringToAuthorityList(Authority.ROLE_USER)
            return UsernamePasswordAuthenticationToken(principal, "", authorities)
        } else {
            // 인가가 불허된 principal
            return UsernamePasswordAuthenticationToken(AnonymousPrincipal(), "", AuthorityUtils.NO_AUTHORITIES)
        }
    }
}