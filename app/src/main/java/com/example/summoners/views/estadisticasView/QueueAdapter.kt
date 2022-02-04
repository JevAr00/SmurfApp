package com.example.summoners.views.estadisticasView

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.summoners.R
import com.example.summoners.modelos.dataModels.rankeds.rankedQueueDataModel

class QueueAdapter(val queue: List<rankedQueueDataModel>): RecyclerView.Adapter<QueueViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QueueViewHolder {
        val layaoutInflater = LayoutInflater.from(parent.context)
        return QueueViewHolder(layaoutInflater.inflate(R.layout.item_queue,parent,false))
    }

    override fun onBindViewHolder(holder: QueueViewHolder, position: Int) {
        holder.bind(queue[position])
    }

    override fun getItemCount(): Int = queue.size
}