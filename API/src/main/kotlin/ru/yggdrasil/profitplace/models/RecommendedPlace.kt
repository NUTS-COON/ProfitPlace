package ru.yggdrasil.profitplace.models

data class RecommendedPlace(
        val coords: Coordinates,
        val address: String,
        val price: Int,
        val space: Double,
        val rating: Double,
        val numberOfCompetitors: Int,
        val numberOfBusStop: Int,
        val numberOfInterestingPlaces: Int,
        val offerUrl: String
)

