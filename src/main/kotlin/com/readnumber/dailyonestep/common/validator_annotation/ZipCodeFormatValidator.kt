package com.readnumber.dailyonestep.common.validator_annotation

import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext

class ZipCodeFormatValidator(
    private var nullable: Boolean = true
) : ConstraintValidator<ZipCodeFormat, String?> {

    companion object {
        val regEx = Regex("(\\d{5,6})")
    }

    override fun initialize(constraintAnnotation: ZipCodeFormat) {
        super.initialize(constraintAnnotation)
        nullable = constraintAnnotation.nullable
    }

    override fun isValid(zipCode: String?, context: ConstraintValidatorContext): Boolean {
        if (zipCode == null) {
            return nullable
        }

        return zipCode.matches(regEx)
    }
}