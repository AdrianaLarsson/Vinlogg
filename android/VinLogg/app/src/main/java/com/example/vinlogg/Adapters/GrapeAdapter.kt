package com.example.vinlogg.Adapters

import android.content.Context
import android.graphics.Color
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.vinlogg.ModelClassVine.GetGrapes
import com.example.vinlogg.ModelClassVine.WineGrapesPostClass
import com.example.vinlogg.R


var procentFromU : String? = null
var result= ArrayList<WineGrapesPostClass>()
class GrapeAdapter(var context: Context, var grapelist: List<GetGrapes>,var  itemClickListener: onGrapeItemClickListener) : RecyclerView.Adapter<GrapeAdapter.MyViewHolder>() {

    var rowIndex = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {


        var view = LayoutInflater.from(context).inflate(R.layout.grapes_items_row, parent, false)
        return MyViewHolder(view)


    }

    override fun getItemCount(): Int {
        Log.w("grapelistsize", "grapelist.size " + grapelist.size)
        return grapelist.size


    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        var grapeN = holder.itemView.findViewById<TextView>(R.id.grapeName)







       var nameGrape = grapelist.get(position).grapeName
        grapeN.text = nameGrape

        val grapes = grapelist[position]


        holder.itemView.findViewById<CardView>(R.id.cardVG).setOnClickListener {

            holder.setData(grapelist,itemClickListener)
            rowIndex = position
            notifyDataSetChanged()

        }

        if (rowIndex == position){
            holder.itemView.findViewById<CardView>(R.id.cardVG).setBackgroundColor(Color.parseColor("#FFF6ED"))
            

        }else{

            holder.itemView.findViewById<CardView>(R.id.cardVG).setBackgroundColor(Color.parseColor("#FFFFFFFF"))
        }


    }

     class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

         fun setData(grape: List<GetGrapes>, action: onGrapeItemClickListener) {


             action.onItemGrapeClick(grape, adapterPosition )

             itemView.setOnClickListener {



             }


         }

     }


}


interface onGrapeItemClickListener{

    fun onItemGrapeClick(itemGrape :List <GetGrapes>, position: Int){



    }
}