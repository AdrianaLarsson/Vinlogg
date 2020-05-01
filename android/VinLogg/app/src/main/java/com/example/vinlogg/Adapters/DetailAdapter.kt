package com.example.vinlogg.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.vinlogg.ModelClassVine.GetUsersWineList
import com.example.vinlogg.ModelClassVine.GetVintageClass
import com.example.vinlogg.ModelClassVine.GetVintagesClassList
import com.example.vinlogg.ModelClassVine.VintagesItem
import com.example.vinlogg.R

class DetailAdapter(
    var context: Context,
    var vintagesDetails: List<VintagesItem>
) : RecyclerView.Adapter<DetailAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        var view = LayoutInflater.from(context)
            .inflate(R.layout.vintages_row_detial_items, parent, false)
        return MyViewHolder(view)

    }

    override fun getItemCount(): Int {
        return vintagesDetails.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {




       var yearDetail = vintagesDetails.get(position).year.toString()
       var shelveName =  vintagesDetails.get(position).shelfName
       var amountDetail=  vintagesDetails.get(position).amount.toString()


        var amount =  holder.itemView.findViewById<TextView>(R.id.textViewAmountDetail)
        var year = holder.itemView.findViewById<TextView>(R.id.textViewYearDetail)
        var shelve =holder.itemView.findViewById<TextView>(R.id.textViewShelveDetail)


        amount.text = amountDetail
        year.text = yearDetail
        shelve.text = shelveName



        holder.itemView.setOnClickListener {

        }
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun setData(vintagesDetails: GetVintagesClassList) {


        }
    }
}


