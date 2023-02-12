package com.readnumber.dailyonestep.common.error.exception

import com.readnumber.dailyonestep.common.error.ErrorCodeEnum.INCORRECT_PASSWORD
import org.springframework.http.HttpStatus

class IncorrectPasswordException : BaseException {
    constructor(errorMessage: String) : super(
        status = HttpStatus.UNAUTHORIZED,
        errorCode = INCORRECT_PASSWORD.errorCode,
        errorMessage = errorMessage
    )
}