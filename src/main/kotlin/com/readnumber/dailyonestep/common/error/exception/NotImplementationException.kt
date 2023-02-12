package com.readnumber.dailyonestep.common.error.exception

import com.readnumber.dailyonestep.common.error.ErrorCodeEnum.NOT_IMPLEMENTATION
import org.springframework.http.HttpStatus

class NotImplementationException : BaseException {
    constructor(errorMessage: String) : super(
        status = HttpStatus.NOT_IMPLEMENTED,
        errorCode = NOT_IMPLEMENTATION.errorCode,
        errorMessage = errorMessage
    )
}