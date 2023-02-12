package com.readnumber.dailyonestep.common.validator_annotation

import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext

class PriceFormatValidator(
    var nullable: Boolean = true
) : ConstraintValidator<PriceFormat, Long?> {
    override fun initialize(constraintAnnotation: PriceFormat) {
        super.initialize(constraintAnnotation)
        nullable = constraintAnnotation.nullable
    }

    override fun isValid(price: Long?, context: ConstraintValidatorContext): Boolean {
        if (price == null) {
            return nullable
        }

        return price > 0L
    }
}