package com.example.vinlogg

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import kotlin.math.roundToInt
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.vinlogg.Adapters.DetailAdapter
import com.example.vinlogg.Adapters.DetailGrapesAdapter
import com.example.vinlogg.ModelClassVine.*
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.GsonBuilder
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail_vine.*
import kotlinx.android.synthetic.main.vintages_row_detial_items.*
import java.util.HashMap

class DetailVineActivity : AppCompatActivity() {

    var vintagesdetails: List<GetUsersWineList>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_vine)





        getEstras()
        getVintagesAndWineGrapesUserListDetail()
        plusBtn()
        minusBtn()
        back()

    }
    fun back() {

        var backBtn = findViewById<ImageView>(R.id.backBtnDetail)

        backBtn.setOnClickListener {

            Log.w("Back", "BTN Pressed ")
            onBackPressed()
        }
    }





    //get data from adapter ti activity, detail side
    fun getEstras() {

        var titleYear = findViewById<TextView>(R.id.txtVdetailYear)
        var titelAmount = findViewById<TextView>(R.id.txtVAmountDetail)
        var titleShelve = findViewById<TextView>(R.id.txtVDetailShelve)
        var titleHolding = findViewById<TextView>(R.id.txtVHoldingdetail)

        val intent = intent
        var vineName = intent.getStringExtra("VINE NAME")
        var wineId = intent.getStringExtra("VINE ID")
        var district = intent.getStringExtra("VINE DISTRICT")
        var alcohol = intent.getStringExtra("VINE ALCOHOL")

        if (alcohol == "-1"){


            alcoholDetail.text =  ""
        }else{

            alcoholDetail.text =  alcohol + "%"
        }

        var image = intent.getStringExtra("VINE IMAGE")
        var country = intent.getStringExtra("WINE COUNTRY")
        var region = intent.getStringExtra("WINE REGION")
        var producer = intent.getStringExtra("WINE PRODUCER")
        var grapes = intent.getStringExtra("WINE GRAPES")
        var shelves = intent.getStringExtra("WINE SHELVE")
        var year = intent.getStringExtra("WINE YEAR")
        var amount = intent.getStringExtra("WINE AMOUNT")

        var grapeProcent = intent.getStringExtra("WINE GRAPES PROCENT")
        var grapeArray = intent.getStringArrayExtra("WINE GRAPE ARRAY")

        Log.w("grapeArray", "grapeArray  " + grapeArray)
        Log.w("grapeProcent", "grapeProcent  " + grapeProcent)

        Log.w("regionregionregion", "region " + region)
        Log.w("countrycountrycountry", "country " + region)
        Log.w("IMAGE", "IMAGE  " + image)
        var txtViewCountry = findViewById<TextView>(R.id.detailListCountry)
        var txtViewRegion = findViewById<TextView>(R.id.detaillistRegion)
        txtViewCountry.text = country + " >> "
        txtViewRegion.text = region + " >> "
        myWineListProducent.text = producer
        detialsideAmount.text = amount
        detialSideshelveName.text = shelves
        detailsideYear.text = year


        Picasso.get().load(image).into(imagviewDetail)
        Log.w("WINEID", "===> " + wineId)
        Log.w("WINENAME", "===> " + vineName)
        namevinedetail.text = vineName
        districtDetail.text = district



        var procentOfG=  grapeProcent.replace("-1", "").replace("0", "")


        if (procentOfG.isNullOrBlank()){

            grapesDetails.text = grapes
        }else{
            grapesDetails.text = grapes + " " + procentOfG + "%"

        }



        if (amount.isNullOrEmpty() && year.isNullOrEmpty()){


            titelAmount.visibility = View.GONE
            titleShelve.visibility = View.GONE
            titleYear.visibility = View.GONE
            titleHolding.visibility = View.GONE
        }

    }


    fun getVintagesAndWineGrapesUserListDetail() {
        val intent = intent
        var wineId = intent.getStringExtra("VINE ID")

        var url = URL().BASEURL + URL().userswinelist + URL().wineIdString + wineId
        var queue = Volley.newRequestQueue(applicationContext)
        val searchRequestVine = object : StringRequest(
            Method.GET, url,
            com.android.volley.Response.Listener { response ->

                val resultResWines = response


                var gson = GsonBuilder().create()
                val wineListUser: List<GetUsersWineList> =
                    gson.fromJson(resultResWines, Array<GetUsersWineList>::class.java).toList()

                vintagesdetails = wineListUser

                //setUpRecyclerAdapterForVintages()
                //recyclerviwForGrapes()
            }, com.android.volley.Response.ErrorListener { error ->
                Log.w("Error", "Error Get Wines" + error.toString())

            }) {

            override fun getParams(): MutableMap<String, String> {
                val params: MutableMap<String, String> = HashMap()
                return params
            }

            override fun getHeaders(): MutableMap<String, String> {
                ClassStringToken().getTokenCustom()
                val headers = HashMap<String, String>()
                headers["Authorization"] = "Bearer $stringToken"
                Log.w("TokenIShereGetW", "TOKENID " + stringToken)
                return headers
            }


        }
        queue.add(searchRequestVine)


    }

    fun plusBtn(){
        var addBtnPlus = findViewById<FloatingActionButton>(R.id.plusBtnDetail)
        var shelves = intent.getStringExtra("WINE SHELVE")
        var year = intent.getStringExtra("WINE YEAR")
        var vineName = intent.getStringExtra("VINE NAME")
        var wineId = intent.getStringExtra("VINE ID")

        addBtnPlus.setOnClickListener {
            val intent = Intent(applicationContext, AddYearShelveActivity::class.java)
            intent.putExtra("WINE YEAR", year)
            intent.putExtra("WINE SHELVE",shelves)
            intent.putExtra("WINE ID", wineId)
            intent.putExtra("WINE NAME", vineName)
            startActivity(intent)
        }




    }

    fun minusBtn(){
        var vineName = intent.getStringExtra("VINE NAME")
        var wineId = intent.getStringExtra("VINE ID")
        var shelves = intent.getStringExtra("WINE SHELVE")
        var year = intent.getStringExtra("WINE YEAR")
        var amount = intent.getStringExtra("WINE AMOUNT")


        var deletBtnMinus = findViewById<FloatingActionButton>(R.id.minusBtnDetail)

        deletBtnMinus.setOnClickListener {


            val intent = Intent(applicationContext, DeleteActivity::class.java)


            intent.putExtra("WINE YEAR", year)
            intent.putExtra("WINE AMOUNT",amount)
            intent.putExtra("WINE SHELVE",shelves)
            intent.putExtra("WINE ID", wineId)
            intent.putExtra("WINE NAME", vineName)
            startActivity(intent)

        }





        Log.w("KOLLAAAAAAA", "vineName" + vineName +wineId + shelves + year + amount)

    }









/*
    fun setUpRecyclerAdapterForVintages() {


        var usersWineList = vintagesdetails

        if (usersWineList != null) {
            for (v in usersWineList) {


                var vintagesdetails = v.vintages


                var vintagesDetailRecyclerView =
                    findViewById(R.id.vintagesDetailsRecyclerview) as RecyclerView


                vintagesDetailRecyclerView.layoutManager = LinearLayoutManager(this)
                vintagesDetailRecyclerView.adapter =
                    vintagesdetails?.let { DetailAdapter(applicationContext, it) }


            }

        }
    }*/

/*
    fun recyclerviwForGrapes() {


        var usersWineList = vintagesdetails

        if (usersWineList != null) {
            for (g in usersWineList) {
                var grapesDetail = g.wineGrapes
                var grapeRecyclerView =
                    findViewById(R.id.recyclerViewGrapesDetail) as RecyclerView
                grapeRecyclerView.layoutManager = LinearLayoutManager(this)
                grapeRecyclerView.adapter =
                    grapesDetail?.let { DetailGrapesAdapter(applicationContext, it) }


            }
        }
    }*/
}