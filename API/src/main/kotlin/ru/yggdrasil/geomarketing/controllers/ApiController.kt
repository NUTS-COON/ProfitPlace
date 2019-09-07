package ru.yggdrasil.geomarketing.controllers

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import ru.yggdrasil.geomarketing.models.FindRecommendedPlacesModel
import ru.yggdrasil.geomarketing.models.RecommendedPlace
import ru.yggdrasil.geomarketing.services.SearchPlaceService

@CrossOrigin(origins = arrayOf("*"), allowedHeaders = arrayOf("*"))
@RestController("api")
class ApiController {

    @Autowired
    lateinit var searchPlaceService: SearchPlaceService

    @PostMapping("findRecommendedPlaces")
    fun findRecommendedPlaces(@RequestBody model: FindRecommendedPlacesModel): List<RecommendedPlace>{
        return searchPlaceService.getRecommendedPlaces(model)
    }
}