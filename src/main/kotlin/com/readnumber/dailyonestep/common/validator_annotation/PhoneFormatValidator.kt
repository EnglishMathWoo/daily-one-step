package com.readnumber.dailyonestep.common.validator_annotation

import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext

class PhoneFormatValidator(
    var nullable: Boolean = true
) : ConstraintValidator<PhoneFormat, String?> {

    companion object {
        val regEx = Regex("(01[016789])(\\d{3,4})(\\d{4})")
    }

    override fun initialize(constraintAnnotation: PhoneFormat) {
        super.initialize(constraintAnnotation)
        nullable = constraintAnnotation.nullable
    }

    override fun isValid(phone: String?, context: ConstraintValidatorContext): Boolean {
        if (phone == null) {
            return nullable
        }

        return phone.matches(regEx)
    }
}