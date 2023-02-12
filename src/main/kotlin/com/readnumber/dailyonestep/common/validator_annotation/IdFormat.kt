package com.readnumber.dailyonestep.common.validator_annotation

import jakarta.validation.Constraint
import kotlin.reflect.KClass

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FIELD)
@Constraint(validatedBy = [IdFormatValidator::class])
annotation class IdFormat(
    val nullable: Boolean = true,
    val message: String = "Id(Pk) 포맷이 올바르지 않습니다.",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<*>> = []
)