package com.example.vinlogg

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.vinlogg.Adapters.DeleteAdapter
import com.example.vinlogg.Adapters.onDeleteClickListener
import com.example.vinlogg.ModelClassVine.*
import com.google.firebase.auth.FirebaseAuth
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody

import java.io.IOException
import java.util.*


class DeleteActivity : AppCompatActivity(), onDeleteClickListener, FetchingApi {


    var vintages: List<GetUsersWineList>? = null

    var putInventoryAmount: Int? = null
    var putInventoryShelveId: Long? = null
    var putIventoryId: Long? = null
    var putResultDelete: Int? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delete)


        getDetails()
        getStringExtra()
        back()

    }


    fun back(){

        var backBtn = findViewById<ImageButton>(R.id.backDelete)

        backBtn.setOnClickListener {

            Log.w("Back", "BTN Pressed ")
            onBackPressed()
        }
    }


    fun getDetails() {


        var queue = Volley.newRequestQueue(applicationContext)

        var wineId = intent.getStringExtra("WINE ID")
        var url = URL().BASEURL + URL().userswinelist +  URL().wineIdString + wineId


        val searchRequestVine = object : StringRequest(
            Method.GET, url,
            com.android.volley.Response.Listener { response ->

                val result = response.toString()

                Log.w("Result", "== Null" + result)


                var gson = GsonBuilder().create()

                val vintagesDetails: List<GetUsersWineList> =
                    gson.fromJson(result, Array<GetUsersWineList>::class.java).toList()

                vintages = vintagesDetails


                Log.w("DETAILS", " DEATAILSLIST  ======>>>>>> " + vintagesDetails)


                show()

            }, com.android.volley.Response.ErrorListener { error ->
                Log.w("Error", "Error Get Wines" + error.toString())

            }) {
            override fun getBody(): ByteArray {
                return super.getBody()
            }

            override fun getBodyContentType(): String {
                return super.getBodyContentType()
            }

            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                ClassStringToken().getTokenCustom()
                headers["Authorization"] = "Bearer $stringToken"
                Log.w("TokenIShereGetW", "TOKENID" + stringToken)
                return headers
            }
            override fun getParams(): MutableMap<String, String> {
                val params: MutableMap<String, String> = HashMap()
                //   params["startsWith"] = "wine"
                return params
            }

        }
        queue.add(searchRequestVine)

    }

    fun show() {

        var wineNameTextWiew = findViewById<TextView>(R.id.textViewDeleteWineName)




        var wineName = intent.getStringExtra("WINE NAME")
        var wineId = intent.getStringExtra("WINE ID")
        Log.w("DELETE", "Wine name =>> " + wineName)
        Log.w("DELETE", "Wine Id =>> " + wineId)
        Log.w("DELETE", "DETAILS  =>> " + vintages)
        wineNameTextWiew.text = wineName

        var usersWineList = vintages

        if (usersWineList != null) {
            for (v in usersWineList) {


                var vintageDetails = v.vintages

                var recyclerviewDeleteList = findViewById(R.id.recyclerViewDelete) as RecyclerView


                recyclerviewDeleteList.layoutManager = LinearLayoutManager(this)
                recyclerviewDeleteList.adapter =
                    vintageDetails?.let { DeleteAdapter(applicationContext, it, this) }
            }
            Log.w("Vintages ", "Vintages" + vintages)
        }

    }


    fun spinnerDelete() {

        var dialog: Dialog

        dialog = Dialog(this)

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.popup_delete_layout)

        dialog.show()
        var arrayOfNumbers = ArrayList<Int>()
        var showTo:Int=0
        if (putInventoryAmount!!.toInt()<=100)
            showTo = putInventoryAmount!!.toInt() + 1
        else
            showTo=150
        for(i in 1 until showTo)
        {
            arrayOfNumbers.add(i)
            if(i>=200)
                break
        }

        var userDeleteAmount: Int? = null


        var deleteSpinner = dialog.findViewById<Spinner>(R.id.spinnerDelete)

        deleteSpinner?.adapter =

            ArrayAdapter<Int>(
                applicationContext,
                R.layout.spinner_delete_layout,
                R.id.textViewSpinnerDelete,
                arrayOfNumbers


            )

        deleteSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                userDeleteAmount = arrayOfNumbers.get(position)

                // Log.w("userDeleteAmount", "User Delete Amount" + deleteUserSelectioAmount)
                // deleteUserSelectioAmount = userDeleteAmount
                Log.w("onItemSelected", "=== >> Delete User Seöection Amount" + userDeleteAmount)
                Log.w("onItemSelected", "=== >> Amount" + putInventoryAmount)
                Log.w("onItemSelected", "=== >> Inventory Id " + putIventoryId)
                Log.w("onItemSelected", "=== >> shelevId " + putInventoryShelveId)


                if (userDeleteAmount!! <=putInventoryAmount!!){

                    putResultDelete = userDeleteAmount?.toInt()?.let { putInventoryAmount?.minus(it) }

                    putIn()
                }else{

                    Toast.makeText(applicationContext, "Du kan inte radera mer än vad det finns", Toast.LENGTH_SHORT).show()
                }




                var btnCancelPopupSDelete: Button? = dialog?.findViewById(R.id.deleteBtnPopUp)
                btnCancelPopupSDelete?.isEnabled = true

                btnCancelPopupSDelete?.setOnClickListener {


                    Log.w("PUT", "=== >> put Result Delete  " + putResultDelete)

                    val intent = Intent(applicationContext, MainActivity::class.java)

                    startActivity(intent)

                    dialog?.cancel()



                }


                var btnDissmiss: Button? = dialog?.findViewById(R.id.cancelBtnPopUpDelete)
                btnDissmiss?.isEnabled = true

                btnDissmiss?.setOnClickListener {

                    Log.w("Avbryt", "Avbryt ")

                    dialog?.dismiss()


                }

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }

    }


    fun putIn() {

        var j = 7
        var h= 9
        Log.w("PUT", "=== >> Delete User Result Amount delete" + putResultDelete)





        Log.w("PUT", "=== >> Amount" + putInventoryAmount)
        Log.w("PUT", "=== >> Inventory Id " + putIventoryId)
        Log.w("PUT", "=== >> shelevId " + putInventoryShelveId)


        var inventoryPutClass =
            InventoryPutClass(putIventoryId, putResultDelete, putInventoryShelveId)
        var gson = Gson()
        var jsonstring = gson.toJson(inventoryPutClass)
        val mediaType = "application/json; charset=utf-8".toMediaType()
        val requestBody = jsonstring.toString().toRequestBody(mediaType)


        ClassStringToken().getTokenCustom()
        val request = Request.Builder()
            .put(requestBody)
            .url("http://54.72.51.80:5000/api/inventories?")
            .header("Content-Type", "application/json")
            .header("Authorization" ,"Bearer $stringToken")
            .build()
        val client = OkHttpClient()

        client.newCall(request).enqueue(object : Callback {

            override fun onResponse(call: Call, response: Response) {
                Log.w("Success!!", "PUT requestBody Inventory =>>" + response.body?.string())

            }


            override fun onFailure(call: Call, e: IOException) {
                Log.w(
                    "ERROR",
                    "Failed POST " + "Inventory  =>>" + call.hashCode() + e.message + e.localizedMessage + e.cause
                )
            }

        })
    }

    override fun onItemDeleteClick(vintageList: List<VintagesItem>, position: Int) {
        var inventoryId = vintageList.get(position).inventoryId
        var shelfId = vintageList.get(position).shelfId
        var amount = vintageList.get(position).amount

        putInventoryAmount = amount
        putIventoryId = inventoryId
        putInventoryShelveId = shelfId



        spinnerDelete()


    }






    fun getStringExtra(){



    }


}




