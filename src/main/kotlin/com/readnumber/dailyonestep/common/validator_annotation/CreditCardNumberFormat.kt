package com.readnumber.dailyonestep.common.validator_annotation

import jakarta.validation.Constraint
import kotlin.reflect.KClass

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FIELD, AnnotationTarget.VALUE_PARAMETER)
@Constraint(validatedBy = [CreditCardNumberFormatValidator::class])
annotation class CreditCardNumberFormat(
    val nullable: Boolean = true,
    val message: String = "카드 번호 포맷이 올바르지 않습니다.",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<*>> = []
)