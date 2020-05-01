package com.example.vinlogg.Adapters

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.vinlogg.AddYearShelveActivity
import com.example.vinlogg.DeleteActivity
import com.example.vinlogg.DetailVineActivity
import com.example.vinlogg.ModelClassVine.GetUsersWineList
import com.example.vinlogg.ModelClassVine.GetWineListWithUser
import com.example.vinlogg.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail_vine.view.*
import kotlinx.android.synthetic.main.my_wine_list_items.view.*
import kotlinx.android.synthetic.main.search_item_row_recyclerview.view.*


class WineAdapter(var context: Context, var wineList: List<GetUsersWineList>) : RecyclerView.Adapter<WineAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

      var view = LayoutInflater.from(context).inflate(R.layout.my_wine_list_items, parent, false)
        return MyViewHolder(view)



    }

    override fun getItemCount(): Int {
        return wineList.size
    }

    @SuppressLint("RestrictedApi")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        var arrayOfYears = ArrayList<String>()
        var arrayOfAmount = ArrayList<String>()
        var minusBtnDelete = holder.itemView.findViewById<FloatingActionButton>(R.id.myWineListMinusBtn)
        var plusBtAdd = holder.itemView.findViewById<FloatingActionButton>(R.id.myWineListPlusBtn)

        var yearWineListTxt = holder.itemView.findViewById<TextView>(R.id.textViewyearwinelist)
        var amountWineListTxt = holder.itemView.findViewById<TextView>(R.id.txtViewAmountWinelist)
        var imageViewThumbnail = holder.itemView.imageViewList

        val wine = wineList[position]
        holder.setData(wine)

       var imageOrginalThumb= wine.imageThumbnail

        var imageBig= wine.imageBig

        Picasso.get().load("$imageOrginalThumb").resize(800,0).into(imageViewThumbnail)


        var producer = wineList.get(position).producer
        var districtName = wineList.get(position).district?.districtName
        var regionName = wineList.get(position).region?.regionName
        var countryName = wineList.get(position).country?.countryName
        var percent = wineList.get(position).alcohol
        var name = wineList.get(position).wineName
        var wineÍd = wineList.get(position).wineId
        var vintagesList= wineList.get(position).vintages


        var amountAdd = ArrayList<String>()
        var shelvesAdd = ArrayList<String>()
        var year = ArrayList<String>()

        if (vintagesList != null) {
            for (v in vintagesList) {

                amountAdd.add("\n" + v?.amount.toString())
                year.add("\n" + v?.year.toString())
                shelvesAdd.add("\n"+ v.shelfName.toString())
            }


        }

        var grapeName = ArrayList<String>()
        var grapeProcent = ArrayList<String>()

        var grapesList = wineList.get(position).wineGrapes


        if (grapesList != null){

            for (g in grapesList){



                grapeName.add(g?.grapeName.toString())
                grapeProcent.add(g.percent.toString())


            }
        }





        var textShelves = shelvesAdd.toString().replace("[", "").
            replace("]", "").replace(",","")

        var textAmount = amountAdd.toString().replace("[", "").
            replace("]", "").replace(",","")

        var textYear = year.toString().replace("[", "").
            replace("]", "").replace(",","")

        var procentOfGrapes = grapeProcent.toString().replace("[", "").
            replace("]", "").replace(",","")

        holder.itemView.wineListYear.text = textYear
        holder.itemView.wineListAmount.text = textAmount

        if (textYear == "" && textAmount == "") {


            minusBtnDelete?.visibility = View.GONE
            yearWineListTxt.visibility = View.GONE
            amountWineListTxt.visibility = View.GONE
        }


     var nameGrape =   grapeName.toString().replace("[","").replace("]", "")

        holder.itemView.wineListGrapes.text = nameGrape
            holder.itemView.wineListWineName.text = name
        holder.itemView.wineListdistrict.text ="\n" + districtName

     /*   if (percent != null) {
            holder.itemView.wineListAlcohol.text = "Alkohol: "+ percent.toInt().toString()+ " %"
        }*/



        holder.itemView.wineListCountry.text = "\n" + countryName +" >>"
        holder.itemView.wineListRegion.text = "\n" +  regionName + " >>"




        minusBtnDelete.setOnClickListener {

            var intent = Intent(context, DeleteActivity::class.java)
            intent.putExtra("WINE NAME", name)
            intent.putExtra("WINE ID", wineÍd.toString())
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)

        }


        plusBtAdd.setOnClickListener {

            var intent = Intent(context, AddYearShelveActivity::class.java)
            intent.putExtra("WINE ID", wineÍd.toString())
            intent.putExtra("WINE NAME", name.toString())
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent)

        }



        holder.itemView.setOnClickListener {



            var intent = Intent(context, DetailVineActivity::class.java)
            var name = wineList.get(position).wineName
            var wineId = wineList.get(position).wineId
            intent.putExtra("VINE ALCOHOL", percent?.toInt().toString())
            intent.putExtra("VINE IMAGE", imageBig.toString())
            intent.putExtra("VINE DISTRICT", districtName)
            intent.putExtra("VINE ID", wineId.toString())
            intent.putExtra("VINE NAME", name)
            intent.putExtra("WINE COUNTRY", countryName)
            intent.putExtra("WINE REGION", regionName)
            intent.putExtra("WINE PRODUCER", producer)
            intent.putExtra("WINE GRAPES", nameGrape)
            intent.putExtra("WINE GRAPES PROCENT", procentOfGrapes)
            intent.putExtra("WINE YEAR", textYear)
            intent.putExtra("WINE AMOUNT",textAmount)
            intent.putExtra("WINE SHELVE",textShelves)
            intent.putExtra("WINE GRAPES", nameGrape)
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);


            context.startActivity(intent)


        }
    }

   inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

       fun setData(wineClassUser: GetUsersWineList ){

       }



    }
}