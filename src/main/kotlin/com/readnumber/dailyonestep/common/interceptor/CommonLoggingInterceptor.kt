package com.readnumber.dailyonestep.common.interceptor

import com.readnumber.dailyonestep.security.common.AuthenticationFacade
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import mu.KotlinLogging
import org.slf4j.MDC
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor
import org.springframework.web.servlet.ModelAndView
import java.util.*

@Component
class CommonLoggingInterceptor(
    private val authenticationFacade: AuthenticationFacade
) : HandlerInterceptor {

    private val logger = KotlinLogging.logger {}

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        if (logger.isDebugEnabled) {
            // 1) Log4j2 Pattern 에 들어갈 unique value (요청 식별자로 사용됩니다.)
            MDC.put("reqUid", UUID.randomUUID().toString())
            // 2) Default Request Logging
            logger.debug("[START] {} {}", request.method, request.requestURI)
            logger.debug("QueryParams: {}", request.queryString)
            // 3) Authentication Logging
            val principal = authenticationFacade.getPrincipal()
            logger.debug(
                "Principal: type={}, id={}, authorId={}",
                principal.getType(),
                principal.getId(),
                principal.getAuthorId()
            )
            logger.debug("Authorization: {}", request.getHeader("Authorization"))
        }
        return true
    }

    override fun postHandle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any,
        modelAndView: ModelAndView?
    ) {
        if (logger.isDebugEnabled) {
            logger.debug("[END] {}", request.requestURI)
        }
    }
}