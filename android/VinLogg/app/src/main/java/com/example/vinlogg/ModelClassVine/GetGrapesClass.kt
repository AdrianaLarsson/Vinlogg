package com.example.vinlogg.ModelClassVine


import com.google.gson.annotations.SerializedName



open class GetGrapes(
	val grapesList: List<GetGrapes>? = null,
	@field:SerializedName("grapeId")
	val grapeId: Long? = null,

	@field:SerializedName("grapeName")
	val grapeName: String? = null



)

open class GetGrapesClass(

	val grapesList: List<GetGrapesClass>? = null,
	@field:SerializedName("grapeId")
	val grapeId: Long? = null,

	@field:SerializedName("grapeName")
	val grapeName: String? = null
){

}





