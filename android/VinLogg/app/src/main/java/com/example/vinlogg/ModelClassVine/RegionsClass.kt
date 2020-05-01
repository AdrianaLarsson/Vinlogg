package com.example.vinlogg.ModelClassVine


import com.google.gson.annotations.SerializedName


data class RegionsItem(

	@field:SerializedName("regionId")
	val regionId: Long? = null,

	@field:SerializedName("regionName")
	val regionName: String? = null,

	@field:SerializedName("districts")
	val districts: List<DistrictsItem>? = null,

	@field:SerializedName("countryId")
	val countryId: Long? = null
)


open class RegionsClass(


	@field:SerializedName("regionId")
	val regionId: Long? = null,

	@field:SerializedName("regionName")
	val regionName: String? = null,

	@field:SerializedName("districts")
	val districts: List<DistrictClass>? = null,

	@field:SerializedName("countryId")
	val countryId: Long? = null


){



}