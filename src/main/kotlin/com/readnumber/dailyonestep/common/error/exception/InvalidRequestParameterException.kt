package com.readnumber.dailyonestep.common.error.exception

import com.readnumber.dailyonestep.common.error.ErrorCodeEnum.INVALID_REQUEST_PARAMETER
import org.springframework.http.HttpStatus

class InvalidRequestParameterException : BaseException {
    constructor(errorMessage: String) : super(
        status = HttpStatus.UNPROCESSABLE_ENTITY,
        errorCode = INVALID_REQUEST_PARAMETER.errorCode,
        errorMessage = errorMessage
    )
}