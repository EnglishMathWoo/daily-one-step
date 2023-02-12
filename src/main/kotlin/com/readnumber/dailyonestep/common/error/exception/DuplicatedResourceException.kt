package com.readnumber.dailyonestep.common.error.exception

import com.readnumber.dailyonestep.common.error.ErrorCodeEnum.DUPLICATED_RESOURCE
import org.springframework.http.HttpStatus

class DuplicatedResourceException : BaseException {
    constructor(errorMessage: String) : super(
        status = HttpStatus.CONFLICT,
        errorCode = DUPLICATED_RESOURCE.errorCode,
        errorMessage = errorMessage
    )
}