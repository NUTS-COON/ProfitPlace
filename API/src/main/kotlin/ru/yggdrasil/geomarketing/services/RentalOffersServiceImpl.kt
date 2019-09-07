package ru.yggdrasil.geomarketing.services

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import ru.yggdrasil.geomarketing.models.Coordinates
import ru.yggdrasil.geomarketing.models.RentalOffer
import ru.yggdrasil.geomarketing.models.rentalApi.Coords

@Service
class RentalOffersServiceImpl : RentalOffersService {

    @Autowired
    lateinit var rentalOffersApiService: RentalOffersApiService

    override fun getRentalOffers(categoryId: Int, city: String, dateFrom: String, maxPrice: Int, minSpace: Int, offerType: Int): List<RentalOffer> {
        val response = rentalOffersApiService.getRentalOffers(categoryId, city, dateFrom, maxPrice, offerType).execute()
        if(!response.isSuccessful || response.body() == null){
            return emptyList()
        }

        return response.body()!!.data
                .filter { it.params.area >= minSpace }
                .map {
                    RentalOffer(getCoordinates(it.coords), "${it.city}, ${it.address}", it.price, it.params.area, it.url)
                }
    }

    private fun getCoordinates(coords: Coords): Coordinates {
        return Coordinates(coords.lat, coords.lng)
    }

}