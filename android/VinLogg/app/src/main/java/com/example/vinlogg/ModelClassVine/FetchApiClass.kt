package com.example.vinlogg.ModelClassVine

import android.util.Log
import android.view.View
import android.widget.Toast
import com.android.volley.RequestQueue

import com.android.volley.toolbox.StringRequest
import com.example.vinlogg.*
import com.example.vinlogg.Fragments.*
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.*
import java.io.IOException
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import java.util.HashMap

interface FetchingApi {

    fun GetCountriesRegionsDistrict(url: String, queue: RequestQueue, view: View) {

        val searchRequestVine = object : StringRequest(Method.GET, url,
                com.android.volley.Response.Listener { response ->

                    val resultResWines = response
                    //val resultResCountries = response.toString()

                    var gson = GsonBuilder().create()
                    val listCouRegiDistrict: List<GetCountriesRegionsDistrictClass> = gson.fromJson(resultResWines, Array<GetCountriesRegionsDistrictClass>::class.java).toList()



                    Log.w("getGrapes", " grapes " + listCouRegiDistrict)


                    //   MyWineListFragment.showCouRegionDist(listCouRegiDistrict,view)
                    SearchWineFragment.CountriesRegionsDistrict(listCouRegiDistrict, view)


                }, com.android.volley.Response.ErrorListener { error ->
            Log.w("Error", "Error Get grapes" + error.toString())

        }) {


            override fun getParams(): MutableMap<String, String> {
                val params: MutableMap<String, String> = HashMap()

                return params
            }


            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()


                ClassStringToken().getTokenCustom()
                headers["Authorization"] = "Bearer $stringToken"
                Log.w("TokenIShereCont1", "TOKENID" + stringToken)
                return headers
            }
        }
        queue.add(searchRequestVine)
    }


    fun getCounRegionDist(url: String, queue: RequestQueue, view: View) {


        val searchRequestVine = object : StringRequest(Method.GET, url,
                com.android.volley.Response.Listener { response ->

                    val resultResWines = response
                    //val resultResCountries = response.toString()

                    var gson = GsonBuilder().create()
                    val listCouRegiDistrict: List<GetCountriesRegionsDistrictClass> = gson.fromJson(resultResWines, Array<GetCountriesRegionsDistrictClass>::class.java).toList()



                    Log.w("getGrapes", " grapes " + listCouRegiDistrict)


                    MyWineListFragment.showCouRegionDist(listCouRegiDistrict, view)


                }, com.android.volley.Response.ErrorListener { error ->
            Log.w("Error", "Error Get grapes" + error.toString())

        }) {


            override fun getParams(): MutableMap<String, String> {
                val params: MutableMap<String, String> = HashMap()

                return params
            }


            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()


                ClassStringToken().getTokenCustom()
                headers["Authorization"] = "Bearer $stringToken"
                Log.w("TokenIShereCount", "TOKENID" + stringToken)
                return headers
            }
        }
        queue.add(searchRequestVine)
    }


    fun getGrapes(url: String, queue: RequestQueue, view: View) {


        val searchRequestVine = object : StringRequest(Method.GET, url,
                com.android.volley.Response.Listener { response ->

                    val resultResWines = response
                    //val resultResCountries = response.toString()

                    var gson = GsonBuilder().create()
                    val grapesList: List<GetGrapes> = gson.fromJson(resultResWines, Array<GetGrapes>::class.java).toList()


                    Log.w("getGrapes", " grapes " + grapesList)

                    SearchWineFragment.Grapes(grapesList, view)


                }, com.android.volley.Response.ErrorListener { error ->
            Log.w("Error", "Error Get grapes" + error.toString())

        }) {


            override fun getParams(): MutableMap<String, String> {
                val params: MutableMap<String, String> = HashMap()
                //   params["startsWith"] = "wine"
                return params

            }

            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                ClassStringToken().getTokenCustom()
                headers["Authorization"] = "Bearer $stringToken"
                Log.w("TokenIShereGr", "TOKENID" + stringToken)
                return headers
            }

        }
        queue.add(searchRequestVine)

    }


    fun getGrapesToWinelist(url: String, queue: RequestQueue, view: View) {

        val searchRequestVine = object : StringRequest(Method.GET, url,
                com.android.volley.Response.Listener { response ->

                    val resultResWines = response
                    //val resultResCountries = response.toString()

                    var gson = GsonBuilder().create()
                    val grapesList: List<GetGrapes> = gson.fromJson(resultResWines, Array<GetGrapes>::class.java).toList()


                    Log.w("getGrapes", " grapes " + grapesList)

                    MyWineListFragment.Grapes(grapesList, view)


                }, com.android.volley.Response.ErrorListener { error ->
            Log.w("Error", "Error Get grapes" + error.toString())

        }) {


            override fun getParams(): MutableMap<String, String> {
                val params: MutableMap<String, String> = HashMap()
                //   params["startsWith"] = "wine"
                return params

            }

            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                ClassStringToken().getTokenCustom()
                headers["Authorization"] = "Bearer $stringToken"
                Log.w("TokenIShereGraTowinel", "TOKENID" + stringToken)
                return headers
            }

        }
        queue.add(searchRequestVine)

    }


    fun getWines(url: String, queue: RequestQueue, view: View) {

        Log.w("llllllllll", " lcountriesRegionsDistrictList " + url)


        val searchRequestVine = object : StringRequest(Method.GET, url,
                com.android.volley.Response.Listener { response ->

                    val resultResWines = response


                    var gson = GsonBuilder().create()
                    val wineListUser: List<GetUsersWineList> = gson.fromJson(resultResWines, Array<GetUsersWineList>::class.java).toList()
                    val getAllWineListSearchWith: List<GetAllWineListSearchWith> = gson.fromJson(resultResWines, Array<GetAllWineListSearchWith>::class.java).toList()
                    Log.w("wwwwwwwwwwwww", " list  " + getAllWineListSearchWith)

                    MyWineListFragment.showWineList(wineListUser, view)
                    SearchWineFragment.showWineList(getAllWineListSearchWith, view)

                    Log.w("countriesregDist", " lcountriesRegionsDistrictList " + wineListUser)


                }, com.android.volley.Response.ErrorListener { error ->
            Log.w("Error", "Error Get Wines" + error.toString())

        }) {

            override fun getParams(): MutableMap<String, String> {
                val params: MutableMap<String, String> = HashMap()


                //   params["startsWith"] = "wine"
                return params
            }

            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                ClassStringToken().getTokenCustom()
                headers["Authorization"] = "Bearer $stringToken"
                Log.w("TokenIShereGetW", "TOKENID" + stringToken)
                return headers
            }


        }
        queue.add(searchRequestVine)


    }


}


