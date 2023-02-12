package com.readnumber.dailyonestep.common.validator_annotation

import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext

class MonthlyDateFormatValidator(
    var nullable: Boolean = true
) : ConstraintValidator<MonthlyDateFormat, Int?> {

    companion object {
        val regEx = Regex("(01[016789])(\\d{3,4})(\\d{4})")
    }

    override fun initialize(constraintAnnotation: MonthlyDateFormat) {
        super.initialize(constraintAnnotation)
        nullable = constraintAnnotation.nullable
    }

    override fun isValid(monthlyDate: Int?, context: ConstraintValidatorContext): Boolean {
        if (monthlyDate == null) {
            return nullable
        }

        return monthlyDate in 1..31
    }
}