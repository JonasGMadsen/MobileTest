package com.example.mobiletry.models

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mobiletry.R

class BeerAdapter(private val onClick: (Beer) -> Unit) : RecyclerView.Adapter<BeerAdapter.BeerViewHolder>() {

    private var beers: List<Beer> = emptyList()

    fun setBeers(beers: List<Beer>) {
        this.beers = beers
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BeerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_beer, parent, false)
        return BeerViewHolder(view, onClick)
    }

    override fun onBindViewHolder(holder: BeerViewHolder, position: Int) {
        holder.bind(beers[position])
    }

    override fun getItemCount(): Int = beers.size

    class BeerViewHolder(itemView: View, val onClick: (Beer) -> Unit) : RecyclerView.ViewHolder(itemView) {
        private val beerNameTextView: TextView = itemView.findViewById(R.id.beerNameTextView)
        private val breweryNameTextView: TextView = itemView.findViewById(R.id.breweryNameTextView)


        fun bind(beer: Beer) {
            beerNameTextView.text = beer.name
            breweryNameTextView.text = beer.brewery
            itemView.setOnClickListener {
                onClick(beer)
            }
        }





    }
}