class FetchApi() {


    //post shelves
    fun postShelves() {


        Log.w("shelvenamepost", "postNewShelve " + postNewShelve)
        var shelvesClassPost = ShelvesClassPost(postNewShelve)


        var gson = Gson()
        var jsonstring = gson.toJson(shelvesClassPost)
        val mediaType = "application/json; charset=utf-8".toMediaType()
        val requestBody = jsonstring.toString().toRequestBody(mediaType)


        ClassStringToken().getTokenCustom()
        Log.w("POSTShelves", "Shelves token " + stringToken)
        val request = Request.Builder()
                .post(requestBody)
                .url(URL().BASEURL + URL().shelvesUrl)
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer $stringToken")
                .build()
        val client = OkHttpClient()

        client.newCall(request).enqueue(object : Callback {

            override fun onResponse(call: Call, response: Response) {
                Log.w("Success!!", "POST " + response.body?.string())
            }


            override fun onFailure(call: Call, e: IOException) {
                Log.w(
                        "ERROR",
                        "Failed POST " + call.hashCode() + e.message + e.localizedMessage + e.cause
                )
            }


        })


    }


    //post vintage


    fun postvVintage() {

        Log.w("POST", "VINTAGE => " + postNewVintage)
        Log.w("POST", "Wine Id => " + postWineId)

        var vintagePostClass = VintagePostClass(postNewVintage, postWineId)
        var gson = Gson()
        var jsonstring = gson.toJson(vintagePostClass)
        val mediaType = "application/json; charset=utf-8".toMediaType()
        val requestBody = jsonstring.toString().toRequestBody(mediaType)


        ClassStringToken().getTokenCustom()
        val request = Request.Builder()
                .post(requestBody)
                .url(URL().BASEURL + "vintages")
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer $stringToken")
                .build()
        val client = OkHttpClient()

        client.newCall(request).enqueue(object : Callback {

            override fun onResponse(call: Call, response: Response) {
                Log.w("Success!!", "POST " + response.body?.string())
            }


            override fun onFailure(call: Call, e: IOException) {
                Log.w(
                        "ERROR",
                        "Failed POST " + call.hashCode() + e.message + e.localizedMessage + e.cause
                )
            }

        })


    }


