package com.readnumber.dailyonestep.common.dto

class MultipleResourceResponseDto<T>(
    val totalCount: Long,
    val resources: List<T>
) {
    companion object {
        fun <T> from(resources: List<T>): MultipleResourceResponseDto<T> {
            return MultipleResourceResponseDto(
                totalCount = resources.size.toLong(),
                resources = resources
            )
        }
    }
}