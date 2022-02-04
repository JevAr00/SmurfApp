package com.example.summoners.modelos.dataModels.champions

import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName

data class ChampsDataModel(
    @SerializedName("data") var data: JsonObject,
)
