package com.readnumber.dailyonestep.common.validator_annotation

import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext

class SocialSecurityNumberFormatValidator(
    var nullable: Boolean = true
) : ConstraintValidator<SocialSecurityNumberFormat, String?> {
    companion object {
        // NOTE: https://blog.naver.com/PostView.naver?blogId=mumasa&logNo=222102707153
        // NOTE: 우선은 생년월일만 검증해야할듯...

        val regEx = Regex("\\d{2}([0]\\d|[1][0-2])([0][1-9]|[1-2]\\d|[3][0-1])\\d{7}")
    }

    override fun initialize(constraintAnnotation: SocialSecurityNumberFormat) {
        super.initialize(constraintAnnotation)
        nullable = constraintAnnotation.nullable
    }
    override fun isValid(socialSecurityNumber: String?, context: ConstraintValidatorContext): Boolean {
        if (socialSecurityNumber == null) return nullable
        return socialSecurityNumber.matches(regEx)
    }
}