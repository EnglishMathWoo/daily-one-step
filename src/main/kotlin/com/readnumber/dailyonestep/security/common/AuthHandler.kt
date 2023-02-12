package com.readnumber.dailyonestep.security.common

import org.springframework.security.core.Authentication

interface AuthHandler <T : AuthToken<*>> {

    fun authenticate(authToken: T): Authentication
}