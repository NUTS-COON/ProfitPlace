package ru.yggdrasil.geomarketing.models.placesApi

data class Address(
    val city: String,
    val country: String,
    val countryCode: String,
    val county: String,
    val district: String,
    val house: String,
    val postalCode: String,
    val stateCode: String,
    val street: String,
    val text: String
)