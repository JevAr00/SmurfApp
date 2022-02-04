package com.example.summoners.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    var server: String = "la1"

    val headers = OkHttpClient.Builder().apply {
        addInterceptor(RequestedHeaders())
    }.build()

    fun setServidor(serverRecibido: String) {
        server = serverRecibido
    }

    fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://${server}.api.riotgames.com/lol/")
            .client(headers)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun getRetrofitChamps(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://ddragon.leagueoflegends.com/cdn/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

}