package ru.yggdrasil.profitplace.services

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import ru.yggdrasil.profitplace.models.placesApi.PlacesResponse

interface PlacesApiService {

    @GET("explore?app_id=0pzn1YmNRQRKlOE9EQA8&app_code=r1xJQ4ZoSoRA9J1lyzq7Lw")
    fun exploreNearbyPlaces(@Query("in") area: String, @Query("cat") category: String, @Query("size") size: Int): Call<PlacesResponse?>

    @GET("search?app_id=0pzn1YmNRQRKlOE9EQA8&app_code=r1xJQ4ZoSoRA9J1lyzq7Lw")
    fun searchNearbyPlaces(@Query("in") area: String, @Query("q") query: String, @Query("size") size: Int): Call<PlacesResponse?>
}