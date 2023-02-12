package com.readnumber.dailyonestep.security.filter

import com.readnumber.dailyonestep.common.token_provider.UserRefreshTokenProvider
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException

class UserRefreshTokenFilter(
    private val tokenProvider: UserRefreshTokenProvider
) : OncePerRequestFilter() {

    private fun getJwtFromHeader(request: HttpServletRequest): String? {
        val authorization = request.getHeader("Authorization")
        if (authorization.isNullOrEmpty() || !authorization.startsWith("Bearer ")) {
            return null
        }

        return authorization.substring(7)
    }

    @Throws(ServletException::class, IOException::class)
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val jwt = getJwtFromHeader(request)
        if (jwt != null) {
            val authentication = tokenProvider.getAuthenticationFromToken(jwt)
            SecurityContextHolder.getContext().authentication = authentication
        }

        filterChain.doFilter(request, response)
    }
}