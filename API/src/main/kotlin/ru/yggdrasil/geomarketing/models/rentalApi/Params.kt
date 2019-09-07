package ru.yggdrasil.geomarketing.models.rentalApi

import com.google.gson.annotations.SerializedName

data class Params(
        @SerializedName("Вид объекта") val objectType: String,
        @SerializedName("Площадь") val area: Double,
        @SerializedName("Тип объявления") val offerType: String
)