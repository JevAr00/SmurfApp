package com.example.summoners.responses

import com.example.summoners.modelos.dataModels.invocador.InvocadorRankedDataModel
import com.example.summoners.modelos.dataModels.rankeds.rankedListDataModel
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface RankedService {

    @GET("league/v4/entries/by-summoner/{encryptedSummonerId}")
    fun byEncryptedSummonerId(
        @Path("encryptedSummonerId") encryptedSummonerId: String
    ): Call<List<InvocadorRankedDataModel>>? = null

    @GET("league/v4/leagues/{leagueId}")
    suspend fun rankedQueue(
        @Path("leagueId") leagueId: String
    ):Response<rankedListDataModel>
}