package ru.yggdrasil.geomarketing.models.rentalApi

data class RentalOffersResponse(
        val code: Int,
        val `data`: List<Data>,
        val status: String
)