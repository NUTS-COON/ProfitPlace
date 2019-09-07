package ru.yggdrasil.profitplace.services

import ru.yggdrasil.profitplace.models.Coordinates

interface PlacesService {
    fun getNearbyCafeCount(coordinates: Coordinates): Int
    fun getNearbyGoingOutPlacesCount(coordinates: Coordinates): Int
    fun getNearbyBusStopCount(coordinates: Coordinates): Int
}