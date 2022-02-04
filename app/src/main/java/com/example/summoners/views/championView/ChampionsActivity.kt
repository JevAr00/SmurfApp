package com.example.summoners.views.championView

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.summoners.api.RetrofitInstance
import com.example.summoners.databinding.ActivityChampionsBinding
import com.example.summoners.responses.ChampionIDService
import com.example.summoners.responses.ChampionService
import com.google.gson.JsonObject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class ChampionsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChampionsBinding
    private lateinit var adapter: ChampsAdapter

    private val retrofit = RetrofitInstance

    private lateinit var champIDs: List<String>
    private val champImages = mutableListOf<String>()
    private val champNombres = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChampionsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        champIDs = listOf()

        initReciclerView()
        getChampionRotationsID()

    }

    //region MetodosBase
    private fun initReciclerView() {
        adapter = ChampsAdapter(champImages, champNombres)
        binding.recyclerChampions.layoutManager = LinearLayoutManager(this)
        binding.recyclerChampions.adapter = adapter
    }
    //endregion

    //region EstaSemanaIDs
    //obtengo los ids
    private fun getChampionRotationsID() {
        CoroutineScope(IO).launch {
            try {
                val call = retrofit.getRetrofit().create(ChampionIDService::class.java).championRotation()
                val responseIDs = call.body()

                if (call.isSuccessful) {
                    champIDs = responseIDs?.ids.toString()
                        .substringAfter("[")
                        .substringBefore("]")
                        .split(", ")
                    getChampsfromID()
                } else {
                    runOnUiThread {
                        Toast.makeText(
                            this@ChampionsActivity,
                            "Error al recuperar los IDs verifique la API y APIKey",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

            } catch (e: Exception) {
                Log.e("ChampionsActivity", "ChampionsActivity.getChampionsRotationsID error\n\n${e}")
            }
        }
    }
    //endregion

    //region ChampsInformacion
    //con los ids que tengo busco en el json de la version toda la info
    private fun getChampsfromID() {
        CoroutineScope(IO).launch {
            try {
                val call = retrofit.getRetrofitChamps().create(ChampionService::class.java).getRotation()
                val response = call?.body()

                if (call!!.isSuccessful) {
                    val champs = response!!.data

                    val champsNames: List<String> = champs.keySet().toString()
                        .substringAfter('[')
                        .substringBefore(']')
                        .split(',')

                    var champInfo: JsonObject
                    val champImg = mutableListOf<String>()
                    val champName = mutableListOf<String>()

                    for (i in 0 until champsNames.count()) {
                        val champ = champs.get(champsNames[i].trim())
                        champInfo = champ.asJsonObject

                        for (j in 0 until champIDs.count()) {
                            if (champInfo.get("key").asString == champIDs[j]) {
                                champImg.add(
                                    "https://ddragon.leagueoflegends.com/cdn/img/champion/splash/${
                                        champInfo.get(
                                            "id"
                                        ).asString
                                    }_0.jpg"
                                )
                                champName.add(champInfo.get("name").asString)
                                break
                            }
                        }
                    }

                    runOnUiThread {
                        champImages.clear()
                        champImages.addAll(champImg)
                        champNombres.addAll(champName)
                        adapter.notifyDataSetChanged()
                    }
                } else {
                    runOnUiThread {
                        Toast.makeText(
                            this@ChampionsActivity,
                            "Error al cargar imagenes en el recyclerView",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

            } catch (e: Exception) {
                Log.e("ChampionsActivity", "ChampionsActivity.getChampsfromID error\n\n${e}")
            }
        }
    }
    //endregion

}