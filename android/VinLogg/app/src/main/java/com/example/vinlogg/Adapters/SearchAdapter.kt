package com.example.vinlogg.Adapters

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.vinlogg.AddYearShelveActivity
import com.example.vinlogg.DeleteActivity
import com.example.vinlogg.DetailVineActivity
import com.example.vinlogg.ModelClassVine.GetAllWineListSearchWith
import com.example.vinlogg.ModelClassVine.VineClass
import com.example.vinlogg.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.search_item_row_recyclerview.view.*
import java.util.*
import java.util.Arrays.asList
import kotlin.collections.ArrayList


class SearchAdapter(
    var context: Context,
    var getAllWineListSearchWith: List<GetAllWineListSearchWith>
) : RecyclerView.Adapter<SearchAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        var view = LayoutInflater.from(context)
            .inflate(R.layout.search_item_row_recyclerview, parent, false)
        return MyViewHolder(view)

    }

    override fun getItemCount(): Int {
        return getAllWineListSearchWith.size
    }


    @RequiresApi(Build.VERSION_CODES.N)
    @SuppressLint("RestrictedApi")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        var txtVYear = holder.itemView.findViewById<TextView>(R.id.textViewyearSearch)
        var txtVAmount = holder.itemView.findViewById<TextView>(R.id.textViewAmountSearch)
        var imageV = holder.itemView.findViewById<ImageView>(R.id.SeacrhimageView)
        var addBtn: FloatingActionButton? =
            holder.itemView.findViewById(R.id.floatingActionButtonPlus)
        var minusBtn: FloatingActionButton? =
            holder.itemView.findViewById(R.id.floatingActionButtonMinus)

        var imageOrginal = getAllWineListSearchWith.get(position).imageThumbnail
        var imageBig = getAllWineListSearchWith.get(position).imageBig

        var vines = VineClass().image
        if (imageOrginal != null) {

            Picasso.get().load("${imageOrginal}").into(imageV)
        } else {

            imageV.setImageResource(vines)
        }

        var grapeName = ArrayList<String>()
        var grapeProcent = ArrayList<String>()
        Log.w("imageOrginal", "imageOrginal" + imageOrginal)
        var grapesList = getAllWineListSearchWith.get(position).wineGrapes


        if (grapesList != null){

            for (g in grapesList){



                grapeName.add(g?.grapeName.toString())
                if (g != null) {
                    grapeProcent.add(g.percent.toString())
                }
            }
        }


        var vintagesList= getAllWineListSearchWith.get(position).vintages




            var amountAdd = ArrayList<String>()
        var shelvesAdd = ArrayList<String>()
            var year = ArrayList<String>(
            )

        var amonut1 = ArrayList<Int>()



        if (vintagesList != null) {
            for (v in vintagesList) {

             amountAdd.add("\n" + v?.amount.toString())
                year.add("\n" + v?.year.toString())

                v?.amount?.let { amonut1.add(it) }
                if (v != null) {
                    shelvesAdd.add(v.shelfName.toString())
                }
            }


        }


     if (amonut1.isEmpty()){

         minusBtn?.visibility = View.GONE


     }else {


         minusBtn?.visibility = View.VISIBLE

     }


        var textAmount = amountAdd.toString().replace("[", "").
                replace("]", "").replace(",","")

        var textYear = year.toString().replace("[", "").
                replace("]", "").replace(",","")

        var textShelves = shelvesAdd.toString().replace("[", "").
            replace("]", "").replace(",","")

        var textGrapeProcent = grapeProcent.toString().replace("[", "").
            replace("]", "").replace(",","")


        holder.itemView.yearSearch.text = textYear
        holder.itemView.amountSearch.text = textAmount
        if (textYear == "" && textAmount == "" ){


            minusBtn?.visibility = View.GONE
            txtVAmount.visibility = View.GONE
            txtVYear.visibility = View.GONE

        }else{

            minusBtn?.visibility = View.VISIBLE

        }

        var nameGrape =   grapeName.toString().replace("[","").replace("]", "")


        val vine = getAllWineListSearchWith[position]

        holder.setData(vine)

        var producer = getAllWineListSearchWith.get(position).producer
        var name = getAllWineListSearchWith.get(position).wineName
        var country = getAllWineListSearchWith.get(position).country?.countryName
        var region = getAllWineListSearchWith.get(position).region?.regionName
        var district = getAllWineListSearchWith.get(position).district?.districtName
        var wineId = getAllWineListSearchWith.get(position).wineId
        var percent = getAllWineListSearchWith.get(position).alcohol
        holder.itemView.nameWineSearchSide.text = name
        holder.itemView.searechSideCountry.text = "\n" + country + " >> "
        holder.itemView.searchSideRegion.text = "\n" + region + " >> "
        holder.itemView.searchSideDistrict.text = "\n" + district
        holder.itemView.searchGrapeSide.text = nameGrape



        addBtn?.setOnClickListener {

            var intent = Intent(context, AddYearShelveActivity::class.java)
            intent.putExtra("WINE ID", wineId.toString())
            intent.putExtra("WINE NAME", name.toString())
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);


            context.startActivity(intent)


        }


        minusBtn?.setOnClickListener {

            var intent = Intent(context, DeleteActivity::class.java)

            intent.putExtra("WINE NAME", name)
            intent.putExtra("WINE ID", wineId.toString())
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);


            context.startActivity(intent)


        }

        holder.itemView.setOnClickListener {

            var intent = Intent(context, DetailVineActivity::class.java)
            var name = getAllWineListSearchWith.get(position).wineName
            var wineId = getAllWineListSearchWith.get(position).wineId
            intent.putExtra("VINE ALCOHOL", percent?.toInt().toString())
            intent.putExtra("WINE COUNTRY", country)
            intent.putExtra("WINE REGION", region)
            intent.putExtra("VINE IMAGE", imageBig.toString())
            intent.putExtra("VINE DISTRICT", district)
            intent.putExtra("VINE ID", wineId.toString())
            intent.putExtra("VINE NAME", name)
            intent.putExtra("WINE PRODUCER", producer)
            intent.putExtra("WINE YEAR", textYear)
            intent.putExtra("WINE AMOUNT",textAmount)
            intent.putExtra("WINE SHELVE",textShelves)
            intent.putExtra("WINE GRAPES",nameGrape)
            intent.putExtra("WINE GRAPES PROCENT",textGrapeProcent)
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);


            context.startActivity(intent)


        }
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun setData(getAllWineListSearchWith: GetAllWineListSearchWith) {
            itemView.nameWineSearchSide.text = getAllWineListSearchWith.wineName

        }
    }
}


