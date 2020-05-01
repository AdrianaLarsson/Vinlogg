package com.example.vinlogg.ModelClassVine



import com.example.vinlogg.R

data class VineClass (


    //  var id : Int = 0,
    var name : String = "",
    var country : String = "",
    var district : String = "",
    var year : String = "",
    var amount : String = "",
    var history : String = "",
    var alcohol : String = "",
    var shelf : String = "",
    var image : Int = 0,
    var grapes : String = ""





)



object Vine {

    val vines = listOf<VineClass>(

        VineClass(
            // 1,
            "Beringer, White Zinfandel",
            "USA, California",
            "Napa Valley",
            "1779,1793",
            "2",
            "Wine is an alcoholic drink typically made from fermented grapes. ",
            "12%",
            "6B",
            R.drawable.vineeee, "Merlot Carbernet"),

        VineClass(
            //2,
            "Lambrusco",
            "Italien",
            "Calabria",
            "1779, 1990,1793",
            "3", "Yeast consumes the sugar in the grapes and converts it to ethanol, carbon dioxide, and heat.",
            "15%", "1A", R.drawable.wine3, "Petit Verdot"),

        VineClass(
            // 3,
            "Tronquuy-Lalande",
            "Frankrike",
            "Abruzzo",
            "1998,1853,2000,1786",
            "4",
            "Different varieties of grapes and strains of yeasts produce differe",
            "11%",
            "3C",
            R.drawable.vine4, "Carbernet Franc")


    )

}