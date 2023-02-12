package com.readnumber.dailyonestep.common.error.exception

import com.readnumber.dailyonestep.common.error.ErrorCodeEnum.INVALID_AUTHORITY
import org.springframework.http.HttpStatus

class InvalidAuthorityException : BaseException {
    constructor(errorMessage: String) : super(
        status = HttpStatus.CONFLICT,
        errorCode = INVALID_AUTHORITY.errorCode,
        errorMessage = errorMessage
    )
}