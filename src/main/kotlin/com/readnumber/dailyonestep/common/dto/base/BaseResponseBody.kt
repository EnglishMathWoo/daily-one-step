package com.readnumber.dailyonestep.common.dto.base

import com.readnumber.dailyonestep.common.error.exception.BaseException

abstract class BaseResponseBody {
    val result: Any?
    val error: ErrorDetail?
    constructor(result: Any?, exception: BaseException?) {
        this.result = result
        this.error = if (exception != null) ErrorDetail(exception) else null
    }
    inner class ErrorDetail {
        val errorCode: String
        val errorMessage: String?
        constructor(exception: BaseException) {
            this.errorCode = exception.errorCode
            this.errorMessage = exception.errorMessage.ifEmpty { exception.message }
        }
    }
}