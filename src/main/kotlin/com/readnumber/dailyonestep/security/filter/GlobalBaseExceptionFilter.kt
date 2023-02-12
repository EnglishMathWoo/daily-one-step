package com.readnumber.dailyonestep.security.filter

import com.fasterxml.jackson.databind.ObjectMapper
import com.readnumber.dailyonestep.common.dto.base.ErrorResponseBody
import com.readnumber.dailyonestep.common.error.exception.BaseException
import com.readnumber.dailyonestep.common.error.exception.InternalServerException
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.MediaType
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException

// FIXME: 리펙토링 해야함.
class GlobalBaseExceptionFilter : OncePerRequestFilter() {
    @Throws(ServletException::class, IOException::class, BaseException::class)
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        try {
            filterChain.doFilter(request, response)
        } catch (e: BaseException) {
            setErrorResponse(response, e)
        } catch (e: Exception) {
            e.printStackTrace()
            val baseException: BaseException = InternalServerException(e.message!!)
            setErrorResponse(response, baseException)
        }
    }

    private fun setErrorResponse(
        response: HttpServletResponse,
        baseException: BaseException,
    ) {
        val objectMapper = ObjectMapper()
        val responseBody = ErrorResponseBody(baseException)

        response.status = baseException.status.value()
        response.contentType = MediaType.APPLICATION_JSON_VALUE
        response.characterEncoding = "UTF-8"

        try {
            response.writer.write(objectMapper.writeValueAsString(responseBody))
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}