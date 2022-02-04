package com.example.summoners.views.estadisticasView.framents

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.summoners.api.RetrofitInstance
import com.example.summoners.databinding.FragmentQueueBinding
import com.example.summoners.modelos.InvocadorModel
import com.example.summoners.modelos.dataModels.rankeds.rankedQueueDataModel
import com.example.summoners.responses.RankedService
import com.example.summoners.views.estadisticasView.QueueAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class QueueFragment: Fragment() {

    private var _binding: FragmentQueueBinding? = null
    private  val binding get() = _binding!!

    private lateinit var adapter: QueueAdapter
    private val summonerInfo = mutableListOf<rankedQueueDataModel>()

    val invocadorModel = InvocadorModel
    private val retrofit = RetrofitInstance

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentQueueBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initReciclerView()
        colaRanked()
    }

    private fun initReciclerView(){
        adapter = QueueAdapter(summonerInfo)
        binding.recyclerQueue.layoutManager = LinearLayoutManager(context)
        binding.recyclerQueue.adapter = adapter
    }

    private fun colaRanked(){
        CoroutineScope(IO).launch {
            try {
                val call = retrofit.getRetrofit().create(RankedService::class.java).rankedQueue(invocadorModel.leagueId.toString())
                if (call.isSuccessful){
                    val resposeInvocador = call.body()
                    val datoInvcador = resposeInvocador!!.entries

                    val summonerList = mutableListOf<rankedQueueDataModel>()

                    for (i in 0 until datoInvcador.count()){
                        if(invocadorModel.rank == datoInvcador[i].rank){
                            val summoner = rankedQueueDataModel("",
                                datoInvcador[i].summonerName,
                                datoInvcador[i].leaguePoints,
                                "",0,0,
                                veteran = false, inactive = false, freshBlood = false, hotStreak = false
                            )
                            summonerList.add(summoner)
                        }
                    }
                    summonerList.sortByDescending { it.leaguePoints.toInt() }

                    activity?.runOnUiThread {
                        binding.lblClasificacion.text = invocadorModel.tier
                        binding.lblRango.text = invocadorModel.rank
                        binding.lblPuntos.text = invocadorModel.leaguePoints.toString()
                        binding.lblLP.text = "LP"
                        summonerInfo.clear()
                        summonerInfo.addAll(summonerList)
                        adapter.notifyDataSetChanged()
                    }
                }

            } catch (e: Exception) {
                Log.e("QueueFragment", "QueueFragment.colaRanked error\n\n${e}")
                activity?.runOnUiThread {

                }
            }
        }
    }

}