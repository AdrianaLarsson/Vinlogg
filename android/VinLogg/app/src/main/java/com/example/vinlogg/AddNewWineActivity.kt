package com.example.vinlogg

import android.Manifest
import android.app.Activity
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.util.Base64
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.content.FileProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.toolbox.StringRequest
import com.example.vinlogg.Adapters.GrapeAdapter
import com.example.vinlogg.Adapters.onGrapeItemClickListener
import com.example.vinlogg.Adapters.procentFromU
import com.example.vinlogg.Adapters.result
import com.example.vinlogg.ModelClassVine.*
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_addnew_wine.*
import kotlinx.android.synthetic.main.pop_up_grapes.*
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONException
import org.json.JSONObject
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException
import java.lang.Exception
import java.util.*

import kotlin.collections.ArrayList


var postWineProcent: Double? = null
var postIdGrape: Long? = 1
var postGrapeProcent: Int? = null
var postDistrictId: Long? = null
var postWineName: String? = null
var postImage: String? = null
var postWineProducer: String? = null
var postImageCamera: String? = null
var postImageGallery: String? = null

class AddNewWineActivity : AppCompatActivity(), onGrapeItemClickListener {
    var regionsArray: List<RegionsItem>? = null
    var districtsArray: List<DistrictsItem>? = null
    var countriesGlobal: List<GetCountriesClass>? = null
    var optionUserSelectionR: String? = null
    var optionUserSelectionC: String? = null
    var countries = ArrayList<String>()
    var regions = ArrayList<RegionsItem>()
    var districts = ArrayList<String>()
    var districtsId = ArrayList<Long>()
    var getCountriesGlobal: List<GetCountriesClass>? = null
    var userOptionDistrictId: Long? = null
    var getGrapesGlobal: List<GetGrapes>? = null
    var postImage: String? = null
    val REQUEST_IMAGE_CAPTURE = 123
    val REQUEST_TAKE_PHOTO = 1
    lateinit var currentPhotoPath: String

    var wineResponsePostONE: Long? = null
    var wineResponsePostTWO: Long? = null
    var nameDistrict: String? = null
    var nameCountry: String? = null
    var nameRegion: String? = null
    var choosenGrapeId: Long = 0
    var precentGrapeForchoosenGrape: Int = 0

