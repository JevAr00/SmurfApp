package com.example.summoners.modelos.dataModels.invocador

import com.google.gson.annotations.SerializedName

data class InvocadorRankedDataModel(
    @SerializedName("leagueId") var leagueId: String,
    @SerializedName("queueType") var queueType: String,
    @SerializedName("tier") var tier: String,
    @SerializedName("rank") var rank: String,
    @SerializedName("summonerId") var summonerId: String,
    @SerializedName("summonerName") var summonerName: String,
    @SerializedName("leaguePoints") var leaguePoints: Number,
    @SerializedName("wins") var wins: Number,
    @SerializedName("losses") var losses: Number,
    @SerializedName("veteran") var veteran: Boolean,
    @SerializedName("inactive") var inactive: Boolean,
    @SerializedName("freshBlood") var freshBlood: Boolean,
    @SerializedName("hotStreak") var hotStreak: Boolean,
)
