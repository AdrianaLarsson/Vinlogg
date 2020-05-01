package com.example.vinlogg.ModelClassVine

import com.google.gson.annotations.SerializedName


data class Wine(

	@field:SerializedName("alcohol")
	val alcohol: Double? = null,

	@field:SerializedName("district")
	val district: District? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("wineGrapes")
	val wineGrapes: List<WineGrapesItem?>? = null,

	@field:SerializedName("imageOriginal")
	val imageOriginal: Any? = null,

	@field:SerializedName("imageThumbnail")
	val imageThumbnail: Any? = null,

	@field:SerializedName("wineId")
	val wineId: Int? = null
)