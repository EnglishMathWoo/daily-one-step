package com.readnumber.dailyonestep.common.error.exception

import com.readnumber.dailyonestep.common.error.ErrorCodeEnum.INTERNAL_SERVER
import org.springframework.http.HttpStatus

class InternalServerException : BaseException {
    constructor(errorMessage: String) : super(
        status = HttpStatus.INTERNAL_SERVER_ERROR,
        errorCode = INTERNAL_SERVER.errorCode,
        errorMessage = errorMessage
    )
}