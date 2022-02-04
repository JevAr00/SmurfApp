package com.example.summoners.views.championView

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.summoners.databinding.ItemChampionBinding
import com.squareup.picasso.Picasso

class ChampionViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val binding = ItemChampionBinding.bind(view)

    fun bind(image: String, name: String) {
        Picasso.get().load(image).into(binding.imgChamp)
        binding.lblNombre.text = name
    }

}