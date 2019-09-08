package ru.yggdrasil.profitplace.services

import ru.yggdrasil.profitplace.models.Coordinates
import ru.yggdrasil.profitplace.models.ThermalPoint

interface DetailAnalyzeService {
    fun getThermalMapping(coordinates: Coordinates): List<ThermalPoint>
}