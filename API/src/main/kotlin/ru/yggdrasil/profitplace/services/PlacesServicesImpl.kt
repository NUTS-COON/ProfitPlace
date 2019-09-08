package ru.yggdrasil.profitplace.services

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import ru.yggdrasil.profitplace.models.Coordinates
import ru.yggdrasil.profitplace.models.placesApi.PlacesResponse

@Service
class PlacesServicesImpl : PlacesService {

    @Autowired
    lateinit var placesApiService: PlacesApiService

    override fun getNearbyCafe(coordinates: Coordinates): PlacesResponse? {
        return exploreNearbyPlaces(coordinates, "Restaurant")
    }

    override fun getNearbyGoingOutPlaces(coordinates: Coordinates): PlacesResponse? {
        return exploreNearbyPlaces(coordinates, "going-out")
    }

    override fun getNearbyBusStop(coordinates: Coordinates): PlacesResponse? {
        return placesApiService.searchNearbyPlaces(getArea(coordinates), "Общественный транспорт", 1000)
                .execute()
                .body()
    }

    override fun getNearbyCafeCount(coordinates: Coordinates): Int {
        return exploreNearbyPlacesCount(coordinates, "Restaurant")
    }

    override fun getNearbyGoingOutPlacesCount(coordinates: Coordinates): Int {
        return exploreNearbyPlacesCount(coordinates, "going-out")
    }

    override fun getNearbyBusStopCount(coordinates: Coordinates): Int {
        val response = placesApiService.searchNearbyPlaces(getArea(coordinates), "Общественный транспорт", 1000).execute()
        if(response.isSuccessful && response.body() != null){
            return response.body()!!.results.items.size
        }

        return 0
    }

    private fun exploreNearbyPlacesCount(coordinates: Coordinates, category: String): Int{
        val response = placesApiService.exploreNearbyPlaces(getArea(coordinates), category, 1000).execute()
        if(response.isSuccessful && response.body() != null){
            return response.body()!!.results.items.size
        }

        return 0
    }

    private fun exploreNearbyPlaces(coordinates: Coordinates, category: String): PlacesResponse? {
        return placesApiService.exploreNearbyPlaces(getArea(coordinates), category, 1000)
                .execute()
                .body()
    }

    fun getArea(coordinates: Coordinates): String{
        return "${coordinates.latitude},${coordinates.longitude};r=600"
    }
}