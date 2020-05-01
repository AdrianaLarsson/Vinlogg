package com.example.vinlogg.ModelClassVine

import com.google.gson.annotations.SerializedName

data class Region(

    @field:SerializedName("regionId")
    val regionId: Long? = null,

    @field:SerializedName("regionName")
    val regionName: String? = null,

    @field:SerializedName("countryId")
    val countryId: Long? = null
)