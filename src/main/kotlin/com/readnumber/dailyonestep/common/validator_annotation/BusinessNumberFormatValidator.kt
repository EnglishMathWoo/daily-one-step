package com.readnumber.dailyonestep.common.validator_annotation

import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext

class BusinessNumberFormatValidator(
    var nullable: Boolean = true
) : ConstraintValidator<BusinessNumberFormat, String?> {
    companion object {
        val regEx = Regex("\\d")
    }

    override fun initialize(constraintAnnotation: BusinessNumberFormat) {
        super.initialize(constraintAnnotation)
        nullable = constraintAnnotation.nullable
    }
    override fun isValid(businessNumber: String?, context: ConstraintValidatorContext): Boolean {
        if (businessNumber == null) return nullable
        if (!listOf(10, 12).contains(businessNumber.length)) return false

        return businessNumber.matches(regEx)
    }
}