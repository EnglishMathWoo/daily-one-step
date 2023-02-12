package com.readnumber.dailyonestep.common.validator_annotation

import jakarta.validation.Constraint
import kotlin.reflect.KClass

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FIELD, AnnotationTarget.VALUE_PARAMETER)
@Constraint(validatedBy = [SocialSecurityNumberFormatValidator::class])
annotation class SocialSecurityNumberFormat(
    val nullable: Boolean = true,
    val message: String = "주민등록번호 포맷이 올바르지 않습니다.",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<*>> = []
)