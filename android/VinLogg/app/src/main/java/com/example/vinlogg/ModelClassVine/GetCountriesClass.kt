package com.example.vinlogg.ModelClassVine


import com.google.gson.annotations.SerializedName


data class GetCountriesClass(

	@field:SerializedName("regions")
	val regions: List<RegionsItem>? = null,

	@field:SerializedName("countryName")
	val countryName: String? = null,

	@field:SerializedName("countryId")
	val countryId: Long? = null
)

 open class GetCountriesRegionsDistrictClass(
	val countriesRegionsDistrictClass: List<GetCountriesRegionsDistrictClass>? = null,


	@field:SerializedName("regions")
	val regions: List<RegionsClass>? = null,

	@field:SerializedName("countryName")
	val countryName: String? = null,

	@field:SerializedName("countryId")
	val countryId: Long? = null


){


}
