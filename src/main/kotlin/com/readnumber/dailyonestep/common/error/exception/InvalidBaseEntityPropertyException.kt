package com.readnumber.dailyonestep.common.error.exception

import com.readnumber.dailyonestep.common.error.ErrorCodeEnum
import org.springframework.http.HttpStatus

class InvalidBaseEntityPropertyException : BaseException {
    constructor(errorMessage: String) : super(
        status = HttpStatus.CONFLICT,
        errorCode = ErrorCodeEnum.INVALID_BASE_ENTITY_PROPERTY.errorCode,
        errorMessage = errorMessage
    )
}