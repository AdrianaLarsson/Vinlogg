package com.example.vinlogg.Fragments


import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.LinearGradient
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.vinlogg.Adapters.WineAdapter
import com.example.vinlogg.AddNewWineActivity
import com.example.vinlogg.ModelClassVine.*
import com.example.vinlogg.R
import com.google.firebase.auth.FirebaseAuth
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.fragment_my_wine_list.*
import kotlinx.android.synthetic.main.fragment_my_wine_list.view.*
import java.util.HashMap

/**
 * A simple [Fragment] subclass.
 */

var userSelectionCountryId: Long? = null
var userRegionSelectionId: Long? = null
var userDistrictId: Long? = null
var userGrapesIdSelection: Long? = null
var userSelectioWineNameStartsWithMyWinelist: String = ""

class MyWineListFragment : Fragment(), FetchingApi {


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment


        ClassStringToken().getTokenCustom()
        val view = inflater.inflate(R.layout.fragment_my_wine_list, container, false)
        // Toast.makeText(activity, "Din vin lista", Toast.LENGTH_SHORT).show()

        //getToken(view)


        searchUserWineList(view)
        countriesRegionsDistrict(view)
        getGrapesUrlQue(view)
        btnCelarFilter(view)
        btnFilter(view)
        gotToNewWineSide(view)
        return view


    }

    fun gotToNewWineSide(view: View){

        var btnNewSide = view.findViewById<Button>(R.id.addNewWineBtnWineList)

        btnNewSide.setOnClickListener {

            val intent = Intent(context, AddNewWineActivity::class.java)

            startActivity(intent)

        }

    }


    class showWineList(showWineList: List<GetUsersWineList>, view: View) :
            GetUsersWineList(showWineList), FetchingApi {
        init {

            var recyclerviewVineList = view.findViewById(R.id.recyclerviewVineList) as? RecyclerView

            var txtViewNoWines = view.rootView.findViewById(R.id.noWinesTexViw) as? TextView
            var sadIconImg = view.findViewById(R.id.imageViewSadIcon) as? ImageView

            Log.w("wineList", "wineList" + showWineList)

            if (showWineList.equals(null)) {


                txtViewNoWines?.setVisibility(View.VISIBLE)
                sadIconImg?.setVisibility(View.VISIBLE)
                recyclerviewVineList?.visibility = View.GONE


            } else {

                if (txtViewNoWines != null) {

                    txtViewNoWines.visibility = View.GONE
                    sadIconImg?.visibility = View.GONE
                }

                //set the adapter and recyclerview

                recyclerviewVineList?.layoutManager = LinearLayoutManager(view.context)
                recyclerviewVineList?.adapter = showWineList?.let { WineAdapter(view.rootView.context, it) }

            }
        }

    }


    fun searchUserWineList(view: View) {


        var searchEdiTxt = view.findViewById<EditText>(R.id.searchUserWineList)
        var queue = Volley.newRequestQueue(this.context?.applicationContext)
        var url = getMyWinelistUrl()

        getWines(url, queue, view)
        searchEdiTxt.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

                var searchString = searchEdiTxt.text.toString()

                if (searchString != null) {
                    userSelectioWineNameStartsWithMyWinelist = searchString

                    var url = getMyWinelistUrl()
                    getWines(url, queue, view)

                }

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }
        })

    }


    fun btnFilter(view: View) {

        var filterBtn = view.findViewById<Button>(R.id.wineListFilterBtn)

        filterBtn.setOnClickListener {


            wineListCountrySpinner.visibility = View.VISIBLE
            wineListRegionSpinner.visibility = View.VISIBLE
            wineListDistrictSpinner.visibility = View.VISIBLE
            wineListGrapesSpinner.visibility = View.VISIBLE
            addNewWineBtnWineList.visibility = View.VISIBLE
            clearFilterBtnWineList.visibility = View.VISIBLE

        }


    }

    fun btnCelarFilter(view: View) {

        var clearfilterBtn = view.findViewById<Button>(R.id.clearFilterBtnWineList)


        clearfilterBtn.setOnClickListener {

            val ft: FragmentTransaction = fragmentManager!!.beginTransaction()
            ft.detach(this).attach(this).commit()

        }


    }


    fun countriesRegionsDistrict(view: View) {


        val url = URL().BASEURL + URL().metadata + URL().countriesRegionDistrict
        var queue = Volley.newRequestQueue(this.context?.applicationContext)
        getCounRegionDist(url, queue, view)

    }

    class showCouRegionDist(list: List<GetCountriesRegionsDistrictClass>, view: View) :
            GetCountriesRegionsDistrictClass(list), FetchingApi {
        var listRegions = list

        init {

            Log.w("listtttt", "list " + list)

            var v = view
            Log.w("countriesregdist", "Grapelist " + list)
            var countriesName = ArrayList<String>()
            var countryId = ArrayList<Long>()
            countriesName.add("Välj ett land")
            countryId.add(0)

            for (c in list) {

                countriesName.add(c.countryName.toString())
                c.countryId?.let { countryId.add(it) }
            }

// ======================= SHOW COUNTRIES IN SPINNER ===============================


            var userSelectionC: Long? = null
            var countrySpinner = view.rootView.findViewById<Spinner>(R.id.wineListCountrySpinner)
            countrySpinner.adapter =
                    ArrayAdapter<String>(
                            view.context,
                            R.layout.spinner_layout,
                            R.id.textViewSpinner,
                            countriesName
                    )

            countrySpinner.onItemSelectedListener =
                    object : AdapterView.OnItemSelectedListener {
                        override fun onNothingSelected(parent: AdapterView<*>?) {

                        }

                        override fun onItemSelected(
                                parent: AdapterView<*>?,
                                view: View,
                                position: Int,
                                id: Long
                        ) {


                            var id = countryId.get(position)




                            if (id > 0L || !id!!.equals(-1L)) {
                                userSelectionCountryId = id

                                Log.w("userSelectionCountryId", "userSelectionCountryId " + userSelectionCountryId)
                                MyWineListFragment().showCountryWineBasedOnCountry(v)
                            } else {


                            }


                            var userSelectionCountry = countriesName.get(position)
                            if (!userSelectionCountry.isEmpty()) {
                                showRegionInSpinner(v, userSelectionCountry)
                            }

                        }

                    }

        }


        fun showRegionInSpinner(view: View, userSelectionCountry: String) {

            var spinnerRegions = view?.findViewById<Spinner>(R.id.wineListRegionSpinner)
            var regionsName = ArrayList<String>()
            var regionsId = ArrayList<Long>()
            regionsId.add(0)
            regionsName.add("Välj en region")
            var countryName: String
            Log.w("userSelectionCId", "userSelectionCountry" + userSelectionCountry)

            for (list in listRegions) {
                countryName = list.countryName.toString()

                if (countryName == userSelectionCountry) {

                    for (r in list.regions!!) {
                        regionsName.add(r.regionName.toString())
                        r.regionId?.toLong()?.let { regionsId.add(it) }



                        spinnerRegions?.adapter =

                                ArrayAdapter<String>(
                                        view.context,
                                        R.layout.spinner_layout_regions,
                                        R.id.textViewSpinnerRegions,
                                        regionsName
                                )


                        var v = view

                        spinnerRegions?.onItemSelectedListener =
                                object : AdapterView.OnItemSelectedListener {
                                    override fun onNothingSelected(parent: AdapterView<*>?) {


                                    }

                                    override fun onItemSelected(
                                            parent: AdapterView<*>?,
                                            view: View?,
                                            position: Int,
                                            id: Long
                                    ) {

                                        var id = regionsId.get(position)

                                        userRegionSelectionId = id


                                        if (id != 0L) {
                                            userRegionSelectionId = id
                                            if (userRegionSelectionId!! > 0L || userSelectionRegionId!!.equals(-1L)) {

                                                MyWineListFragment().showCountryWineBasedOnCountry(v)



                                                Log.w(
                                                        "userSelectionRegionId",
                                                        "userSelectionRegionId " + userRegionSelectionId
                                                )


                                            }
                                        }


                                        var userSelectionR = regionsName.get(position)
                                        showDistrictInSpinner(v, userSelectionR)

                                    }


                                }
                    }
                }
            }


        }


        fun showDistrictInSpinner(view: View, userSelectionR: String) {
            var spinnerDistrict = view?.findViewById<Spinner>(R.id.wineListDistrictSpinner)
            var districtName = ArrayList<String>()
            var districtId = ArrayList<Long>()
            districtId.add(0)


            districtName.add("Välj ett distrikt")

            var regionNames: String
            var v = view
            for (list in listRegions) {
                for (r in list.regions!!) {
                    regionNames = r.regionName.toString()

                    if (regionNames == userSelectionR) {
                        for (j in r.districts!!) {
                            districtName.add(j.districtName.toString())
                            j.districtId?.let { districtId.add(it) }



                            spinnerDistrict?.adapter =

                                    ArrayAdapter<String>(
                                            view.context,
                                            R.layout.spinner_layout_districts,
                                            R.id.textViewSpinnerDistricts,
                                            districtName
                                    )

                            spinnerDistrict?.onItemSelectedListener =
                                    object : AdapterView.OnItemSelectedListener {
                                        override fun onNothingSelected(parent: AdapterView<*>?) {

                                        }

                                        override fun onItemSelected(
                                                parent: AdapterView<*>?,
                                                view: View?,
                                                position: Int,
                                                id: Long
                                        ) {

                                            var id = districtId.get(position)


                                            Log.w(
                                                    "userDistrictId ",
                                                    "userDistrictId  " + userDistrictId
                                            )


                                            if (id > 0L || id.equals(-1L)) {
                                                userDistrictId = id

                                                MyWineListFragment().showCountryWineBasedOnCountry(v)
                                            }


                                        }

                                    }


                        }
                    }

                }

            }


        }


    }


    fun getGrapesUrlQue(view: View) {

        val url = URL().BASEURL + URL().metadata + URL().grapesUrl
        var queue = Volley.newRequestQueue(view.context?.applicationContext)
        getGrapesToWinelist(url, queue, view)

    }


    class Grapes(grapesList: List<GetGrapes>, view: View) : GetGrapes(grapesList) {

        init {
            var grapesNames = ArrayList<String>()
            var grapesId = ArrayList<Long>()
            grapesNames.add("Välj druva")
            grapesId.add(0)

            var spinnerGrape = view?.findViewById<Spinner>(R.id.wineListGrapesSpinner)

            for (g in grapesList) {
                grapesNames.add(g.grapeName.toString())
                g.grapeId?.let { grapesId.add(it) }


            }

            spinnerGrape?.adapter =

                    ArrayAdapter<String>(
                            view.context,
                            R.layout.spinner_layout_districts,
                            R.id.textViewSpinnerDistricts,
                            grapesNames
                    )

            var v = view
            spinnerGrape?.onItemSelectedListener =
                    object : AdapterView.OnItemSelectedListener {
                        override fun onNothingSelected(parent: AdapterView<*>?) {

                        }

                        override fun onItemSelected(
                                parent: AdapterView<*>?,
                                view: View?,
                                position: Int,
                                id: Long
                        ) {

                            var id = grapesId.get(position)

                            userGrapesIdSelection = id

                            if (userGrapesIdSelection!! > 0L || userGrapesIdSelection!!.equals(-1L)) {

                                MyWineListFragment().showCountryWineBasedOnCountry(v)

                                Log.w(
                                        "userGrapesIdSelection ",
                                        "userGrapesIdSelection   " + userGrapesIdSelection
                                )
                            }


                        }
                    }


        }
    }

    fun showCountryWineBasedOnCountry(view: View) {


        var queue = Volley.newRequestQueue(view.context.applicationContext)
        var url = getMyWinelistUrl()

        getWines(url, queue, view)


    }

    fun getMyWinelistUrl(): String {

        var userGrapesIdSelectionLocal = userGrapesIdSelection
        var userSelectionCountryIdLocal = userSelectionCountryId
        var userRegionSelectionIdLocal = userRegionSelectionId
        var userDistrictIdLocal = userDistrictId
        var userSelectioWineNameStartsWithMyWinelistLocal = userSelectioWineNameStartsWithMyWinelist

        var url = URL().BASEURL + URL().userswinelistFullUrl

        if (userGrapesIdSelectionLocal != null && userGrapesIdSelectionLocal > 0) {
            url = url.replace("grapeid=-1", "grapeid=$userGrapesIdSelectionLocal")
        }
        if (userSelectionCountryIdLocal != null && userSelectionCountryIdLocal > 0) {
            url = url.replace("countryid=-1", "countryid=$userSelectionCountryIdLocal")
        }
        if (userRegionSelectionIdLocal != null && userRegionSelectionIdLocal > 0) {
            url = url.replace("regionid=-1", "regionid=$userRegionSelectionIdLocal")
        }
        if (userDistrictIdLocal != null && userDistrictIdLocal > 0) {
            url = url.replace("districtid=-1", "districtid=$userDistrictIdLocal")
        }
        if (!userSelectioWineNameStartsWithMyWinelistLocal.isNullOrBlank()) {
            url = url.replace("startswith=", "startswith=$userSelectioWineNameStartsWithMyWinelistLocal")
        }

        Log.w(
                "MyWinelist",
                "MyWinelist ===>> " + url
        )
        return url
    }


}










