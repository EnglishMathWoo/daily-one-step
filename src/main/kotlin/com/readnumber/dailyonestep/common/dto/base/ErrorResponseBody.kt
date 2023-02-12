package com.readnumber.dailyonestep.common.dto.base

import com.readnumber.dailyonestep.common.error.exception.BaseException

class ErrorResponseBody : BaseResponseBody {
    constructor(exception: BaseException) : super(result = null, exception = exception)
}