package com.readnumber.dailyonestep.common.error.exception

import com.readnumber.dailyonestep.common.error.ErrorCodeEnum.INVALID_TOKEN
import org.springframework.http.HttpStatus

class InvalidTokenException : BaseException {
    constructor(errorMessage: String) : super(
        status = HttpStatus.UNAUTHORIZED,
        errorCode = INVALID_TOKEN.errorCode,
        errorMessage = errorMessage
    )
}