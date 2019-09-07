package ru.yggdrasil.geomarketing.services

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import ru.yggdrasil.geomarketing.models.FindRecommendedPlacesModel
import ru.yggdrasil.geomarketing.models.RecommendedPlace
import java.text.SimpleDateFormat
import java.util.*

@Service
class SearchPlaceServiceImpl : SearchPlaceService {

    @Autowired
    lateinit var rentalOffersService: RentalOffersService
    @Autowired
    lateinit var placesService: PlacesService

    private val dateFormatter = SimpleDateFormat("yyyy-MM-dd")


    override fun getRecommendedPlaces(model: FindRecommendedPlacesModel): List<RecommendedPlace> {
        val handler = Dispatchers.IO + CoroutineExceptionHandler { _, exception ->

        }

        return runBlocking(handler) {
            val rentalTask = async {
                return@async rentalOffersService.getRentalOffers(7, "Краснодарский край", getFormattedStartDate(), model.maxPrice, model.minSpace, 2)
            }

            val cafeTask = async {
                return@async placesService.getNearbyCafeCount(model.zone)
            }

            val busStopTask = async {
                return@async placesService.getNearbyBusStopCount(model.zone)
            }

            val cafeCount = cafeTask.await()
            val busStopCount = busStopTask.await()
            val rental = rentalTask.await()
            return@runBlocking rental.map {
                RecommendedPlace(
                        it.coordinates,
                        it.address,
                        it.price,
                        it.space,
                        getRating(cafeCount, busStopCount),
                        it.offerUrl)
            }
        }
    }

    fun getRating(placesCount: Int, busStopCount: Int): Double{
        return 5.0
    }

    fun getFormattedStartDate(): String{
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_YEAR, -30)

        return dateFormatter.format(calendar.time)
    }
}