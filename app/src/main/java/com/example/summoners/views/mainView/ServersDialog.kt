package com.example.summoners.views.mainView

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.fragment.app.DialogFragment
import com.example.summoners.R
import com.example.summoners.databinding.ActivityMainBinding

class ServersDialog(binding: ActivityMainBinding) : DialogFragment() {

    var serverSeleccionado: String = "la1"
    private var mainBinding: ActivityMainBinding = binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView: View = inflater.inflate(R.layout.fragment_dialog_servers, container, false)
        rootView.findViewById<RadioButton>(R.id.rbnLan).isChecked = true

        rootView.findViewById<View>(R.id.submitButton).setOnClickListener {
            val selectedId = rootView.findViewById<RadioGroup>(R.id.serverRadioGroup).checkedRadioButtonId
            val radio = rootView.findViewById<RadioButton>(selectedId)

            selectServidor(radio.text.toString())
            setNombreServidor(mainBinding)

            dismiss()
        }
        return rootView
    }

    //region seleccionServers

    //Para poner en la BaseUrl de la api
    private fun selectServidor(server: String) {
        val inicialesServer: List<String> = server.split(":").map { it.trim() }
        when (inicialesServer[0]) {
            "LAN" -> serverSeleccionado = "la1"
            "LAS" -> serverSeleccionado = "la2"
            "NA" -> serverSeleccionado = "na1"
            "EUW" -> serverSeleccionado = "euw1"
            "EUN" -> serverSeleccionado = "eun1"
            "BR" -> serverSeleccionado = "br1"
            "JP" -> serverSeleccionado = "jp1"
            "KR" -> serverSeleccionado = "kr"
            "OC" -> serverSeleccionado = "oc1"
            "RU" -> serverSeleccionado = "ru"
            "TR" -> serverSeleccionado = "tr1"
        }
    }

    //Para mostrar en el boton
    private fun setNombreServidor(binding: ActivityMainBinding) {
        when (serverSeleccionado) {
            "la1" -> binding.btnServidores.text = "LAN"
            "la2" -> binding.btnServidores.text = "LAS"
            "na1" -> binding.btnServidores.text = "NA"
            "euw1" -> binding.btnServidores.text = "EUW"
            "eun1" -> binding.btnServidores.text = "EUN"
            "br1" -> binding.btnServidores.text = "BR"
            "jp1" -> binding.btnServidores.text = "JP"
            "kr" -> binding.btnServidores.text = "KR"
            "oc1" -> binding.btnServidores.text = "OC"
            "ru" -> binding.btnServidores.text = "RU"
            "tr1" -> binding.btnServidores.text = "TR"
        }
    }
    //endregion
}