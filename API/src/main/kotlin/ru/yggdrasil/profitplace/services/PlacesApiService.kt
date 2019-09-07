package ru.yggdrasil.profitplace.services

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import ru.yggdrasil.profitplace.models.placesApi.PlacesResponse

interface PlacesApiService {

    @GET("around?app_id=0pzn1YmNRQRKlOE9EQA8&app_code=r1xJQ4ZoSoRA9J1lyzq7Lw")
    fun getNearbyPlaces(@Query("in") area: String, @Query("cat") category: String): Call<PlacesResponse>
}