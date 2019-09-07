package ru.yggdrasil.profitplace.services

import ru.yggdrasil.profitplace.models.RentalOffer

interface RentalOffersService {
    fun getRentalOffers(
            categoryId: Int,
            city: String,
            dateFrom: String,
            maxPrice: Int,
            minSpace: Int,
            offerType: Int): List<RentalOffer>
}