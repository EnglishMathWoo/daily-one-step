package com.readnumber.dailyonestep.common.error.exception

import org.springframework.http.HttpStatus

abstract class BaseException(
    val errorCode: String,
    val status: HttpStatus,
    val errorMessage: String
) : RuntimeException()