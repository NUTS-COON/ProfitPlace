package ru.yggdrasil.profitplace.services

import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import ru.yggdrasil.profitplace.models.Coordinates
import ru.yggdrasil.profitplace.models.ThermalPoint
import ru.yggdrasil.profitplace.models.placesApi.PlacesResponse

@Service
class DetailAnalyzeServiceImpl : DetailAnalyzeService {

    @Autowired
    lateinit var placesService: PlacesService

    override fun getThermalMapping(coordinates: Coordinates): List<ThermalPoint> {
        return runBlocking {

            val cafeTask = async {
                return@async getThermalPoints(placesService.getNearbyCafe(coordinates), 0.8)
            }

            val goingOutPlacesTask = async {
                return@async getThermalPoints(placesService.getNearbyGoingOutPlaces(coordinates))
            }

            val busStopTask = async {
                return@async getThermalPoints(placesService.getNearbyBusStop(coordinates))
            }

            val cafe = cafeTask.await()
            val goingOutPlace = goingOutPlacesTask.await()
            val busStop = busStopTask.await()

            val res = cafe.toMutableList()
            res.addAll(goingOutPlace)
            res.addAll(busStop)
            res
        }
    }

    private fun getThermalPoints(placesResponse: PlacesResponse?, alpha: Double = 1.0): List<ThermalPoint> {
        if(placesResponse == null){
            return emptyList()
        }

        return placesResponse.results.items.map {
            ThermalPoint(
                    it.position[0],
                    it.position[1],
                    (it.distance / 600.0) * alpha
            )
        }
    }
}