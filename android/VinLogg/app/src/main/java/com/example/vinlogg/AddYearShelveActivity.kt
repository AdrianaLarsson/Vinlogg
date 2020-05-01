package com.example.vinlogg

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.vinlogg.ModelClassVine.*
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.gson.GsonBuilder

import java.util.*
import kotlin.collections.ArrayList


var postIdShelve: Long? = null
var postIdVintage: Long? = null
var postAmount: String? = null
var postWineId: Long? = null
var postNewVintage: Int? = null
var postNewShelve: String? = null

class AddYearShelveActivity : AppCompatActivity() {

    var vintageGlobal: List<GetVintageClass>? = null
    var shelvesClassGlobal: List<GetShelvesClass>? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_year_shelve)
        getWineNameExtra()
        spinnerSelectYear()
        spinnerSelectShelve()
        getWineYearVolley()
        getUserShelves()
        postInventoryButtonFun()
        addNewVintage()
        addNewShelves()
        back()


    }
    fun back(){

        var backBtn = findViewById<ImageButton>(R.id.backAddShelve)

        backBtn.setOnClickListener {

            Log.w("Back", "BTN Pressed ")
            onBackPressed()
        }
    }


    fun getWineNameExtra(){

        var wineName = findViewById<TextView>(R.id.txtViewAddYear)
        var nameWine = intent.getStringExtra("WINE NAME")
        wineName.text = nameWine
    }


//get year
    fun getWineYearVolley() {


        var queue = Volley.newRequestQueue(applicationContext)
        val intent = intent
        var wineId = intent.getStringExtra("WINE ID")


        var url = URL().BASEURL + URL().inventages + URL().userId + "&wineId=" + wineId


        val searchRequestVine = object : StringRequest(
            Method.GET, url,
            com.android.volley.Response.Listener { response ->

                val result = response.toString()

                Log.w("Result", "Resilt" + result)


                var gson = GsonBuilder().create()
                val wineList: List<GetVintageClass> =
                    gson.fromJson(result, Array<GetVintageClass>::class.java).toList()
                vintageGlobal = wineList
                spinnerSelectYear()


            }, com.android.volley.Response.ErrorListener { error ->
                Log.w("Error", "Error Get Wines" + error.toString())

            }) {
            override fun getBody(): ByteArray {
                return super.getBody()
            }

            override fun getBodyContentType(): String {
                return super.getBodyContentType()
            }

            override fun getParams(): MutableMap<String, String> {
                val params: MutableMap<String, String> = HashMap()
                //   params["startsWith"] = "wine"
                return params
            }

            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                getCustomToken()
                headers["Authorization"] = "Bearer $stringToken"
                Log.w("TokenIShereGetYears", "TOKENID" + stringToken)
                return headers
            }


        }
        queue.add(searchRequestVine)


    }

    //select year

    fun spinnerSelectYear() {


        try {

            var amount: EditText? = findViewById(R.id.amonutInventory) as EditText
            amount?.addTextChangedListener(object : TextWatcher {

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                }

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun afterTextChanged(s: Editable?) {
                    postAmount = amount.text.toString()
                    Log.w("amount.text.toString()", "== wineId" + postAmount)


                }
            })

        } catch (e: Exception) {


            Log.w("E", "E: Exception " + e.message)
        }


        var arraOfYear = vintageGlobal
        var listOfYearToSpinner = ArrayList<String>()
        var yearId = ArrayList<Long>()
        listOfYearToSpinner.add(0, "Välj årgång")
        yearId.add(0, 0)


        if (arraOfYear != null) {
            for (y in arraOfYear) {

                listOfYearToSpinner.add(y.year.toString())
                y.vintageId?.toLong()?.let { yearId.add(it) }


            }
        }


        try {


            var spinnerSelectYear = findViewById<Spinner>(R.id.selectYear)
            spinnerSelectYear.adapter = ArrayAdapter<String>(
                this,
                R.layout.select_year_spinner_layout,
                R.id.textViewSpinnerYear,
                listOfYearToSpinner
            )


            spinnerSelectYear.onItemSelectedListener = object : AdapterView.OnItemClickListener,
                AdapterView.OnItemSelectedListener {
                override fun onItemClick(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                }

                override fun onItemSelected(
                    parent: AdapterView<*>?, view: View?, position: Int, id: Long
                ) {
                    var yearIdselectionUser = yearId?.get(id.toInt())
                    postIdVintage = yearIdselectionUser

                    Log.w("VINTAGEID", "Shelve ID" + postIdVintage)

                }

                override fun onNothingSelected(parent: AdapterView<*>?) {

                }

            }


        } catch (e: Exception) {

            Log.w("E", "e:Exception " + e.message)
        }


    }


    //get user shelve
    fun getUserShelves() {


        val queue = com.android.volley.toolbox.Volley.newRequestQueue(applicationContext)
        val intent = intent
        var wineId = intent.getStringExtra("WINE ID")
        var url = URL().BASEURL + URL().shelvesUrl + URL().userId + URL().wineIdString + wineId


        val searchRequestVine = object : StringRequest(
            Method.GET, url,
            com.android.volley.Response.Listener { response ->

                val result = response.toString()

                Log.w("Result", "== Null" + result)


                var gson = GsonBuilder().create()

                val wineList: List<GetShelvesClass> =
                    gson.fromJson(result, Array<GetShelvesClass>::class.java).toList()

                shelvesClassGlobal = wineList

                spinnerSelectShelve()


                Log.w("GETWINENAME", " WINELIST  ======>>>>>> " + wineList)


            }, com.android.volley.Response.ErrorListener { error ->
                Log.w("Error", "Error Get Wines" + error.toString())

            }) {
            override fun getBody(): ByteArray {
                return super.getBody()
            }

            override fun getBodyContentType(): String {
                return super.getBodyContentType()
            }

            override fun getParams(): MutableMap<String, String> {
                val params: MutableMap<String, String> = HashMap()
                //   params["startsWith"] = "wine"
                return params
            }

            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                getCustomToken()
                headers["Authorization"] = "Bearer $stringToken"
                Log.w("TokenIShereGetShelve", "TOKENID" + stringToken)
                return headers
            }


        }


        queue.add(searchRequestVine)


    }

