package com.example.summoners.views.championView

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.summoners.R

class ChampsAdapter(private val images: List<String>, private val names: List<String>) :
    RecyclerView.Adapter<ChampionViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChampionViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ChampionViewHolder(layoutInflater.inflate(R.layout.item_champion, parent, false))
    }

    override fun onBindViewHolder(holder: ChampionViewHolder, position: Int) {
        val img = images[position]
        val name = names[position]
        holder.bind(img, name)
    }

    override fun getItemCount(): Int = images.size

}