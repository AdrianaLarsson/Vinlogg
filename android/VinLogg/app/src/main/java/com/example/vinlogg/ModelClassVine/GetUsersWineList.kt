package com.example.vinlogg.ModelClassVine


import com.google.gson.annotations.SerializedName


open class GetUsersWineList(

	var wineListUser : List<GetUsersWineList>? = null,
	@field:SerializedName("alcohol")
	val alcohol: Double? = null,

	@field:SerializedName("vintages")
	val vintages: List<VintagesItem>? = null,

	@field:SerializedName("wineName")
	val wineName: String? = null,

	@field:SerializedName("producer")
	var producer: String? = null,
	@field:SerializedName("district")
	val district: District? = null,

	@field:SerializedName("country")
	val country: Country? = null,

	@field:SerializedName("region")
	val region: Region? = null,

	@field:SerializedName("wineGrapes")
	val wineGrapes: List<WineGrapesItem>? = null,

	@field:SerializedName("imageThumbnail")
	val imageThumbnail: Any? = null,

	@field:SerializedName("imageBig")
	val imageBig: Any? = null,

	@field:SerializedName("wineId")
	val wineId: Int? = null
)