//select shelve in spinner
    fun spinnerSelectShelve() {

        var arrayOfShelves = shelvesClassGlobal
        var shelveId = ArrayList<Long>()
        var listShelves = ArrayList<String>()


        listShelves.add(0, "Välj hylla")
        shelveId.add(0, 0)
        if (arrayOfShelves != null) {
            for (s in arrayOfShelves) {
                listShelves.add(s.name.toString())
                s.shelfId?.toLong()?.let { shelveId.add(it) }

            }
        }

        try {
            var spinnerSelectShelve = findViewById<Spinner>(R.id.selectShelve)

            spinnerSelectShelve.adapter = ArrayAdapter<String>(
                this,
                R.layout.select_shelve_spinner_layout,
                R.id.textViewSpinnerShelve,
                listShelves
            )

            spinnerSelectShelve.onItemSelectedListener = object : AdapterView.OnItemClickListener,
                AdapterView.OnItemSelectedListener {
                override fun onItemClick(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {

                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                }

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {

                    var shelveIdselectionUser = shelveId?.get(id.toInt())
                    postIdShelve = shelveIdselectionUser



                }
            }

        } catch (e: Exception) {

            Log.w("E", " e:Exception " + e.cause + e.message)
        }

    }

    //put post iventory

    fun postInventoryButtonFun() {
        var btnSavePistInventory: FloatingActionButton? = null
        btnSavePistInventory = findViewById(R.id.floatingActionButtonSave)
        btnSavePistInventory.setOnClickListener {
            FetchApi().postInventory()
            var intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)


        }
    }

    //pop up for add new vintage
    fun addNewVintage() {

        var dialog: Dialog? = null
        var btnAddVintage: Button? = null

        btnAddVintage = this.findViewById(R.id.yearNewAddBtn)


        btnAddVintage.setOnClickListener {
            dialog = Dialog(this)
            dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog?.setContentView(R.layout.layout_pop_addnew_vintage)
            dialog?.show()
            var btnCancelPopup: Button? = dialog?.findViewById(R.id.cancelBtnVintagePopUp)
            btnCancelPopup?.isEnabled = true

            btnCancelPopup?.setOnClickListener {

                dialog?.cancel()
            }


            var vintageBtnPopOk: Button? = dialog?.findViewById(R.id.addBtnVintagePopUp)
            var editTextNewVintage: EditText? =
                dialog?.findViewById<EditText>(R.id.editTextNewVintagae)
            vintageBtnPopOk?.setOnClickListener {

                val intent = intent
                var wineId = intent.getStringExtra("WINE ID")

                postWineId = wineId.toLong()
                Log.w("addNewVintage", "== wineId" + wineId)
                Log.w("addNewVintage", "New Vintage Edittex" + editTextNewVintage?.text.toString())
                postNewVintage = editTextNewVintage?.text.toString().toInt()






                FetchApi().postvVintage()
                finish()
                val i = intent
                overridePendingTransition(0, 0)
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                finish()
                overridePendingTransition(0, 0)
                startActivity(i)
                dialog?.dismiss()




            }


        }


    }

    //pop up fpr add new shelve

    fun addNewShelves() {

        var dialog: Dialog
        var btnAddShelves: Button? = null
        btnAddShelves = this.findViewById(R.id.shelveAddnew)
        btnAddShelves?.setOnClickListener {

            dialog = Dialog(this)
            dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog?.setContentView(R.layout.layout_addnew_shelves_popup)
            dialog?.show()


            var btnCancelPopupS: Button? = dialog?.findViewById(R.id.cancelBtnShelvesPopUp)
            btnCancelPopupS?.isEnabled = true

            btnCancelPopupS?.setOnClickListener {

                dialog?.cancel()


            }

            var popShelvesOkBtn = dialog.findViewById<Button>(R.id.addBtnShelvesPopUp)
            var editTextShelevs: EditText? = dialog.findViewById<EditText>(R.id.editTextNewShelves)
            popShelvesOkBtn.setOnClickListener {

                postNewShelve = editTextShelevs?.text.toString()
                Log.w("addNewShelves", "New Shelves Edittex" + editTextShelevs?.text.toString())

                FetchApi().postShelves()
                finish()
                val intent = intent
                overridePendingTransition(0, 0)
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                finish()
                overridePendingTransition(0, 0)
                startActivity(intent)
                dialog.dismiss()

            }

        }

    }

    fun getCustomToken(){


        val mUser = FirebaseAuth.getInstance().currentUser
        if (mUser != null) {
            mUser.getIdToken(true)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val idToken = task.result?.token
                        val expirationTimestamp = task.result?.expirationTimestamp
                        stringToken = idToken

                        Log.w("DeleteActivity", "Token  === >>> " + idToken)
                        Log.w("DeleteActivity", "expirationTimestamp  === >>> " + expirationTimestamp)

                    } else {
                        task.exception

                        Log.w("wrong", "wrongwrong" + task.exception)
                    }
                }
        }




    }


}