    var districtsItem: List<DistrictsItem>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_addnew_wine)


        newNameProcentWineEditText()
        getVolleyCountriesRegionsDistrict()
        getGrapesVolley()
        alertDialog()

        postButtonNewWineName()
        goBack()


    }


    fun alertDialog() {


        var captureImageBtn = findViewById<ImageButton>(R.id.captureImageBtn)
        captureImageBtn.setOnClickListener {

            //choices in array, upload picture and take a picture from camera
            val listItems =
                    arrayOf("Ladda upp en bild från galleriet", "Ta en bild från din camera")

            //alerrt dilog if statment choose
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Väl att ladda upp en bild från galleriet eller ta en bild från din camera")
            builder.setSingleChoiceItems(listItems, -1) { dialog, i: Int ->

                var choose = listItems[i]



                if (choose == "Ladda upp en bild från galleriet") {
                    Toast.makeText(
                            this,
                            "Du valde : " + "Ladda upp en bild från galleriet",
                            Toast.LENGTH_SHORT


                    ).show()

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) ==
                                PackageManager.PERMISSION_DENIED
                        ) {
                            //permission denied
                            val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE);
                            //show popup to request runtime permission
                            requestPermissions(permissions, PERMISSION_CODE);
                        } else {
                            //permission already granted
                            pickImageFromGallery();
                        }
                    } else {
                        //system OS is < Marshmallow
                        pickImageFromGallery();
                    }


                } else if (choose == "Ta en bild från din camera") {


                    Toast.makeText(
                            this,
                            "Du valde : " + "Ta en bild från din camera",
                            Toast.LENGTH_SHORT
                    )
                            .show()

                    dispatchTakePictureIntent()
                    //dispatchTakePictureIntent()

                }


                dialog.dismiss()
            }

            //cancel button

            builder.setNeutralButton("Avbryt") { dialog: DialogInterface?, _: Int ->

                dialog?.cancel()

            }


            val dialogM = builder.create()
            dialogM.show()
        }


    }


    private fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(packageManager)?.also {
                val photoFile: File? = try {
                    createImageFile()
                } catch (ex: IOException) {

                    Log.w("ex: IOException", "Error :" + ex.cause + ex.message)

                    null
                }
                photoFile?.also {
                    val photoURI: Uri = FileProvider.getUriForFile(
                            this,
                            "com.example.android.fileprovider",
                            it

                    )


                }
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)

            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE) {

            var bmp = data?.extras?.get("data") as Bitmap

            captureImageBtn.setImageBitmap(bmp)

            Log.w("BMP", "BITMAP ==> " + bmp)


            var byteArrayOutputStream = ByteArrayOutputStream()
            bmp.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
            var byteArray = byteArrayOutputStream.toByteArray()
            var encodedFromCamera = Base64.encodeToString(byteArray, Base64.DEFAULT)
            Log.w("BMP", "encodeToString ==> " + encodedFromCamera)

            postImageCamera = encodedFromCamera
            getImageUrlGalleryAndCamera()

        } else if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE) {

            var uri = data?.data
            captureImageBtn.setImageURI(uri)

            var bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri)

            var byteArrayOutputStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
            var byteArray = byteArrayOutputStream.toByteArray()
            var fromGallery = Base64.encodeToString(byteArray, Base64.DEFAULT)


            Log.w("BMP", "encodeToString FROM GALLERY ==> " + fromGallery)
            postImageGallery = fromGallery
            getImageUrlGalleryAndCamera()

        }
    }

    @Throws(IOException::class)
    private fun createImageFile(): File {
        // Create an image file name
        val timeStamp: String = java.text.SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
                "JPEG_${timeStamp}_",
                ".jpg",
                storageDir


        ).apply {
            currentPhotoPath = absolutePath

            Log.w("absolutePath", "absolutePath => " + absolutePath)
            Log.w("absoluteFile", "absoluteFile => " + absoluteFile)
            Log.w("currentPhotoPath", "currentPhotoPath => " + currentPhotoPath)

            Log.w("storageDir", "storageDir => " + storageDir)
            Log.w("timeStamp", "timeStamp => " + timeStamp)
        }


    }


    private fun pickImageFromGallery() {
        //Intent to pick image
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }


    companion object {
        //image pick code
        private val IMAGE_PICK_CODE = 1000;

        //Permission code
        private val PERMISSION_CODE = 1001;
    }


    override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<out String>,
            grantResults: IntArray
    ) {
        when (requestCode) {
            PERMISSION_CODE -> {
                if (grantResults.size > 0 && grantResults[0] ==
                        PackageManager.PERMISSION_GRANTED
                ) {
                    //permission from popup granted
                    pickImageFromGallery()
                } else {
                    //permission from popup denied
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


    fun getImageUrlGalleryAndCamera() {


        Log.w("GETIMAGEURLFCG", "encodeToString FROM GALLERY ==> " + postImageGallery)

        Log.w("BMP", "encodeToString FROM CAMERA ==> " + postImageCamera)

    }


    fun newNameProcentWineEditText() {
        var editTextNewWine = findViewById<EditText>(R.id.AutoCpTxVaddVineNameNew)
        var editProducer = findViewById<EditText>(R.id.editTextProcucer)
        var newWineProcentEditText = findViewById<EditText>(R.id.editTextProcentWineNew)

        editTextNewWine.requestFocus()


        //new wine name
        editTextNewWine.addTextChangedListener(object : TextWatcher {


            override fun afterTextChanged(s: Editable?) {


                postWineName = editTextNewWine.text.toString()


            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {


            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })


        newWineProcentEditText.addTextChangedListener(object : TextWatcher {

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun afterTextChanged(s: Editable?) {


                try {

                    postWineProcent = newWineProcentEditText.text.toString().toDouble()
                } catch (e: Exception) {


                }


            }
        })




        editProducer.addTextChangedListener(object : TextWatcher {

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {

                postWineProducer = editProducer.text.toString()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }
        })


    }


    fun getVolleyCountriesRegionsDistrict() {


        val queue = com.android.volley.toolbox.Volley.newRequestQueue(applicationContext)

        val url = URL().BASEURL + URL().metadata + URL().countriesRegionDistrict
        val crdRequest = object : StringRequest(
                Method.GET, url,
                com.android.volley.Response.Listener { response ->

                    val result = response.toString()


                    var gson = GsonBuilder().create()

                    val countriesList: List<GetCountriesClass> =
                            gson.fromJson(result, Array<GetCountriesClass>::class.java).toList()


                    getCountriesGlobal = countriesList

                    getCountriesRegionsDistrictVolley()

                    Log.w("GETCOUNTRIES", " COUNTRIESLIST  ======>>>>>> " + countriesList)


                }, com.android.volley.Response.ErrorListener { error ->
            Log.w(
                    "Error",
                    "Error Get COUNTRIES " + error.toString() + error.printStackTrace() + error.cause + error.cause
            )

        }) {
            override fun getBody(): ByteArray {
                return super.getBody()
            }

            override fun getBodyContentType(): String {
                return super.getBodyContentType()
            }

            override fun getParams(): MutableMap<String, String> {
                val params: MutableMap<String, String> = HashMap()

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
        queue.add(crdRequest)
    }

    fun getCountriesRegionsDistrictVolley() {


        Log.w("getCountriesVolley", "===>> Countries " + getCountriesGlobal)
        var arrayOfRegions: List<RegionsItem>? = null

        var arrayOfDistricts: List<DistrictsItem>? = null
        var arrayOfCountries = getCountriesGlobal

        var listArrayCountries = ArrayList<String>()
        listArrayCountries.add(0, "Välj land")
        var listArrayRegions = ArrayList<RegionsItem>()
        // listArrayRegions.add(0, "Välj region")
        var listArrayDistricts = ArrayList<String>()
        listArrayDistricts.add(0, "Välj district")

        var listArrayIdDistrict = ArrayList<Long>()
        listArrayIdDistrict.add(0, 0)

        if (arrayOfCountries != null) {
            for (c in arrayOfCountries) {

                listArrayCountries.add(c.countryName.toString())



                arrayOfRegions = c.regions

                if (arrayOfRegions != null) {
                    for (r in arrayOfRegions) {
                        //  listArrayRegions.add(r.regionName.toString())


                        arrayOfDistricts = r.districts as List<DistrictsItem>


                        if (arrayOfDistricts != null) {
                            for (d in arrayOfDistricts) {
                                listArrayDistricts.add(d.districtName.toString())
                                // d.districtId?.toLong()?.let { listArrayIdDistrict.add(it) }
                            }

                        }
                    }
                }
            }
        }


        // regions.add(listArrayRegions.toString())


        countries = listArrayCountries
        regions = listArrayRegions
        districts = listArrayDistricts
        districtsId = listArrayIdDistrict


        showCountriesInSpinner()



        Log.w("TAGGETCRD", "Array of Countries" + listArrayCountries)
        Log.w("TAGGETCRD", "Array of Regions" + listArrayRegions)
        Log.w(
                "TAGGETCRD",
                "Array of District" + listArrayDistricts + "District Id" + listArrayIdDistrict
        )
        Log.w("TAGGETCRD", "Array of District ID" + listArrayIdDistrict)

    }


    fun goBack(){
        var backBtn = findViewById<ImageView>(R.id.backNewWineBtn)

        backBtn.setOnClickListener {

            Log.w("Back", "BTN Pressed ")
            onBackPressed()
        }
    }

    //show countries in spinner

    fun showCountriesInSpinner() {


        var spinnerC = findViewById<Spinner>(R.id.spinneCountriesNew)
        spinnerC?.adapter = ArrayAdapter<String>(
                this,
                R.layout.spinner_layout,
                R.id.textViewSpinner,
                countries
        )

        spinnerC.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
            ) {

                var countriesName = getCountriesGlobal

                if (countriesName != null) {
                    for (c in countriesName) {
                        nameCountry = c.countryName
                        Log.w("nameCountry", "nameCountry  " + nameCountry)
                        optionUserSelectionC = countries.get(position)
                        regionsArray = c.regions
                        showRegionsSpinner()
                    }

                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }


    }

    //show regions in spinner
    fun showRegionsSpinner() {

        Log.w("ShowRegion", "districtsItem " + districtsItem)
        Log.w("ShowRegion", "c.countryName" + nameCountry)
        Log.w("ShowRegion", "User selection of country" + optionUserSelectionC)
        var arrayOfRegions = ArrayList<String>()
        var arrayOfRegionsId = ArrayList<Long>()



        regionsArray?.forEach { r ->

            arrayOfRegions.add(r.regionName.toString())


            if (nameCountry == optionUserSelectionC) {


                var spinnerR = findViewById<Spinner>(R.id.spinneRegionsNew)

                spinnerR.adapter = ArrayAdapter<String>(
                        this,
                        R.layout.spinner_layout_regions,
                        R.id.textViewSpinnerRegions,
                        arrayOfRegions
                )

                spinnerR.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                            parent: AdapterView<*>?,
                            view: View?,
                            position: Int,
                            id: Long
                    ) {

                        var list = getCountriesGlobal
                        if (list != null) {
                            for (l in list) {
                                var region = l.regions
                                if (region != null) {
                                    for (r in region) {
                                        Log.w("REGIONNEAMESSS", "REGIONAMESSSSS " + r.regionName)
                                        optionUserSelectionR = arrayOfRegions.get(position)
                                        Log.w("optionUserSelectionR", "optionUserSelectionR  " + optionUserSelectionR)

                                        var districtAr = r.districts

                                        districtsArray = districtAr
                                        if (districtAr != null) {
                                            for (d in districtAr) {

                                                var regionNames = r.regionName
                                                if (regionNames == optionUserSelectionR) {

                                                    nameDistrict = d.districtName

                                                    d.districtId?.toLong()?.let { districtsId.add(it) }
                                                    Log.w("SHOWDISTRICTS", "DISTRCTIS  " + d.districtName)

                                                    showDistrictSpinener()

                                                }
                                            }
                                        }

                                    }
                                }

                            }
                        }

                    }

                    override fun onNothingSelected(parent: AdapterView<*>?) {
                    }

                }


            }
        }


    }

    //show deistrict in spinner

    fun showDistrictSpinener() {


        var arrayOfDistricts = ArrayList<String>()
        var arrayOfDistrictsID = ArrayList<Long>()

        arrayOfDistrictsID.add(districtsId[0])

        districtsArray?.forEach { j ->

            j.districtId?.toLong()?.let { arrayOfDistrictsID.add(it) }
            arrayOfDistricts.add(j.districtName.toString())


            var spinnerD = findViewById<Spinner>(R.id.spinneDistrictsNew)

            spinnerD.adapter = ArrayAdapter<String>(
                    this,
                    R.layout.spinner_layout_districts,
                    R.id.textViewSpinnerDistricts,
                    arrayOfDistricts
            )

            spinnerD.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

                override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                ) {

                    //   var idOptionD = districtsArray!![id.toInt()]

                    var idOptionD = arrayOfDistricts.get(position)
                    var idOptionDist = arrayOfDistrictsID.forEach {
                        j.districtId



                        postDistrictId = j.districtId

                        Log.w("DISTRICTIDDDDDD", "DISTRICT IIDDD " + postDistrictId)
                        Log.w("DistrictId", "UserId Selection " + idOptionD)


                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {

                }
            }
        }


    }


    //get volley greapes api
    fun getGrapesVolley() {


        val queue = com.android.volley.toolbox.Volley.newRequestQueue(applicationContext)

        val url = URL().BASEURL + URL().metadata + URL().grapesUrl
        val grapesRequest = object : StringRequest(
                Method.GET, url,
                com.android.volley.Response.Listener { response ->

                    val result = response.toString()
                    var gson = GsonBuilder().create()

                    val grapesList: List<GetGrapes> =
                            gson.fromJson(result, Array<GetGrapes>::class.java).toList()


                    getGrapesGlobal = grapesList

                    showGrapesInSpinner()


                }, com.android.volley.Response.ErrorListener { error ->
            Log.w(
                    "Error",
                    "Error Get GRAPES " + error.toString() + error.printStackTrace() + error.cause + error.cause
            )

        }) {
            override fun getBody(): ByteArray {
                return super.getBody()
            }

            override fun getBodyContentType(): String {
                return super.getBodyContentType()
            }

            override fun getParams(): MutableMap<String, String> {
                val params: MutableMap<String, String> = HashMap()

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
        queue.add(grapesRequest)

    }


/*
        var grapesListArray = ArrayList<String>()
        var grapesId = ArrayList<Long>()
        grapesId.add(0, 0)
        grapesListArray.add(0, "Välj druva")

        var arrayOfGrapes = getGrapesGlobal

        if (arrayOfGrapes != null) {
            for (g in arrayOfGrapes) {
                grapesListArray.add(g.grapeName.toString())
                g.grapeId?.toLong()?.let { grapesId.add(it) }

            }

        }

        var spinnerG = findViewById<Spinner>(R.id.spinneGrapesNew)
        spinnerG.adapter = ArrayAdapter<String>(
            this,
            R.layout.spinner_grapes_layout,
            R.id.textViewGrapes,
            grapesListArray
        )

        spinnerG.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {

                var optionGrapesId = grapesId[id.toInt()].toLong()

                postIdGrape = optionGrapesId
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }


        //get grape procent from user

        var grapesProcent = findViewById<EditText>(R.id.editTextGrapesNewProcent)

        grapesProcent.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable?) {
                postGrapeProcent = grapesProcent.text.toString().toInt()

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })*/


    //post wine
    fun postWine() {

        Log.w("PostTest", "Get value Camera image " + postImageCamera)
        Log.w("PostTest", "Get value Gallery image " + postImageGallery)
        Log.w("PostTest", "Get value postWineProcent " + postWineProcent)
        Log.w("PostTest", "Get value postGrapeProcent " + postGrapeProcent)
        Log.w("PostTest", "Get value postIdGrape " + postIdGrape)
        Log.w("PostTest", "Get value postDistrictId " + postDistrictId)
        Log.w("PostTest", "Get value postWineName " + postWineName)


        if (postDistrictId == 0L) {

            Toast.makeText(applicationContext, "Du måste väla distrikt", Toast.LENGTH_SHORT).show()
        }

        if (postIdGrape == 0L) {

            Toast.makeText(applicationContext, "Du måste väla druva", Toast.LENGTH_SHORT).show()
        }


        if (AutoCpTxVaddVineNameNew.text.toString().isEmpty()) {
            AutoCpTxVaddVineNameNew.error = "Detta fält måste fyllas i"
        }

        if (editTextProcucer.text.toString().isEmpty()) {

            editTextProcucer.error = "Detta fält måste fyllas i"

        }


        // Log.w("PostTest", "Get value postImage " + postImage )

        newNameProcentWineEditText()
        showDistrictSpinener()
        getGrapesVolley()
        getImageUrlGalleryAndCamera()

        Log.w("USERRRRR", "result  " + result)



        if (postImageCamera != null) {


            var grapes = result
            var winePostClass = WinePostClass(
                    postWineName,
                    postImageCamera,
                    postDistrictId,
                    postWineProcent,
                    postWineProducer,
                    grapes
            )
            var gson = Gson()
            var jsonstring = gson.toJson(winePostClass)
            val mediaType = "application/json; charset=utf-8".toMediaType()
            val requestBody = jsonstring.toString().toRequestBody(mediaType)
            ClassStringToken().getTokenCustom()

            val request = Request.Builder()
                    .post(requestBody)
                    .url(URL().BASEURL + "wines")
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer $stringToken")
                    .build()
            val client = OkHttpClient()

            Log.w("REQUESTBODY", "RRRRRRRRRR" + requestBody)
            client.newCall(request).enqueue(object : Callback {

                override fun onResponse(call: Call, response: Response) {

                    try {
                        var parentObject = JSONObject(response.body?.string());
                        var wineId = parentObject.getLong("wineId");


                        Log.w("Response", "wineId 1 " + wineId)
                        var intent = Intent(applicationContext, AddYearShelveActivity::class.java)
                        intent.putExtra("WINE ID", wineId.toString())
                        startActivity(intent)

                    } catch (e: JSONException) {


                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                }


                override fun onFailure(call: Call, e: IOException) {
                    Log.w(
                            "ERROR",
                            "Failed POST " + call.hashCode() + e.message + e.localizedMessage + e.cause
                    )
                }
            })

        } else {


            var grapes = result
            var winePostClass = WinePostClass(
                    postWineName,
                    postImageGallery,
                    postDistrictId,
                    postWineProcent,
                    postWineProducer,
                    grapes
            )
            var gson = Gson()
            var jsonstring = gson.toJson(winePostClass)
            val mediaType = "application/json; charset=utf-8".toMediaType()
            val requestBody = jsonstring.toString().toRequestBody(mediaType)


            ClassStringToken().getTokenCustom()
            val request = Request.Builder()
                    .post(requestBody)
                    .url(URL().BASEURL + "wines")
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer $stringToken")
                    .build()
            val client = OkHttpClient()
            Log.w("REQUESTBODY", "RRRRRRRRRR" + requestBody)
            client.newCall(request).enqueue(object : Callback {

                override fun onResponse(call: Call, response: Response) {

                    try {
                        var parentObject = JSONObject(response.body?.string());
                        var wineId = parentObject.getLong("wineId")


                        Log.w("Response", "wineId 2 " + wineId)
                        var intent = Intent(applicationContext, AddYearShelveActivity::class.java)
                        intent.putExtra("WINE ID", wineId.toString())
                        result.clear()

                        startActivity(intent)

                    } catch (e: JSONException) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                }

                override fun onFailure(call: Call, e: IOException) {
                    Log.w(
                            "ERROR",
                            "Failed POST " + call.hashCode() + e.message + e.localizedMessage + e.cause
                    )
                }
            })
        }
    }


    fun postButtonNewWineName() {

        var btnPostWineNew = findViewById<FloatingActionButton>(R.id.floatingActionButtonNew)
        btnPostWineNew.setOnClickListener {
            postWine()

        }
    }


    override fun onItemGrapeClick(itemGrape: List<GetGrapes>, position: Int) {
        Toast.makeText(applicationContext, "Du valde " + itemGrape.get(position).grapeName, Toast.LENGTH_SHORT).show()


        // var grapeP = p
        //  var jjj = grapePrecent.text.get(idGrapeSelection)
        var idGrapeSelection = itemGrape.get(position).grapeId


        if (idGrapeSelection != null) {
            choosenGrapeId = idGrapeSelection


        }

    }

    //show grapes in spinner and structure the code
    fun showGrapesInSpinner() {

        Log.w("getGrapesGlobal", "getGrapesGlobal " + getGrapesGlobal)


        spinneGrapesNew.setOnClickListener {
            var dialog: Dialog

            dialog = Dialog(this)







            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setContentView(R.layout.pop_up_grapes)

            var grapeP = dialog.findViewById<EditText>(R.id.precentGrape)
            grapeP.addTextChangedListener(object : TextWatcher {


                override fun afterTextChanged(s: Editable?) {
                    var getGrapeP = grapeP.text.toString()
                    try {
                        precentGrapeForchoosenGrape = getGrapeP.toInt()

                    } catch (ex: Exception) {
                        precentGrapeForchoosenGrape = -1
                    }
                    Log.w("getGrapeP", "getGrapeP " + getGrapeP)

                }

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                }
            })
            var grapeRecyclerView = dialog.findViewById<RecyclerView>(R.id.recyclerViewGrape)

            grapeRecyclerView?.layoutManager = LinearLayoutManager(applicationContext)
            grapeRecyclerView?.adapter =
                    getGrapesGlobal?.let { GrapeAdapter(applicationContext, it, this) }
            dialog.show()


            var btnPlus: Button = dialog.findViewById(R.id.plusBtnGrapePopUp)

            btnPlus.setOnClickListener {

                val choosenGrapeIdLocal = choosenGrapeId
                val precentGrapeForchoosenGrapeLocal = precentGrapeForchoosenGrape


                if (choosenGrapeIdLocal > 0 && precentGrapeForchoosenGrapeLocal >= 0) {
                    var toAdd = WineGrapesPostClass(choosenGrapeIdLocal, precentGrapeForchoosenGrapeLocal)
                    result.add(toAdd)
                    Toast.makeText(applicationContext, "aderrar ", Toast.LENGTH_SHORT).show()
                }
            }

            var btnCancelGrapePopUp: Button = dialog.findViewById(R.id.cancelBtnGrape)

            btnCancelGrapePopUp.setOnClickListener {

                dialog.dismiss()
            }


            var btnOkAdd: Button? = dialog?.findViewById(R.id.addBtnGrape)
            btnOkAdd?.isEnabled = true

            btnOkAdd?.setOnClickListener {


                dialog?.cancel()


            }


        }

    }

}


/*

    var spinnerD = findViewById<Spinner>(R.id.spinneDistrictsNew)

    spinnerD.adapter = ArrayAdapter<String>(
    this,
    R.layout.spinner_layout_districts,
    R.id.textViewSpinnerDistricts,
    arrayOfDistricts
    )

    spinnerD.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

        override fun onItemSelected(
            parent: AdapterView<*>?,
            view: View?,
            position: Int,
            id: Long
        ) {

            // var idOptionD = districtsArray[id.toInt()].toLong()

            var idOptionD = districtsArray?.get(position)?.districtId
            postDistrictId = idOptionD

            Log.w("DistrictId", "UserId Selection" + userOptionDistrictId)

        }

        override fun onNothingSelected(parent: AdapterView<*>?) {

        }
    }

*/
