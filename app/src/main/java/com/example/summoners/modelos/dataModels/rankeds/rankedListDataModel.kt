package com.example.summoners.modelos.dataModels.rankeds

import com.google.gson.annotations.SerializedName

data class rankedListDataModel(
    @SerializedName("entries") var entries: List<rankedQueueDataModel>
)
