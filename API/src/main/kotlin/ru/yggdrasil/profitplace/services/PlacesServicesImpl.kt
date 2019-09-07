package ru.yggdrasil.profitplace.services

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import ru.yggdrasil.profitplace.models.Zone

@Service
class PlacesServicesImpl : PlacesService {

    @Autowired
    lateinit var placesApiService: PlacesApiService

    override fun getNearbyCafeCount(zone: Zone): Int {
        return getNearbyPlacesCount(zone, "Restaurant")
    }

    override fun getNearbyBusStopCount(zone: Zone): Int {
        return getNearbyPlacesCount(zone, "transport")
    }

    private fun getNearbyPlacesCount(zone: Zone, category: String): Int {
        val response = placesApiService.getNearbyPlaces(getArea(zone), category).execute()
        if(response.isSuccessful && response.body() != null){
            return response.body()!!.results.items.size
        }

        return 0
    }

    fun getArea(zone: Zone): String{
        return "${zone.latitude},${zone.longitude};r=${zone.radius}"
    }
}