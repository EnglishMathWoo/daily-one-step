package com.readnumber.dailyonestep.common.validator_annotation

import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext

class CreditCardNumberFormatValidator(
    var nullable: Boolean = true
) : ConstraintValidator<CreditCardNumberFormat, String?> {
    companion object {
        val regEx = Regex("(\\d{15,16})")
    }

    override fun initialize(constraintAnnotation: CreditCardNumberFormat) {
        super.initialize(constraintAnnotation)
        nullable = constraintAnnotation.nullable
    }
    override fun isValid(creditCardNumber: String?, context: ConstraintValidatorContext): Boolean {
        if (creditCardNumber == null) return nullable
        if (!creditCardNumber.matches(regEx)) return false

        return validByLuhnFormula(creditCardNumber)
    }

    private fun validByLuhnFormula(cardNumber: String): Boolean {
        // NOTE: https://ahnanne.tistory.com/67
        // NOTE: https://ko.wikipedia.org/wiki/%EB%A3%AC_%EC%95%8C%EA%B3%A0%EB%A6%AC%EC%A6%98

        val rawNumbers = cardNumber.split("").filter { it.isNotEmpty() }.map { it.toInt() }
        val lastNumber = rawNumbers.last()

        val numbers = rawNumbers.subList(0, rawNumbers.lastIndex).reversed()
        val sum = numbers
            .mapIndexed { i, v -> if (i % 2 == 0) v * 2 else v }
            .sumOf { if (it > 9) it - 9 else it }

        return (sum + lastNumber) % 10 == 0
    }
}