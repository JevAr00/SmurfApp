package com.example.summoners.responses

import com.example.summoners.modelos.dataModels.champions.ChampsDataModel
import retrofit2.Response
import retrofit2.http.GET

interface ChampionService {
    @GET("11.23.1/data/en_US/champion.json")
    suspend fun getRotation(): Response<ChampsDataModel>?
}