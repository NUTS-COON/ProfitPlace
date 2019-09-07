package ru.yggdrasil.geomarketing.services

import ru.yggdrasil.geomarketing.models.Zone

interface PlacesService {
    fun getNearbyCafeCount(zone: Zone): Int
    fun getNearbyBusStopCount(zone: Zone): Int
}