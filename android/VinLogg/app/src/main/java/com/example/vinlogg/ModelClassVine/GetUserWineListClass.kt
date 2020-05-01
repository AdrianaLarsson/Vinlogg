package com.example.vinlogg.ModelClassVine


import com.google.gson.annotations.SerializedName




data class GetUserWineListClass(

    @field:SerializedName("alcohol")
	val alcohol: Double? = null,

    @field:SerializedName("imageBig")
	val imageBig: String? = null,
    @field:SerializedName("imageThumbnail")
	val imageThumbnail: String? = null,

    @field:SerializedName("vintages")
	val vintages: List<GetVintagesClassList>? = null,

    @field:SerializedName("wineName")
	val wineName: String? = null,

    @field:SerializedName("district")
	val district: District? = null,

    @field:SerializedName("wineGrapes")
	val wineGrapeClasses: List<WineGrapesListClass?>? = null,

    @field:SerializedName("wineId")
	val wineId: Long? = null
)




open class GetWineListWithUser(

    val wineList: List<GetWineListWithUser>? = null,
    @field:SerializedName("alcohol")
	val alcohol: Double? = null,

    @field:SerializedName("imageOriginal")
	val imageOriginal: String? = null,
    @field:SerializedName("imageThumbnail")
	val imageThumbnail: String? = null,

    @field:SerializedName("vintages")
	val vintages: List<GetVintagesClassList>? = null,

    @field:SerializedName("wineName")
	val wineName: String? = null,

    @field:SerializedName("district")
	val district: District? = null,

    @field:SerializedName("wineGrapes")
	val wineGrapeClasses: List<WineGrapesListClass>? = null,

    @field:SerializedName("wineId")
	val wineId: Long? = null




){



}