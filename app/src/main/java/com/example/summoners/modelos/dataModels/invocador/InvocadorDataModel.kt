package com.example.summoners.modelos.dataModels.invocador

import com.google.gson.annotations.SerializedName

data class InvocadorDataModel(
    @SerializedName("id") var id: String,
    @SerializedName("accountId") var accountId: String,
    @SerializedName("puuid") var puuid: String,
    @SerializedName("name") var name: String,
    @SerializedName("profileIconId") var profileIconId: Number,
    @SerializedName("revisionDate") var revisionDate: Number,
    @SerializedName("summonerLevel") var summonerLevel: Int
)