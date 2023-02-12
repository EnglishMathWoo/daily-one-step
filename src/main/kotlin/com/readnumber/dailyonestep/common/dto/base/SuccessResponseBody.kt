package com.readnumber.dailyonestep.common.dto.base

class SuccessResponseBody : BaseResponseBody {
    constructor(result: Any) : super(result = result, exception = null)
}