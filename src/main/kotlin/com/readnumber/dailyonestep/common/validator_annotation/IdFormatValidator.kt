package com.readnumber.dailyonestep.common.validator_annotation

import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext

class IdFormatValidator(
    var nullable: Boolean = true
) : ConstraintValidator<IdFormat, Long?> {

    override fun initialize(constraintAnnotation: IdFormat) {
        super.initialize(constraintAnnotation)
        nullable = constraintAnnotation.nullable
    }

    override fun isValid(id: Long?, context: ConstraintValidatorContext): Boolean {
        if (id == null) {
            return nullable
        }

        return id > 0
    }
}