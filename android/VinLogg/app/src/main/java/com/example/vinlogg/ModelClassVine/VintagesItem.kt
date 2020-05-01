package com.example.vinlogg.ModelClassVine


import com.google.gson.annotations.SerializedName


data class VintagesItem(

	@field:SerializedName("amount")
	val amount: Int? = null,

	@field:SerializedName("ean")
	val ean: String? = null,

	@field:SerializedName("shelfId")
	val shelfId: Long? = null,

	@field:SerializedName("year")
	val year: Int? = null,

	@field:SerializedName("grade")
	val grade: Any? = null,

	@field:SerializedName("inventoryId")
	val inventoryId: Long? = null,

	@field:SerializedName("vintageId")
	val vintageId: Long? = null,

	@field:SerializedName("shelfName")
	val shelfName: String? = null,

	@field:SerializedName("wineId")
	val wineId: Long? = null
)