package com.example.vinlogg.ModelClassVine



data class WinePostClass(

    var name: String? = null,
    var image: String? = null,
    var districtId: Long? = null,
    var alcohol: Double? = null,
    var producer : String? = null,
    var WineGrapes: List<WineGrapesPostClass>



){

}