package com.example.vinlogg.ModelClassVine

import com.google.gson.annotations.SerializedName


data class WineResponsePost(

    @field:SerializedName("alcohol")
	val alcohol: Double? = null,

    @field:SerializedName("image")
	val image: String? = null,

    @field:SerializedName("district")
	val district: District? = null,

    @field:SerializedName("name")
	val name: String? = null,

    @field:SerializedName("wineGrapes")
	val wineGrapeClasses: List<WineGrapesListClass?>? = null,

    @field:SerializedName("wineId")
	val wineId: Long? = null
)