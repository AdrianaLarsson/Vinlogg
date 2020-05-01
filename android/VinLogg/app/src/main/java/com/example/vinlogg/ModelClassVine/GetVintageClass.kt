package com.example.vinlogg.ModelClassVine


import com.google.gson.annotations.SerializedName


data class GetVintageClass(

	@field:SerializedName("ean")
	val ean: String? = null,

	@field:SerializedName("year")
	val year: Int? = null,

	@field:SerializedName("vintageId")
	val vintageId: Long? = null,

	@field:SerializedName("wine")
	val wine: Wine? = null,

	@field:SerializedName("wineId")
	val wineId: Int? = null
)