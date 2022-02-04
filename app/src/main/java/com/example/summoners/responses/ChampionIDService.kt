package com.example.summoners.responses

import com.example.summoners.modelos.dataModels.champions.ChampsIDDataModel
import retrofit2.Response
import retrofit2.http.GET

interface ChampionIDService {
    @GET("platform/v3/champion-rotations")
    suspend fun championRotation(): Response<ChampsIDDataModel>
}