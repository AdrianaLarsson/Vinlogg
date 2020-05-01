package com.example.vinlogg.ModelClassVine

import com.google.gson.annotations.SerializedName

open class Country (
    @field:SerializedName("countryId")
    val countryId: Long? = null,

    @field:SerializedName("countryName")
    val countryName: String? = null


)