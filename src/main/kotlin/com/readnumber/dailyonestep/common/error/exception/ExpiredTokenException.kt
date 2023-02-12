package com.readnumber.dailyonestep.common.error.exception

import com.readnumber.dailyonestep.common.error.ErrorCodeEnum.EXPIRED_TOKEN
import org.springframework.http.HttpStatus

class ExpiredTokenException : BaseException {
    constructor(errorMessage: String) : super(
        status = HttpStatus.UNAUTHORIZED,
        errorCode = EXPIRED_TOKEN.errorCode,
        errorMessage = errorMessage
    )
}