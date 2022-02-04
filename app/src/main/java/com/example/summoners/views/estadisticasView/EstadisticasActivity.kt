package com.example.summoners.views.estadisticasView

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.summoners.R
import com.example.summoners.api.RetrofitInstance
import com.example.summoners.databinding.ActivityEstadisticasBinding
import com.example.summoners.modelos.InvocadorModel
import com.example.summoners.modelos.dataModels.invocador.InvocadorRankedDataModel
import com.example.summoners.responses.RankedService
import com.example.summoners.views.estadisticasView.framents.EstadisticasFragment
import com.example.summoners.views.estadisticasView.framents.QueueFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class EstadisticasActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEstadisticasBinding

    val invocadorModel = InvocadorModel
    private val retrofit = RetrofitInstance

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEstadisticasBinding.inflate(layoutInflater)
        setContentView(binding.root)

        searchByEncriptedSummonerId(InvocadorModel.id!!)
    }

    //region llamadoFragmentos
    private fun setCurrentFragment(Fragment: Fragment) = supportFragmentManager.beginTransaction().apply {
        replace(R.id.contenedorFragmentos, Fragment)
        commit()
    }

    private fun callFragments() {
        CoroutineScope(IO).launch {
            val estadisticasFragment = EstadisticasFragment()
            val queueFragment = QueueFragment()
            delay(550)

            setCurrentFragment(estadisticasFragment)

            runOnUiThread {
                if (invocadorModel.tier != null) {
                    binding.navOpciones.menu.findItem(R.id.itemRanked).isVisible = true
                    binding.navOpciones.animation
                }
            }

            binding.navOpciones.setOnItemSelectedListener {
                when (it.itemId) {
                    R.id.itemEstadisticas -> setCurrentFragment(estadisticasFragment)
                    R.id.itemRanked -> setCurrentFragment(queueFragment)
                }
                true
            }
        }
    }
    //endregion

    //region datosInvocadorRaneked
    private fun searchByEncriptedSummonerId(encrySummId: String) {
        CoroutineScope(IO).launch {
            try {
                val call = retrofit.getRetrofit()
                    .create(RankedService::class.java)
                    .byEncryptedSummonerId(encrySummId)

                call?.enqueue(object : Callback<List<InvocadorRankedDataModel>> {
                    override fun onResponse(
                        call: Call<List<InvocadorRankedDataModel>>,
                        response: Response<List<InvocadorRankedDataModel>>
                    ) {
                        val rankeds = response.body()
                        if (rankeds.toString() != "[]") {
                            if (rankeds?.size!! >= 1) {
                                invocadorModel.setValuesRanked(rankeds[invocadorModel.selectMainQueue(rankeds)])
                            } else {
                                invocadorModel.setValuesRanked(rankeds[0])
                            }
                        }
                        callFragments()
                    }

                    override fun onFailure(call: Call<List<InvocadorRankedDataModel>>, t: Throwable) {
                        Log.e("EstadisticasActivity.searchByEncriptedSummonerId.onResponse error", t.message, t)
                        runOnUiThread {
                            onBackPressed()
                        }
                    }
                })

            } catch (e: Exception) {
                Log.e("EstaditicasActivity", "EstadisticasActivity.searchByEncriptedSummonerId error\n\n${e}")
            }
        }
    }
    //endregion
}