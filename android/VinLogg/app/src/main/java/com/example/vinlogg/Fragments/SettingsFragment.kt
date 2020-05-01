package com.example.vinlogg.Fragments


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.vinlogg.AddYearShelveActivity
import com.example.vinlogg.LoginActivity
import com.example.vinlogg.ModelClassVine.*

import com.google.firebase.database.*
import com.example.vinlogg.R
import com.google.firebase.auth.FirebaseAuth
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.fragment_settings.*
import okhttp3.*
import org.json.JSONArray
import org.json.JSONObject
import org.w3c.dom.Text
import java.io.IOException


/**
 * A simple [Fragment] subclass.
 */
class SettingsFragment : Fragment() {


    private var mDatabaseReference: DatabaseReference? = null
    private var mDatabase: FirebaseDatabase? = null
    private var mAuth: FirebaseAuth? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {



        var view = inflater.inflate(R.layout.fragment_settings, container, false)








        logut(view)
        showUserInfo(view)

        return view


    }


    fun logut(view: View){
         var btnLogOut : Button? = null

        btnLogOut = view?.findViewById(R.id.logOut)
        btnLogOut?.setOnClickListener {


            FirebaseAuth.getInstance().signOut()
            var intent =  Intent(context, LoginActivity::class.java)
             startActivity(intent);


        }



        }



    fun showUserInfo(view: View) {



        val firebaseAuth = FirebaseAuth.getInstance()
        val firebaseUser = firebaseAuth.currentUser


        var userNode = firebaseUser?.uid
        var settIngEmail : EditText = view.findViewById(R.id.emailNameSettings)
        if (settIngEmail != null){

            settIngEmail.setText(firebaseUser?.email)

        }


        Log.w(
            "Tag", " UserId : =====>>>> " + userNode
        )


        val db = FirebaseDatabase.getInstance()
        val myRef = db.getReference("Users")
        val userRef = firebaseUser?.uid?.let { myRef.child(it) }

        userRef?.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                var firstNameS = view.findViewById<EditText>(R.id.firstNameSettings)
                var lastNameS = view.findViewById<EditText>(R.id.lastNameSettings)
                var userNameS = view.findViewById<EditText>(R.id.userNameSettings)



                firstNameS.setText(dataSnapshot.child("firstName").value as? String)
                lastNameS.setText(dataSnapshot.child("lastName").value as? String)
                userNameS.setText(dataSnapshot.child("userName").value as? String)







            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w("ERROR", "Failed to read value.", error.toException())
            }


        })

    }



}





















/*


    fun getUserSelectionPost(){

   fun showGrapes(){


       Log.w(" In showGrapes", "listOfTest " + listOfGrapes)
        var arraOfGrapesName = listOfGrapes?.let {
            ArrayList<GetGrapes>(it)
        }

        var nameGrapes = ArrayList<String>()
        for (i in arraOfGrapesName!!){
            nameGrapes.add(i.grapeName.toString())
        }

        Log.w(" In ShowGrapes", "only Names " + nameGrapes)




    }*/







/*

                //val jsonObject = JsonArray()
                //jsonObject.plus("name", "yesss")
              //   jsonObject.put("Memberid", 1)

                val queue = com.android.volley.toolbox.Volley.newRequestQueue(activity)
                val url = "http://10.110.108.187:5000/api/shelves"


                val stringRequest = JsonArrayRequest(
                        Request.Method.POST, url, jsonObject,
                        Response.Listener { response ->


                            },Response.ErrorListener{

                                Log.w("Exception", "Exception " )

                           })


                try {
                        queue.add(stringRequest)
                        Log.w("Successss", "Response " )


                    }catch (e:Exception){

                        Log.w("Exception", "Exception "+ e )

                    }




                 }
          }

*/





/*        val queue = com.android.volley.toolbox.Volley.newRequestQueue(activity)
        val url = "http://10.110.108.182:5000/api/shelves"

        val params: JSONObject = JSONObject()

        try {
            params.put("name", "yesss")
            params.put("MemberId", 1)
            //params.put("version", "2.5")
        } catch (e: JSONException) { // TODO Auto-generated catch block
            e.printStackTrace()
        }
//

        //
        val jsonObjReq: JsonObjectRequest = object : JsonObjectRequest(
            POST,
            url, params,
            Response.Listener { response -> Log.d("Tag", response.toString())
                if (ServerError() != null ){


                }

            },
            Response.ErrorListener() {
                    error -> VolleyLog.d("TAG", "Error: " + error.message)






            }) {

            override fun getHeaders(): Map<String, String> {
                val headers =
                    HashMap<String, String>()
                headers["Content-Type"] = "application/json; charset=utf-8"
                return headers
            }
        }
        jsonObjReq.retryPolicy = DefaultRetryPolicy(
            60000,
            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        )


// Adding request to request queue
        // Adding request to request queue
        queue.add(jsonObjReq)
    }*/



/*

    var url = "http://10.110.108.182:5000/api/shelves/"
    val jsonobj = JSONObject()

    jsonobj.put("Name", "aaa")
    jsonobj.put("MemberId", 1)

    val queue = com.android.volley.toolbox.Volley.newRequestQueue(activity)
    val req = JsonObjectRequest(Request.Method.POST, url, jsonobj,
        Response.Listener { response ->

            println("oooooooooooooookkkkkkkkkkkkkkkkkk" + jsonobj)


        }, Response.ErrorListener { error: VolleyError ->
            println("Error $error.message")
        }
    )

    queue.add(req)
}

*/



/*

        var responseStringGrapes: String = ""
        var jsonArrayGrapes: JSONArray
        var jsonObjectWines: JSONObject
        var grapeName: Any



        val queue = com.android.volley.toolbox.Volley.newRequestQueue(activity)
        val url = "https://prod.tydalsystems.se/api/new_spinfo3.php?typ=ank&tpl=cst&transport=train"



        var stringRequest = StringRequest(
            Request.Method.POST, url,
            Response.Listener<String> { response ->

                responseStringGrapes = response.substring(0, response.length)

                var gson = GsonBuilder().create()
                val grapelist: List<Test> = gson.fromJson(responseStringGrapes, Array<Test>::class.java).toList()

                Log.w("EEERRROOORRR", " ======>>>>>> Grapes : It didnt work!" + grapelist)

                val oneTest = grapelist[0]

              //  val mainActivity = activity!! as MainActivity

              //  mainActivity.setTest(oneTest)





            }, Response.ErrorListener {

                Log.w("EEERRROOORRR", " ======>>>>>> Grapes : It didnt work!")


            })


        queue.add(stringRequest)


    }
*/






































