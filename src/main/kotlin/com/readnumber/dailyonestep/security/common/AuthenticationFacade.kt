package com.readnumber.dailyonestep.security.common

import com.readnumber.dailyonestep.security.principal.Principal
import org.springframework.security.core.Authentication

interface AuthenticationFacade {
    fun getAuthentication(): Authentication?
    fun getPrincipal(): Principal
}