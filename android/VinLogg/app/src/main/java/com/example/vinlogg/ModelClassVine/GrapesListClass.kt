package com.example.vinlogg.ModelClassVine

import com.google.gson.annotations.SerializedName


data class GrapesListClass(

	@field:SerializedName("grapeId")
	val grapeId: Long? = null,

	@field:SerializedName("grapeName")
	val grapeName: String? = null,

	@field:SerializedName("percent")
	val percent: Int? = null
)