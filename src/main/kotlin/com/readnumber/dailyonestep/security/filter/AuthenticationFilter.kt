package com.readnumber.dailyonestep.security.filter

import com.readnumber.dailyonestep.security.common.AuthHandler
import com.readnumber.dailyonestep.security.common.AuthToken
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.util.matcher.AntPathRequestMatcher
import org.springframework.security.web.util.matcher.NegatedRequestMatcher
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException

abstract class AuthenticationFilter<T : AuthToken<*>>(
    pattern: String,
    private val authHeaderName: String,
    private val authHandler: AuthHandler<T>
) :
    OncePerRequestFilter() {

    private val matcher = AntPathRequestMatcher(pattern)
    abstract fun toAuthToken(value: String): T

    private fun extractAuthValue(request: HttpServletRequest): String? {
        return request.getHeader(authHeaderName)
    }

    private fun attemptToAuthenticate(authToken: T) {
        // if auth fail then throw 401 ex
        val authentication = authHandler.authenticate(authToken)
        SecurityContextHolder.getContext().authentication = authentication
    }

    @Throws(ServletException::class, IOException::class)
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val value = extractAuthValue(request)
        if (value != null) {
            val authToken = toAuthToken(value)
            attemptToAuthenticate(authToken)
        }
        filterChain.doFilter(request, response)
    }

    override fun shouldNotFilter(request: HttpServletRequest): Boolean {
        return NegatedRequestMatcher(matcher).matches(request)
    }
}