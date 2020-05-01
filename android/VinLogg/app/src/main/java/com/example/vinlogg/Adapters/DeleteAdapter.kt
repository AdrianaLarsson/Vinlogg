package com.example.vinlogg.Adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.vinlogg.ModelClassVine.VintagesItem
import com.example.vinlogg.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.items_delete_row.view.*

class DeleteAdapter(var context: Context, var vintagesList: List<VintagesItem>, var clickListener: onDeleteClickListener) : RecyclerView.Adapter<DeleteAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        var view = LayoutInflater.from(context).inflate(R.layout.items_delete_row, parent, false)
        return MyViewHolder(view)

    }

    override fun getItemCount(): Int {


        return vintagesList.size
    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {


        var wineListUser = vintagesList?.get(position)


        var year = wineListUser?.year.toString()
        var amount = wineListUser?.amount.toString()
        var shelveName = wineListUser?.shelfName.toString()

        // var year = getUsersWineList.get(position)?
        // var amonut = getUsersWineList.get(position)?


        //  var shelveName = getUsersWineList.get(position)?.shelfName

        holder.itemView.textViewDeleteShelve.text = shelveName
        holder.itemView.textViewAmount.text = amount
        holder.itemView.textViewDeleteYear.text = year

        holder.itemView.delteFloatingActionButton.setOnClickListener {



            vintagesList?.let { it1 -> holder.setData(it1, clickListener) }
        }


    }


    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun setData(vintagesList: List<VintagesItem>, action: onDeleteClickListener) {


            var btn: FloatingActionButton = itemView.findViewById(R.id.delteFloatingActionButton)

            btn.setOnClickListener {
                action.onItemDeleteClick(vintagesList, adapterPosition)


            }


        }

    }

}




interface onDeleteClickListener{

   fun onItemDeleteClick(vintagesList: List<VintagesItem>, position: Int)
}