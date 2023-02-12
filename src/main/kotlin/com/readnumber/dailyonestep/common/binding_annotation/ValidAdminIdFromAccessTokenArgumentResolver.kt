package com.readnumber.dailyonestep.common.binding_annotation

import com.readnumber.dailyonestep.common.error.exception.InvalidRequestParameterException
import com.readnumber.dailyonestep.common.token_provider.AdminAccessTokenProvider
import jakarta.servlet.http.HttpServletRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.MethodParameter
import org.springframework.stereotype.Component
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer

@Component
class ValidAdminIdFromAccessTokenArgumentResolver : HandlerMethodArgumentResolver {
    @Autowired
    lateinit var accessTokenProvider: AdminAccessTokenProvider

    override fun supportsParameter(methodParameter: MethodParameter): Boolean {
        return methodParameter.getParameterAnnotation(ValidAdminIdFromAccessToken::class.java) != null
    }

    override fun resolveArgument(
        parameter: MethodParameter,
        mavContainer: ModelAndViewContainer?,
        webRequest: NativeWebRequest,
        binderFactory: WebDataBinderFactory?
    ): Long {
        val jwt = innerGetJwt(webRequest.nativeRequest as HttpServletRequest)
        return accessTokenProvider.getAuthenticationIdFromToken(jwt)
    }

    private fun innerGetJwt(request: HttpServletRequest): String {
        val authorization = request.getHeader("Authorization")
        if (authorization.isNullOrEmpty() || !authorization.startsWith("Bearer ")) {
            throw InvalidRequestParameterException("Authorization 헤더값이 존재하지 않습니다.")
        }

        return authorization.substring(7)
    }
}