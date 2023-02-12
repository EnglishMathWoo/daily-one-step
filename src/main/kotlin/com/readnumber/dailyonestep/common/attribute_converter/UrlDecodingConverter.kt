package com.readnumber.dailyonestep.common.attribute_converter

import jakarta.persistence.AttributeConverter
import java.net.URLDecoder

class UrlDecodingConverter : AttributeConverter<String?, String?> {
    override fun convertToDatabaseColumn(attribute: String?): String? {
        if (attribute.isNullOrEmpty()) return null
        return URLDecoder.decode(attribute, "UTF-8")
    }

    override fun convertToEntityAttribute(stringAttribute: String?): String? {
        if (stringAttribute.isNullOrEmpty()) return null
        return URLDecoder.decode(stringAttribute, "UTF-8")
    }
}