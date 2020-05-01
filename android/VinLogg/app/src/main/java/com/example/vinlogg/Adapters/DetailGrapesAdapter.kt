package com.example.vinlogg.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.vinlogg.ModelClassVine.GetVintagesClassList
import com.example.vinlogg.ModelClassVine.VintagesItem
import com.example.vinlogg.ModelClassVine.WineGrapesItem
import com.example.vinlogg.R

class DetailGrapesAdapter(
    var context: Context,
    var grapesList: List<WineGrapesItem>
) : RecyclerView.Adapter<DetailGrapesAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        var view = LayoutInflater.from(context)
            .inflate(R.layout.detail_grapes_row_items, parent, false)
        return MyViewHolder(view)

    }

    override fun getItemCount(): Int {
        return grapesList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {




        var nameGrape = grapesList.get(position).grapeName
        var percentGrape =  grapesList.get(position).percent.toString()



        var grapeName =  holder.itemView.findViewById<TextView>(R.id.textViewSgrapeNamDetail)
        var precent = holder.itemView.findViewById<TextView>(R.id.textViewGrapePercentDetail)



        grapeName.text = nameGrape
        precent.text = percentGrape




        holder.itemView.setOnClickListener {

        }
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun setData(grapesList: WineGrapesItem) {


        }
    }
}


