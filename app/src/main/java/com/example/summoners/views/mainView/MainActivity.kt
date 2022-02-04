package com.example.summoners.views.mainView


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.summoners.api.RetrofitInstance
import com.example.summoners.databinding.ActivityMainBinding
import com.example.summoners.modelos.InvocadorModel
import com.example.summoners.responses.InvocadorService
import com.example.summoners.views.championView.ChampionsActivity
import com.example.summoners.views.estadisticasView.EstadisticasActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val dialogViewServers by lazy { ServersDialog(binding) }

    val invocadorModel = InvocadorModel
    private val retrofit = RetrofitInstance

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBuscar.btn.setOnClickListener {
            val animacion = ButtonLoading(it)
            animacion.setLoading()
            getDatos(animacion)
        }

        binding.btnServidores.setOnClickListener {
            dialogViewServers.show(supportFragmentManager, "ServerDialog")
        }

        binding.btnCampeones.setOnClickListener { initActivityChampions() }
    }

    private fun getDatos(animacion: ButtonLoading) {
        if (binding.nombreInvocador.text.isNotEmpty()) {
            val (NombreBien, NombreMal) = binding.nombreInvocador.text.partition { it.isLetterOrDigit() }
            if (NombreMal.isEmpty()) {
                retrofit.setServidor(dialogViewServers.serverSeleccionado)
                searchByName(binding.nombreInvocador.text.toString(), animacion)
            } else {
                malNombreError(NombreMal)
                animacion.reset()
            }
        } else {
            sinNombreError()
            animacion.reset()
        }
    }

    //region datosInvocador
    private fun searchByName(nombre: String, animacion: ButtonLoading) {
        CoroutineScope(IO).launch {
            try {
                val call = retrofit.getRetrofit().create(InvocadorService::class.java).byName(nombre)
                val resposeInvocador = call.body()

                invocadorModel.clearAll()
                invocadorModel.setValuesInvocador(resposeInvocador)
                if (call.isSuccessful) {
                    initActivityEstadisticas()
                } else {
                    runOnUiThread { mensajeError(call.code()) }
                }

                delay(500)
                runOnUiThread {
                    animacion.reset()
                }
            } catch (e: Exception) {
                Log.e("MainActivity", "MainActivity.searchByName error\n\n${e}")
            }
        }
    }
    //endregion

    private fun mensajeError(codigo:Int){
        when(codigo){
            401 -> noAutorizado()
            403 -> noAutenticado()
            404 -> noEncontrado()
            429 -> limitePeticiones()
            500 -> errorInterno()
            502 -> respuestaInvalida()
            503 -> sinServicio()
            504 -> sinRespuesta()
        }
    }

    //region HTTP status codes
    private fun noAutorizado(){
        Toast.makeText(this, "Se requiere de una API key para poder utilizar esta API", Toast.LENGTH_LONG).show()
    }

    private fun noAutenticado() {
        Toast.makeText(this, "Porfavor verifica la API key", Toast.LENGTH_LONG).show()
    }

    private fun noEncontrado() {
        Toast.makeText(
            this,
            "El invocador no existe o no pertenece al servidor ${binding.btnServidores.text}",
            Toast.LENGTH_LONG
        ).show()
    }

    private fun limitePeticiones(){
        Toast.makeText(this, "Se ha exedido la cantidad maxima de peticiones.Por favor espere 2 minutos", Toast.LENGTH_LONG).show()
    }

    private fun errorInterno(){
        Toast.makeText(this, "Ah ocurrido un error interno en el servidor", Toast.LENGTH_LONG).show()
    }

    private fun respuestaInvalida(){
        Toast.makeText(this, "Se ha obtenido una respuesta invalida del servidor", Toast.LENGTH_LONG).show()
    }

    private fun sinServicio(){
        Toast.makeText(this, "Se ha obtenido una respuesta invalida del servidor", Toast.LENGTH_LONG).show()
    }

    private  fun sinRespuesta(){
        Toast.makeText(this, "El servidor tardo demaciado en responder. No se obtuvo una respuesta", Toast.LENGTH_LONG).show()
    }
    //endregion

    private fun malNombreError(nombre: CharSequence) {
        Toast.makeText(this, "El nombre no puede contener [${nombre}]", Toast.LENGTH_SHORT).show()
    }

    private fun sinNombreError() {
        Toast.makeText(this, "Por favor ingrese un nombre de invocador", Toast.LENGTH_LONG).show()
    }

    private fun initActivityEstadisticas() {
        val intent = Intent(this, EstadisticasActivity::class.java)
        startActivity(intent)
    }

    private fun initActivityChampions() {
        val intent = Intent(this, ChampionsActivity::class.java)
        startActivity(intent)
    }
}

