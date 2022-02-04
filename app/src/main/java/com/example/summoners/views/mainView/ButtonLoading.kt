package com.example.summoners.views.mainView

import android.view.View
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import com.example.summoners.R

class ButtonLoading(view: View) {

    val layout = view as LinearLayout
    val progressBar = view.findViewById<ProgressBar>(R.id.btnItemBarraCarga)
    val texto = view.findViewById<TextView>(R.id.btnItemTexto)


    /**
     *Inicia la animacion del boton
     */
    fun setLoading(){
        layout.isEnabled = false
        texto.visibility = View.GONE
        progressBar.visibility = View.VISIBLE
    }

    /**
     *Pone el boton en default
     */
    fun reset(){
        layout.isEnabled = true
        progressBar.visibility = View.GONE
        texto.visibility = View.VISIBLE
    }
}