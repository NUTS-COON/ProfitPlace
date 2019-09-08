package ru.yggdrasil.profitplace.services

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import ru.yggdrasil.profitplace.models.FindRecommendedPlacesModel
import ru.yggdrasil.profitplace.models.RecommendedPlace
import java.text.SimpleDateFormat
import java.util.*
import java.util.stream.Collectors
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

@Service
class SearchPlaceServiceImpl : SearchPlaceService {

    @Autowired
    lateinit var rentalOffersService: RentalOffersService
    @Autowired
    lateinit var placesService: PlacesService

    private val dateFormatter = SimpleDateFormat("yyyy-MM-dd")


    override fun getRecommendedPlaces(model: FindRecommendedPlacesModel): List<RecommendedPlace> {

        return runBlocking(Dispatchers.IO) {

            val rentalPlaces = rentalOffersService.getRentalOffers(7, "Краснодарский край", getFormattedStartDate(), model.maxPrice, model.minSpace, 2)
            return@runBlocking rentalPlaces
                    .filter {
                        getDistance(
                                it.coordinates.latitude,
                                it.coordinates.longitude,
                                model.zone.latitude,
                                model.zone.longitude) <= model.zone.radius
                    }
                    .parallelStream()
                    .map {

                        val cafeTask = async {
                            return@async placesService.getNearbyCafeCount(it.coordinates)
                        }

                        val goingOutPlacesTask = async {
                            return@async placesService.getNearbyGoingOutPlacesCount(it.coordinates)
                        }

                        val busStopTask = async {
                            return@async placesService.getNearbyBusStopCount(it.coordinates)
                        }

                        runBlocking {
                            val cafeCount = cafeTask.await()
                            val goingOutPlacesCount = goingOutPlacesTask.await()
                            val busStopCount = busStopTask.await()
                            val rating = getRating(cafeCount, busStopCount, goingOutPlacesCount)

                            RecommendedPlace(
                                    it.coordinates,
                                    it.address,
                                    it.price,
                                    it.space,
                                    rating,
                                    cafeCount,
                                    busStopCount,
                                    goingOutPlacesCount,
                                    it.offerUrl)
                        }
                    }
                    .collect(Collectors.toList())
        }
    }

    private fun degreesToRadians(degrees: Double): Double{
        return degrees * Math.PI / 180
    }

    private fun getDistance(lat1: Double, lng1: Double, lat2: Double, lng2: Double): Double{
        val earthRadiusKm = 6371000

        val dLat = degreesToRadians(lat2-lat1)
        val dLon = degreesToRadians(lng2-lng1)

        val newLat1 = degreesToRadians(lat1)
        val newLat2 = degreesToRadians(lat2)

        val a = sin(dLat/2) * sin(dLat/2) +
                sin(dLon/2) * sin(dLon/2) * cos(newLat1) * cos(newLat2)
        val c = 2 * atan2(sqrt(a), sqrt(1-a))
        return earthRadiusKm * c
    }

    fun getRating(placesCount: Int, busStopCount: Int, goingOutPlacesCount: Int): Double {
        var rating = 0.0

        if(placesCount <= 30){
            rating += (5 - placesCount * (5 / 30))
        }

        if(busStopCount > 4){
            rating += 1
        }else{
            rating += busStopCount * 0.1
        }

        if(goingOutPlacesCount > 7){
            rating += 4
        }else{
            rating += goingOutPlacesCount * 0.1
        }

        return rating
    }

    fun getFormattedStartDate(): String{
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_YEAR, -30)

        return dateFormatter.format(calendar.time)
    }
}