package com.readnumber.dailyonestep.common.error.exception

import com.readnumber.dailyonestep.common.error.ErrorCodeEnum.NOT_FOUND_RESOURCE
import org.springframework.http.HttpStatus

class NotFoundResourceException : BaseException {
    constructor(errorMessage: String) : super(
        status = HttpStatus.NOT_FOUND,
        errorCode = NOT_FOUND_RESOURCE.errorCode,
        errorMessage = errorMessage
    )
}