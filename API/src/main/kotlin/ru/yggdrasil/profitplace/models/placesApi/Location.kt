package ru.yggdrasil.profitplace.models.placesApi

data class Location(
        val address: Address,
        val position: List<Double>
)