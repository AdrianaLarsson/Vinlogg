package com.example.vinlogg.Fragments


import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.toolbox.Volley
import com.example.vinlogg.Adapters.SearchAdapter
import com.example.vinlogg.AddNewWineActivity
import com.example.vinlogg.ModelClassVine.*
import com.example.vinlogg.R
import kotlinx.android.synthetic.main.fragment_search_vine.*
import kotlin.contracts.Returns


/**
 * A simple [Fragment] subclass.
 */


var countryN: String? = null
var userSelectionR: String? = null
var regionN: String? = null
var regionsObject: List<RegionsClass>? = null
var districtList: List<DistrictClass>? = null
var districts: String? = null
var listOfCouRegDis: List<GetCountriesRegionsDistrictClass>? = null
var userSelectionCountry: String? = null

var userSelectionCId: Long? = null
var userSelectionRegionId: Long? = null
var userSelectionDistrictId: Long? = null
var userSelectioIdGrape: Long? = null
var userSelectioWineNameStartsWith: String? = null

class SearchWineFragment : Fragment(), FetchingApi {


    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {


        var view = inflater.inflate(R.layout.fragment_search_vine, container, false)





        getGrapesUrlQue(view)
        toNewWineSide(view)
        apiFetch(view)
        countriesRegionsDistrict(view)
        getFilters(view)
        clearFilters(view)

        return view


    }


