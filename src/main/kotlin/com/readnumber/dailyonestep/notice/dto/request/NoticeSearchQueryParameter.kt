package com.readnumber.dailyonestep.notice.dto.request

import com.readnumber.dailyonestep.common.attribute_converter.UrlDecodingConverter
import jakarta.persistence.Convert

data class NoticeSearchQueryParameter(
    @Convert(converter = UrlDecodingConverter::class)
    val keyword: String?
)
