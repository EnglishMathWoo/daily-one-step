package com.readnumber.dailyonestep.post.dto.request

import com.readnumber.dailyonestep.common.attribute_converter.UrlDecodingConverter
import jakarta.persistence.Convert

data class PostSearchQueryParameter(
    @Convert(converter = UrlDecodingConverter::class)
    val keyword: String?
)