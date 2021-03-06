package ru.yggdrasil.profitplace.models.rentalApi

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonIgnoreType
import com.fasterxml.jackson.annotation.JsonProperty
import com.google.gson.internal.LinkedTreeMap
import kotlin.reflect.jvm.internal.impl.load.kotlin.JvmType

@JsonIgnoreProperties(ignoreUnknown = true)
data class Data(
        val address: String,
        val avitoid: String,
        val cat1: String,
        val cat1_id: Int,
        val cat2: String,
        val cat2_id: Int,
        val city: String,
        val city1: String,
        val contactname: String,
        val coords: Coords,
        val count_ads_same_phone: Int,
        val description: String,
        val id: Int,
        val images: List<Image>,
        val metro: String,
        val nedvigimost_type: String,
        val nedvigimost_type_id: String,
        val param_12868: Int,
        val param_12869: Int,
        val param_4867: String,
        val param_4869: String,
        val param_4887: String,
        val param_4920: Double,
        val param_4922: String,
        val params: LinkedTreeMap<String, String>,
        val person: String,
        val person_type: String,
        val person_type_id: Int,
        val phone: String,
        val phone_operator: String,
        val phone_protected: Int?,
        val phone_region: String,
        val price: Int,
        val region: String,
        val source: String,
        val source_id: Int,
        val time: String,
        val title: String,
        val url: String
)