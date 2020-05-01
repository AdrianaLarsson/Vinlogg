package com.example.vinlogg.ModelClassVine


import com.google.gson.annotations.SerializedName


open class DistrictsItem(

	@field:SerializedName("districtId")
	val districtId: Long? = null,

	@field:SerializedName("districtName")
	val districtName: String? = null,

	@field:SerializedName("regionId")
	val regionId: Long? = null
)


open class DistrictClass(

	@field:SerializedName("districtId")
	val districtId: Long? = null,

	@field:SerializedName("districtName")
	val districtName: String? = null,

	@field:SerializedName("regionId")
	val regionId: Long? = null



){



}