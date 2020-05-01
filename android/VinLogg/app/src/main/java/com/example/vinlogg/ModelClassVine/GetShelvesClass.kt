package com.example.vinlogg.ModelClassVine


import com.google.gson.annotations.SerializedName


data class GetShelvesClass(

	@field:SerializedName("shelfId")
	val shelfId: Long? = null,

	@field:SerializedName("name")
	val name: String? = null
)