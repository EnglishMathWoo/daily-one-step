package com.readnumber.dailyonestep.common.attribute_converter

import com.readnumber.dailyonestep.common.error.exception.InvalidRequestParameterException
import jakarta.persistence.AttributeConverter
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class LocalDateTimeConverter : AttributeConverter<LocalDateTime?, String?> {
    companion object {
        val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.KOREAN)
    }

    override fun convertToDatabaseColumn(attribute: LocalDateTime?): String? {
        if (attribute == null) return null
        return attribute.toString()
    }

    override fun convertToEntityAttribute(stringAttribute: String?): LocalDateTime? {
        if (stringAttribute.isNullOrEmpty()) {
            return null
        }

        val result = try {
            LocalDateTime.parse(stringAttribute, formatter)
        } catch (e: Exception) {
            throw InvalidRequestParameterException("날짜 포맷 값이 올바르지 않습니다.")
        }

        return result
    }
}