    //post inventory

    fun postInventory() {

        Log.w("POST", "=== >> Inventorrryyyy")
        Log.w("POST", "=== >> Amonut" + postAmount)
        Log.w("POST", "=== >> Vintage " + postIdVintage)
        Log.w("POST", "=== >> shelve " + postIdShelve)

        AddYearShelveActivity().spinnerSelectShelve()
        AddYearShelveActivity().spinnerSelectYear()

        var inventoryPostClass =
                InventoryPostClass(postAmount?.toInt(), postIdVintage, postIdShelve)
        var gson = Gson()
        var jsonstring = gson.toJson(inventoryPostClass)
        val mediaType = "application/json; charset=utf-8".toMediaType()
        val requestBody = jsonstring.toString().toRequestBody(mediaType)


        ClassStringToken().getTokenCustom()
        Log.w("TokenISherePostIvent", "Iventory " + stringToken)
        val request = Request.Builder()
                .post(requestBody)
                .url("http://54.72.51.80:5000/api/inventories?")
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer $stringToken")
                .build()
        val client = OkHttpClient()

        client.newCall(request).enqueue(object : Callback {

            override fun onResponse(call: Call, response: Response) {
                Log.w("Success!!", "POST requestBody Inventory =>>" + requestBody)
                Log.w("Success!!", "POST Inventory =>>" + response.body?.string())
            }

            override fun onFailure(call: Call, e: IOException) {
                Log.w(
                        "ERROR",
                        "Failed POST " + "Inventory  =>>" + call.hashCode() + e.message + e.localizedMessage + e.cause
                )
            }

        })

    }


    fun postNewUser() {


        //Log.w("POSTUSER", "=== >> FirstName" + postFirstName)
        //  Log.w("POSTUSER", "=== >> LastName" + postLastName)
        Log.w("POSTUSER", "=== >> UserName " + postUserName)
        //   Log.w("POSTUSER", "=== >> Email " + postEmail)
        // Log.w("POSTUSER", "=== >> Password " + postPassword)


        var postNewUserClass =
                PostNewUserClass(postUserName)
        var gson = Gson()
        var jsonstring = gson.toJson(postNewUserClass)
        val mediaType = "application/json; charset=utf-8".toMediaType()
        val requestBody = jsonstring.toString().toRequestBody(mediaType)


        ClassStringToken().getTokenCustom()
        val request = Request.Builder()
                .post(requestBody)
                .url("http://54.72.51.80:5000/api/users/register")
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer $stringToken")
                .build()
        val client = OkHttpClient()

        client.newCall(request).enqueue(object : Callback {

            override fun onResponse(call: Call, response: Response) {
                Log.w("Success!!", "POST requestBody New user =>>" + requestBody)
                Log.w("Success!!", "POST New user =>>" + response.body?.string())
            }

            override fun onFailure(call: Call, e: IOException) {
                Log.w(
                        "ERROR",
                        "Failed POST " + "User  =>>" + call.hashCode() + e.message + e.localizedMessage + e.cause
                )
            }

        })

    }


}


class URL() {

    var allWinelistFullUrl: String = "allwinelist?startswith=&countryid=-1&regionid=-1&districtid=-1&grapeid=-1"
    var userswinelistFullUrl: String = "userswinelist?startswith=&countryid=-1&regionid=-1&districtid=-1&grapeid=-1"
    var allwineList: String = "allwinelist?"
    var userswinelist: String = "userswinelist?"

    var wineIdString = "&wineId="
    var userId: String = "?userid=Av4ivM6LMlRVz7W5YbgilBXUK5d2"
    var BASEURL: String = "http://54.72.51.80:5000/api/"

    var metadata: String = "metadata"
    var grapesUrl: String = "/grapes"
    var shelvesUrl: String = "shelves"
    var inventages: String = "vintages"
    var countriesRegionDistrict: String = "/countries"
    var startsWith = "startswith="
    var countryid = "&countryid="
    var regionId = "&regionid="
    var districtId = "&districtid="
    var grapeId = "&grapeid="


}











