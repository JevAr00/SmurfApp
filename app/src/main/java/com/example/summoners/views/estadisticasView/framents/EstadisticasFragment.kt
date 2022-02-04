package com.example.summoners.views.estadisticasView.framents

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.children
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.example.summoners.R
import com.example.summoners.databinding.FragmentEstadisticasBinding
import com.example.summoners.modelos.InvocadorModel
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlin.math.roundToInt

class EstadisticasFragment: Fragment() {

    private var _binding: FragmentEstadisticasBinding? = null
    private  val binding get() = _binding!!

    val invocadorModel = InvocadorModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEstadisticasBinding.inflate(inflater , container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        CoroutineScope(IO).launch {
            delay(100)
            activity?.runOnUiThread {
                showInfo()
            }
        }
    }

    //region showInformacion
    private fun showInfo() {
        binding.lblNombreInvocador.text = invocadorModel.summonerName
        binding.lblNivel.text = "Lvl: ${invocadorModel.summonerLevel}"
        invocadorModel.iconoPerfil(binding)
        invocadorModel.emblemaRanked(binding)
        if(invocadorModel.emblemaRanked(binding) != "Unranked"){
            binding.winsNumero.text = invocadorModel.wins.toString()
            binding.lossesNumero.text = invocadorModel.losses.toString()
            binding.lblWins.text = "Victorias"
            binding.lblLosses.text = "Derrotas"
            binding.lblPorcentaje.text = "Porcentaje de victoria"
            setupPieChart()
            loadData()
        }else{
            binding.lblNodatos1.text = "¡¿Que ha pasado?!"
            binding.imgNodatos.setImageResource(R.drawable.nodata_emote)
            binding.lblNodatos.text = "Parece que este invocador\nno se encuentra clasificado"
        }
    }
    //endregion

    //region grafico
    private fun loadData(){
        val entries = ArrayList<PieEntry>()
        entries.add(PieEntry(invocadorModel.losses!!.toFloat(),"D"))
        entries.add(PieEntry(invocadorModel.wins!!.toFloat(),"V"))

        val colors = ArrayList<Int>()
        colors.add(Color.parseColor("#E31638"))
        colors.add(Color.parseColor("#20CDD2"))

        val pieDataSet = PieDataSet(entries,"")
        pieDataSet.colors = colors
        pieDataSet.sliceSpace = 2f

        val data = PieData(pieDataSet)
        data.setDrawValues(false)
        data.setValueTextSize(20f)
        data.setValueTextColor(Color.BLACK)
        binding.grafico.data = data
        binding.grafico.invalidate()
    }

    private fun setupPieChart(){
        binding.grafico.isVisible = true
        binding.grafico.legend.isEnabled = false
        binding.grafico.description.isEnabled = false
        binding.grafico.isRotationEnabled = false
        binding.grafico.setDrawCenterText(true)
        binding.grafico.setDrawEntryLabels(true)
        binding.grafico.setTouchEnabled(false)
        binding.grafico.centerText = "${calcular()}%"
        binding.grafico.setCenterTextSize(25f)
    }

    private fun calcular(): Int {
        val sum: Double  = invocadorModel.wins!!.toDouble() + invocadorModel.losses!!.toDouble()
        var div: Double = 100 / sum
        return (div * invocadorModel.wins!!.toInt()).roundToInt()
    }
    //endregion
}