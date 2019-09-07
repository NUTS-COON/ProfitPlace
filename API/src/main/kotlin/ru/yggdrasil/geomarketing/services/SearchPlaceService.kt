package ru.yggdrasil.geomarketing.services

import ru.yggdrasil.geomarketing.models.FindRecommendedPlacesModel
import ru.yggdrasil.geomarketing.models.RecommendedPlace

interface SearchPlaceService {
    fun getRecommendedPlaces(model: FindRecommendedPlacesModel): List<RecommendedPlace>
}