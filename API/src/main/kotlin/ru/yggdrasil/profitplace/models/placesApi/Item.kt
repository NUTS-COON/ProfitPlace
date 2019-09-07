package ru.yggdrasil.profitplace.models.placesApi

data class Item(
        val averageRating: Double,
        val category: Category,
        val distance: Int,
        val having: List<Any>,
        val href: String,
        val icon: String,
        val id: String,
        val position: List<Double>,
        val title: String,
        val type: String,
        val vicinity: String
)