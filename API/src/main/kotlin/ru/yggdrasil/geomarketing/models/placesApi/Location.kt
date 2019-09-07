package ru.yggdrasil.geomarketing.models.placesApi

data class Location(
        val address: Address,
        val position: List<Double>
)