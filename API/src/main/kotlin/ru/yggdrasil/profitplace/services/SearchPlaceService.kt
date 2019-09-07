package ru.yggdrasil.profitplace.services

import ru.yggdrasil.profitplace.models.FindRecommendedPlacesModel
import ru.yggdrasil.profitplace.models.RecommendedPlace

interface SearchPlaceService {
    fun getRecommendedPlaces(model: FindRecommendedPlacesModel): List<RecommendedPlace>
}