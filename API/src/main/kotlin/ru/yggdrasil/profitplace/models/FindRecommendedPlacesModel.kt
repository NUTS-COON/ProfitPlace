package ru.yggdrasil.profitplace.models

data class FindRecommendedPlacesModel(
        val zone: Zone,
        val type: PlaceType,
        val maxPrice: Int,
        val minSpace: Int
)