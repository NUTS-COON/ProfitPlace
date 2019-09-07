package ru.yggdrasil.profitplace.services

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import ru.yggdrasil.profitplace.models.Coordinates
import ru.yggdrasil.profitplace.models.RentalOffer
import ru.yggdrasil.profitplace.models.rentalApi.Coords

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
                .filter {
                    if(!it.params.containsKey("Площадь")){
                        return@filter false
                    }

                    val space = it.params["Площадь"]?.toDoubleOrNull()
                    return@filter space != null && space >= minSpace
                }
                .map {
                    RentalOffer(getCoordinates(it.coords), "${it.city}, ${it.address}", it.price, it.params["Площадь"]!!.toDouble(), it.url)
                }
    }

    private fun getCoordinates(coords: Coords): Coordinates {
        return Coordinates(coords.lat, coords.lng)
    }

}