    fun apiFetch(view: View) {

        var autoCompleteTextViewSearchVine: EditText? = null

        autoCompleteTextViewSearchVine =
                view.findViewById(R.id.autoCompleteTextViewaddVineName) as EditText

        var queue = Volley.newRequestQueue(this.context?.applicationContext)

        autoCompleteTextViewSearchVine.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {


                try {

                    var searchWineString = autoCompleteTextViewSearchVine.text.toString()


                    if (searchWineString != null) {


                        userSelectioWineNameStartsWith = searchWineString
                        var url = getSearchWinelistUrl()

                        getWines(url, queue, view)


                    }

                } catch (e: Exception) {


                }


            }

            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }
        })


    }

    fun getSearchWinelistUrl(): String {

        var userSelectioIdGrapeLocal = userSelectioIdGrape
        var userSelectionCIdLocal = userSelectionCId
        var userSelectionRegionIdLocal = userSelectionRegionId
        var userSelectionDistrictIdLocal = userSelectionDistrictId
        var userSelectioWineNameStartsWithLocal = userSelectioWineNameStartsWith

        var url = URL().BASEURL + URL().allWinelistFullUrl

        if (userSelectioIdGrapeLocal != null && userSelectioIdGrapeLocal > 0) {
            url = url.replace("grapeid=-1", "grapeid=$userSelectioIdGrapeLocal")
        }
        if (userSelectionCIdLocal != null && userSelectionCIdLocal > 0) {
            url = url.replace("countryid=-1", "countryid=$userSelectionCIdLocal")
        }
        if (userSelectionRegionIdLocal != null && userSelectionRegionIdLocal > 0) {
            url = url.replace("regionid=-1", "regionid=$userSelectionRegionIdLocal")
        }
        if (userSelectionDistrictIdLocal != null && userSelectionDistrictIdLocal > 0) {
            url = url.replace("districtid=-1", "districtid=$userSelectionDistrictIdLocal")
        }
        if (!userSelectioWineNameStartsWithLocal.isNullOrBlank()) {
            url = url.replace("startswith=", "startswith=$userSelectioWineNameStartsWithLocal")
        }

        Log.w(
                "SearchWinelistUrl",
                "SearchWinelistUrl ===>> " + url
        )
        return url
    }

    fun getFilters(view: View) {

        var filterBtn = view.findViewById<Button>(R.id.btnFilter)

        filterBtn.setOnClickListener {

            spinneCountries.visibility = View.VISIBLE
            regionSpinner.visibility = View.VISIBLE
            districtSpinner.visibility = View.VISIBLE
            spinnerViewGrapes.visibility = View.VISIBLE
            clearFilterBtn.visibility = View.VISIBLE
            buttonNewWine.visibility = View.VISIBLE


        }


    }


    fun clearFilters(view: View) {

        var clearBtnFilter = view.findViewById<Button>(R.id.clearFilterBtn)
        clearBtnFilter.setOnClickListener {

            spinneCountries.visibility = View.VISIBLE
            regionSpinner.visibility = View.VISIBLE
            districtSpinner.visibility = View.VISIBLE
            spinnerViewGrapes.visibility = View.VISIBLE
            clearFilterBtn.visibility = View.VISIBLE
            buttonNewWine.visibility = View.VISIBLE

            val ft: FragmentTransaction = fragmentManager!!.beginTransaction()
            ft.detach(this).attach(this).commit()


        }


    }


    class showWineList(showWineList: List<GetAllWineListSearchWith>, view: View) :
            GetAllWineListSearchWith(showWineList) {


        init {

            //set the adapter and recyclerview

            var recyclerSearch =
                    view.rootView.findViewById(R.id.recyclerViewSearch) as? RecyclerView

            recyclerSearch?.layoutManager = LinearLayoutManager(view.context)
            recyclerSearch?.adapter = SearchAdapter(view.rootView.context, showWineList)


            Log.w("wineList", "wineList" + showWineList)


            var wineNames = ArrayList<String>()

            var arraOfWineNames = showWineList

            if (arraOfWineNames != null) {
                for (i in arraOfWineNames) {

                    wineNames.add(i.wineName.toString())
                    // wineId.add(i.station)
                }
            }

        }

    }


    fun countriesRegionsDistrict(view: View) {


        val url = URL().BASEURL + URL().metadata + URL().countriesRegionDistrict
        var queue = Volley.newRequestQueue(this.context?.applicationContext)
        GetCountriesRegionsDistrict(url, queue, view)


    }

    class CountriesRegionsDistrict(list: List<GetCountriesRegionsDistrictClass>, view: View) :
            GetCountriesRegionsDistrictClass(list), FetchingApi {

        var listRegions = list

        init {
            var v = view
            Log.w("countriesregdist", "Grapelist " + list)
            var countriesName = ArrayList<String>()
            var countryId = ArrayList<Long>()
            countriesName.add("Välj ett land")
            countryId.add(0)

            for (c in listRegions) {

                countriesName.add(c.countryName.toString())
                c.countryId?.let { countryId.add(it) }
            }

// ======================= SHOW COUNTRIES IN SPINNER ===============================


            var userSelectionC: Long? = null
            var countrySpinner = view.rootView.findViewById<Spinner>(R.id.spinneCountries)
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

                            userSelectionCId = countryId.get(position)

                            if (userSelectionCId!! > 0L || !userSelectionCId!!.equals(-1L)) {
                             //   Log.w("userSelectionCId", "userSelectionCId " + userSelectionCountry)

                                SearchWineFragment().showCountryWineBasedOnCountry(view)
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

            var spinnerRegions = view?.findViewById<Spinner>(R.id.regionSpinner)
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

                                        userSelectionRegionId = id


                                        if (id != 0L) {
                                            userSelectionRegionId = id
                                            if (userSelectionRegionId!! > 0L || userSelectionRegionId!!.equals(
                                                            -1L
                                                    )
                                            ) {


                                                SearchWineFragment().showCountryWineBasedOnCountry(v)

                                                Log.w(
                                                        "userSelectionRegionId",
                                                        "userSelectionRegionId " + userSelectionRegionId
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
            var spinnerDistrict = view?.findViewById<Spinner>(R.id.districtSpinner)
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

                                            userSelectionDistrictId = districtId.get(position)


                                            if (userSelectionDistrictId!! > 0L || userSelectionDistrictId!!.equals(-1L)) {

                                                SearchWineFragment().showCountryWineBasedOnCountry(v)
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
        getGrapes(url, queue, view)

    }


    class Grapes(grapesList: List<GetGrapes>, view: View) : GetGrapes(grapesList) {

        init {
            var grapesNames = ArrayList<String>()
            var grapesId = ArrayList<Long>()
            grapesNames.add("Välj druva")
            grapesId.add(0)

            var spinnerGrape = view?.findViewById<Spinner>(R.id.spinnerViewGrapes)

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

                            userSelectioIdGrape = grapesId.get(position)



                                SearchWineFragment().showCountryWineBasedOnCountry(v)



                        }
                    }


        }
    }


    fun toNewWineSide(view: View) {

        var btnNewSide = view.findViewById<Button>(R.id.buttonNewWine)

        btnNewSide.setOnClickListener {

            val intent = Intent(context, AddNewWineActivity::class.java)

            startActivity(intent)

        }
    }

    fun showCountryWineBasedOnCountry(view: View) {


        var queue = Volley.newRequestQueue(view.context.applicationContext)


        var url = getSearchWinelistUrl()
        getWines(url, queue, view)

        Log.w("YYYYYYYYYYY", "URL " + url)


    }


}




