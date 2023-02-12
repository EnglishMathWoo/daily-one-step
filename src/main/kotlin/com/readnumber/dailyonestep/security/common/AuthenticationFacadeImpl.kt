package com.readnumber.dailyonestep.security.common

import com.readnumber.dailyonestep.security.principal.AnonymousPrincipal
import com.readnumber.dailyonestep.security.principal.Principal
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service

@Service
class AuthenticationFacadeImpl : AuthenticationFacade {
    override fun getAuthentication(): Authentication? {
        return SecurityContextHolder.getContext().authentication
    }

    override fun getPrincipal(): Principal {
        val authentication = getAuthenticationOrThrowEx()
        val principal = authentication.principal

        if (isAnonymous(principal)) {
            return AnonymousPrincipal()
        }

        if (principal is Principal) {
            return principal
        }

        throw AccessDeniedException("허가되지 않은 접근 입니다.")
    }

    private fun getAuthenticationOrThrowEx(): Authentication {
        // security filter chain 을 통해 들어온 요청이 아닐 시 null 입니다.
        return SecurityContextHolder.getContext().authentication
            ?: throw AccessDeniedException("허가되지 않은 접근 입니다.")
    }

    private fun isAnonymous(principal: Any): Boolean {
        // Test 코드 실행시 익명 인증객체의 principal 를 위함
        // @WithAnonymousUser
        return principal == "anonymous" || principal == "anonymousUser"
    }
}