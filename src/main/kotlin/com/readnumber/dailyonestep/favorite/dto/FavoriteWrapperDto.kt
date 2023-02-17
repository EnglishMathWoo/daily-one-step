package com.readnumber.dailyonestep.favorite.dto

class FavoriteWrapperDto(
    val favorite: FavoriteDto
) {
    companion object {
        fun from(dto: FavoriteDto): FavoriteWrapperDto {
            return FavoriteWrapperDto(dto)
        }
    }
}