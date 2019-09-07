package ru.yggdrasil.profitplace.models

data class RentalOffer(
        val coordinates: Coordinates,
        val address: String,
        val price: Int,
        val space: Double,
        val offerUrl: String
)