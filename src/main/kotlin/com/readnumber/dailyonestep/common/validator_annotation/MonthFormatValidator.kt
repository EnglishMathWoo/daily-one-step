package com.readnumber.dailyonestep.common.validator_annotation

import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext

class MonthFormatValidator(
    var nullable: Boolean = true
) : ConstraintValidator<MonthFormat, Int?> {
    override fun initialize(constraintAnnotation: MonthFormat) {
        super.initialize(constraintAnnotation)
        nullable = constraintAnnotation.nullable
    }

    override fun isValid(month: Int?, context: ConstraintValidatorContext): Boolean {
        if (month == null) return nullable

        return month in 1..12
    }
}