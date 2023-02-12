package com.readnumber.dailyonestep.common.error.exception

import com.readnumber.dailyonestep.common.error.ErrorCodeEnum.ACCESS_DENIED_RESOURCE
import org.springframework.http.HttpStatus

class AccessDeniedResourceException : BaseException {
    constructor(errorMessage: String) : super(
        status = HttpStatus.FORBIDDEN,
        errorCode = ACCESS_DENIED_RESOURCE.errorCode,
        errorMessage = errorMessage
    )
}