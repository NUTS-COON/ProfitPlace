package ru.yggdrasil.profitplace.controllers

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import ru.yggdrasil.profitplace.models.FindRecommendedPlacesModel
import ru.yggdrasil.profitplace.models.RecommendedPlace
import ru.yggdrasil.profitplace.services.SearchPlaceService

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