package com.example.summoners.modelos

import com.example.summoners.R
import com.example.summoners.databinding.FragmentEstadisticasBinding
import com.example.summoners.modelos.dataModels.invocador.InvocadorDataModel
import com.example.summoners.modelos.dataModels.invocador.InvocadorRankedDataModel
import com.squareup.picasso.Picasso


object InvocadorModel {

    //Informacion general
    var id: String? = null
    var accoundId: String? = null
    var puuid: String? = null
    var summonerName: String? = null
    var profileIcon: Number? = null
    var revisionDate: Number? = null
    var summonerLevel: Int? = null

    //Informacion ranked
    var leagueId: String? = null
    var queueType: String? = null
    var tier: String? = null
    var rank: String? = null
    var leaguePoints: Number? = null
    var wins: Number? = 0
    var losses: Number? = 0
    var veteran: Boolean = false
    var inactive: Boolean = false
    var freshBlood: Boolean = false
    var hotStreak: Boolean = false

    fun clearAll(){
        id = null
        accoundId = null
        puuid = null
        summonerName = null
        profileIcon = null
        revisionDate = null
        summonerLevel = null
        leagueId = null
        queueType = null
        tier = null
        rank = null
        leaguePoints = null
        wins = 0
        losses = 0
        veteran = false
        inactive = false
        freshBlood = false
        hotStreak = false
    }

    fun setValuesInvocador(invocador: InvocadorDataModel?) {
        id = invocador?.id
        accoundId = invocador?.accountId
        puuid = invocador?.puuid
        summonerName = invocador?.name
        profileIcon = invocador?.profileIconId
        revisionDate = invocador?.revisionDate
        summonerLevel = invocador?.summonerLevel
    }

    fun setValuesRanked(invocador: InvocadorRankedDataModel) {
        leagueId = invocador.leagueId
        queueType = invocador.queueType
        tier = invocador.tier
        rank = invocador.rank
        leaguePoints = invocador.leaguePoints
        wins = invocador.wins
        losses = invocador.losses
        veteran = invocador.veteran
        inactive = invocador.inactive
        freshBlood = invocador.freshBlood
        hotStreak = invocador.hotStreak
    }

    fun selectMainQueue(colas: List<InvocadorRankedDataModel>): Int{
        var mayor = mutableListOf<Int>()
        for (i in 0 until colas.count()){
            when (colas[i].tier?.lowercase()) {
                "bronze" -> {
                    mayor.add(i,1)
                }
                "silver" -> {
                    mayor.add(i,2)
                }
                "gold" -> {
                    mayor.add(i,3)
                }
                "platinum" -> {
                    mayor.add(i,4)
                }
                "diamond" -> {
                    mayor.add(i,5)
                }
                "master" -> {
                    mayor.add(i,6)
                }
                "grandmaster" -> {
                    mayor.add(i,7)
                }
                "challenger" -> {
                    mayor.add(i,8)
                }
            }
        }
        return if(mayor[0] > mayor[1]){
            0
        }else{
            1
        }

    }

    //region ImagenesSuperior

    fun iconoPerfil(binding: FragmentEstadisticasBinding) {
        Picasso.get().load("https://ddragon.leagueoflegends.com/cdn/11.20.1/img/profileicon/${profileIcon}.png")
            .into(binding.iconoInvocador)
    }

    fun emblemaRanked(binding: FragmentEstadisticasBinding):String {
        var img = R.drawable.emblem_unranked
        var nombreDivision = "Unranked"
        if (tier != null) {
            when (tier?.lowercase()) {
                "bronze" -> {
                    img = R.drawable.emblem_bronze
                    nombreDivision = "Bronce"
                }
                "silver" -> {
                    img = R.drawable.emblem_silver
                    nombreDivision = "Plata"
                }
                "gold" -> {
                    img = R.drawable.emblem_gold
                    nombreDivision = "Oro"
                }
                "platinum" -> {
                    img = R.drawable.emblem_platinum
                    nombreDivision = "Platino"
                }
                "diamond" -> {
                    img = R.drawable.emblem_diamond
                    nombreDivision = "Diamante"
                }
                "master" -> {
                    img = R.drawable.emblem_master
                    nombreDivision = "Master"
                }
                "grandmaster" -> {
                    img = R.drawable.emblem_grandmaster
                    nombreDivision = "Gran Master"
                }
                "challenger" -> {
                    img = R.drawable.emblem_challenger
                    nombreDivision = "Challenger"
                }
            }
        }
        Picasso.get().load(img).into(binding.emblemaRanked)
        binding.lblNombreEmblema.text = nombreDivision
        return nombreDivision
    }

    //endregion
}