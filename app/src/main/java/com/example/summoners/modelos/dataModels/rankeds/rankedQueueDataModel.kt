package com.example.summoners.modelos.dataModels.rankeds

import com.google.gson.annotations.SerializedName

data class rankedQueueDataModel(
    @SerializedName("summonerId") var summonerId: String,
    @SerializedName("summonerName") var summonerName: String,
    @SerializedName("leaguePoints") var leaguePoints: Number,
    @SerializedName("rank") var rank: String,
    @SerializedName("wins") var wins: Number,
    @SerializedName("losses") var losses: Number,
    @SerializedName("veteran") var veteran: Boolean,
    @SerializedName("inactive") var inactive: Boolean,
    @SerializedName("freshBlood") var freshBlood: Boolean,
    @SerializedName("hotStreak") var hotStreak: Boolean,
)