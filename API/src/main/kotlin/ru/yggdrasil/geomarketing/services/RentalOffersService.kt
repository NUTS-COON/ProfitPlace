package ru.yggdrasil.geomarketing.services

import ru.yggdrasil.geomarketing.models.RentalOffer

interface RentalOffersService {
    fun getRentalOffers(
            categoryId: Int,
            city: String,
            dateFrom: String,
            maxPrice: Int,
            minSpace: Int,
            offerType: Int): List<RentalOffer>
}