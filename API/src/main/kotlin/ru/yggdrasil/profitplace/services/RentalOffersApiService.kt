package ru.yggdrasil.profitplace.services

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import ru.yggdrasil.profitplace.models.rentalApi.RentalOffersResponse

interface RentalOffersApiService {

    @GET("main/api?user=mburavczov@bk.ru&token=9aa47f71e765cef10278d1803ce957a8&withcoords=1")
    fun getRentalOffers(
            @Query("category_id") categoryId: Int,
            @Query("city") city: String,
            @Query("date1") dateFrom: String,
            @Query("price2") maxPrice: Int,
            @Query("nedvigimost_type") offerType: Int): Call<RentalOffersResponse?>
}