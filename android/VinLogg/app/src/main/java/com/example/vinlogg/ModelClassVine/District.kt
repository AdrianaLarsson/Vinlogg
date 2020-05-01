package com.example.vinlogg.ModelClassVine

import com.google.gson.annotations.SerializedName


data class District(

	@field:SerializedName("districtId")
	val districtId: Long? = null,

	@field:SerializedName("districtName")
	val districtName: String? = null,

	@field:SerializedName("regionId")
	val regionId: Long? = null
)