package com.example.vinlogg.ModelClassVine


import com.google.gson.annotations.SerializedName


data class Test(

	@field:SerializedName("datum")
	val datum: String? = null,

	@field:SerializedName("booking")
	val booking: List<Any?>? = null,

	@field:SerializedName("other")
	val other: List<String?>? = null,

	@field:SerializedName("nexttpl")
	val nexttpl: String? = null,

	@field:SerializedName("curtpl")
	val curtpl: String? = null,

	@field:SerializedName("deviation")
	val deviation: List<Any?>? = null,

	@field:SerializedName("tagNr")
	val tagNr: Int? = null,

	@field:SerializedName("tid")
	val tid: String? = null,

	@field:SerializedName("speed")
	val speed: Any? = null,

	@field:SerializedName("operator")
	val operator: String? = null,

	@field:SerializedName("koncept")
	val koncept: String? = null,

	@field:SerializedName("delay")
	val delay: Int? = null,

	@field:SerializedName("nexttime")
	val nexttime: String? = null,

	@field:SerializedName("composition")
	val composition: List<Any?>? = null,

	@field:SerializedName("sysm")
	val sysm: String? = null,

	@field:SerializedName("service")
	val service: List<Any?>? = null,

	@field:SerializedName("station")
	val station: String? = null,

	@field:SerializedName("timeAtLocation")
	val timeAtLocation: String? = null,

	@field:SerializedName("stops")
	val stops: Any? = null,

	@field:SerializedName("sp")
	val sp: String? = null,

	@field:SerializedName("status")
	val status: Int? = null,

	@field:SerializedName("bertid")
	val bertid: Any? = null
)