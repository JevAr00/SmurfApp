package com.example.summoners.responses

import com.example.summoners.modelos.dataModels.invocador.InvocadorDataModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface InvocadorService {
    @GET("summoner/v4/summoners/by-name/{summonerName}")
    suspend fun byName(
        @Path("summonerName") nombreInvocador: String
    ): Response<InvocadorDataModel>
}