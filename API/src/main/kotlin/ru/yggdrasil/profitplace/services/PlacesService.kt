package ru.yggdrasil.profitplace.services

import ru.yggdrasil.profitplace.models.Coordinates
import ru.yggdrasil.profitplace.models.placesApi.PlacesResponse

interface PlacesService {
    fun getNearbyCafe(coordinates: Coordinates): PlacesResponse?
    fun getNearbyGoingOutPlaces(coordinates: Coordinates): PlacesResponse?
    fun getNearbyBusStop(coordinates: Coordinates): PlacesResponse?

    fun getNearbyCafeCount(coordinates: Coordinates): Int
    fun getNearbyGoingOutPlacesCount(coordinates: Coordinates): Int
    fun getNearbyBusStopCount(coordinates: Coordinates): Int
}