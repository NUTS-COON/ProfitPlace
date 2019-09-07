package ru.yggdrasil.profitplace.models.rentalApi

data class RentalOffersResponse(
        val code: Int,
        val `data`: List<Data>,
        val status: String
)