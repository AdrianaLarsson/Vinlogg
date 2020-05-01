package com.example.vinlogg.ModelClassVine


import com.google.gson.annotations.SerializedName


data class GetVintagesClassList(

	@field:SerializedName("alcohol")
	val alcohol: Double? = null,

	@field:SerializedName("vintages")
	val vintages: List<VintagesItem>? = null,

	@field:SerializedName("wineName")
	val wineName: String? = null,

	@field:SerializedName("district")
	val district: District? = null,

	@field:SerializedName("wineGrapes")
	val wineGrapes: List<WineGrapesItem>? = null,

	@field:SerializedName("imageOriginal")
	val imageOriginal: String? = null,

	@field:SerializedName("imageThumbnail")
	val imageThumbnail: String? = null,

	@field:SerializedName("wineId")
	val wineId: Long? = null
)