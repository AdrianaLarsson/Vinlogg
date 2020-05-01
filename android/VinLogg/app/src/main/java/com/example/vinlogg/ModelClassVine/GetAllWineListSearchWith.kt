package com.example.vinlogg.ModelClassVine


import android.graphics.Region
import com.google.gson.annotations.SerializedName


open class GetAllWineListSearchWith(

	val getAllWineListSearchWith: List<GetAllWineListSearchWith>? = null,
	@field:SerializedName("alcohol")
	val alcohol: Double? = null,

	@field:SerializedName("vintages")
	val vintages: List<VintagesItem?>? = null,

	@field:SerializedName("wineName")
	val wineName: String? = null,

	@field:SerializedName("producer")
	val producer: String? = null,

	@field:SerializedName("district")
	val district: District? = null,

	@field:SerializedName("region")
	val region: com.example.vinlogg.ModelClassVine.Region? = null,

	@field:SerializedName("country")
	val country: Country? = null,

	@field:SerializedName("wineGrapes")
	val wineGrapes: List<WineGrapesItem?>? = null,

	@field:SerializedName("imageBig")
	val imageBig: Any? = null,

	@field:SerializedName("imageThumbnail")
	val imageThumbnail: Any? = null,

	@field:SerializedName("wineId")
	val wineId: Long? = null
)