package com.readnumber.dailyonestep.common.dto

import com.readnumber.dailyonestep.common.error.exception.BaseException

class ErrorResponseBody : BaseResponseBody {
    constructor(exception: BaseException) : super(result = null, exception = exception)
}