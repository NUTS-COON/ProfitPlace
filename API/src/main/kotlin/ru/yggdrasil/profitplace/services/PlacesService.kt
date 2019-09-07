package ru.yggdrasil.profitplace.services

import ru.yggdrasil.profitplace.models.Zone

interface PlacesService {
    fun getNearbyCafeCount(zone: Zone): Int
    fun getNearbyBusStopCount(zone: Zone): Int
}