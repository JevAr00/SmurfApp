package com.example.summoners.views.estadisticasView

import android.graphics.Color
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.summoners.databinding.ItemQueueBinding
import com.example.summoners.modelos.InvocadorModel
import com.example.summoners.modelos.dataModels.rankeds.rankedQueueDataModel

class QueueViewHolder(view:View): RecyclerView.ViewHolder(view) {

    private val binding = ItemQueueBinding.bind(view)
    var invocadorModel = InvocadorModel

    fun bind(invocador: rankedQueueDataModel){
        if(invocadorModel.summonerName == invocador.summonerName){
            binding.lblNombre.text = invocador.summonerName
            binding.lblNombre.setTextColor(Color.parseColor("#1699E3"))
            binding.lblPuntos.text = invocador.leaguePoints.toString()
        }else {
            binding.lblNombre.text = invocador.summonerName
            binding.lblNombre.setTextColor(Color.BLACK)
            binding.lblPuntos.text = invocador.leaguePoints.toString()
        }
    }
}