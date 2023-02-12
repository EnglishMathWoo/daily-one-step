package com.readnumber.dailyonestep.common.error.exception

import com.readnumber.dailyonestep.common.error.ErrorCodeEnum.EXTERNAL_SERVER
import org.springframework.http.HttpStatus

class ExternalServerException : BaseException {
    constructor(errorMessage: String) : super(
        status = HttpStatus.INTERNAL_SERVER_ERROR,
        errorCode = EXTERNAL_SERVER.errorCode,
        errorMessage = errorMessage
    )
}