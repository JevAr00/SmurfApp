package com.example.summoners.modelos.dataModels.champions

import com.google.gson.annotations.SerializedName

data class ChampsIDDataModel(
    @SerializedName("freeChampionIds") var ids: List<Number>
)
