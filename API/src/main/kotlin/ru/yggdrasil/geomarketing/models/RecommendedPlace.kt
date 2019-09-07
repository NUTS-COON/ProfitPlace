package ru.yggdrasil.geomarketing.models

data class RecommendedPlace(
        val coords: Coordinates,
        val address: String,
        val price: Int,
        val space: Double,
        val rating: Double,
        val offerUrl: String
)

