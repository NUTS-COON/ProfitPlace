package ru.yggdrasil.profitplace.models

import com.google.gson.annotations.SerializedName

enum class PlaceType {
    @SerializedName("0")
    Cafe,
    @SerializedName("1")
    GroceryStore
}