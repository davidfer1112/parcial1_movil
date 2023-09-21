package com.example.parcial1

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.gms.location.LocationServices
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken

import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener

class vistaJugar : AppCompatActivity() {

    private lateinit var palabraActual: Pair<String, String>

    //lista de palabras
    private val palabras = listOf(
        Pair("CAMINO", "OCAMIN"),
        Pair("FRUTA", "FRUAT"),
        Pair("TELEFONO", "EFOLPETNO"),
        Pair("UNIVERSIDAD", "ADIVERSIUN"),
        Pair("PLANETARIO", "OTARPLENAI"),
        Pair("MARIPOSA", "MSAPIARO"),
        Pair("GUITARRA", "RIATUGRA"),
        Pair("ELEFANTE", "ENFELATE"),
        Pair("BIBLIOTECA", "BBLCITEOAI"),
        Pair("RELAMPAGO", "MAARELGPO"),
        Pair("CASCADA", "ASCACDA"),
        Pair("ENTENDER", "TERNEDNE"),
        Pair("DINAMICO", "ONMICADI"),
        Pair("ENTUSIASMO", "NMOSTEUSIA"),
        Pair("CARAMELO", "MLAECARO"),
        Pair("AUTOMOVIL", "AMOOTLUVI"),
        Pair("ELECCION", "CIONHELE"),
        Pair("PARAGUAS", "AASRAPGU"),
        Pair("SISTEMATICO", "AISTOTCMESI"),
        Pair("TRAPEZOIDE", "OEDTZAPIRE")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vista_jugar)

        val textovidad:TextView = findViewById(R.id.text_vidas)
        val textopuntaje:TextView = findViewById(R.id.text_puntaje)
        val editintento:EditText = findViewById(R.id.edit_inento)
        val botonvalidar:Button = findViewById(R.id.boton_verificar)

        editintento.filters = arrayOf(android.text.InputFilter.AllCaps())

        var vidas: Int = 3
        var puntaje: Int = 0

        textovidad.text = "Vidas restantes: $vidas"
        textopuntaje.text = "Puntaje: $puntaje"

        // Escoge una palabra al azar de la lista
        palabraActual = palabras.random()

        // Muestra la palabra desordenada en el TextView
        val textPalabraAdivina: TextView = findViewById(R.id.text_palabra_adivina)
        textPalabraAdivina.text = palabraActual.second

        botonvalidar.setOnClickListener {
            val editintento: EditText = findViewById(R.id.edit_inento)
            val textPalabraAdivina: TextView = findViewById(R.id.text_palabra_adivina)

            val intento = editintento.text.toString().trim()
            val palabraOrdenada = palabraActual.first

            if (intento == palabraOrdenada) {
                // Adivinó la palabra correctamente, suma un punto y muestra un mensaje
                puntaje++
                Toast.makeText(this, "¡Correcto!", Toast.LENGTH_SHORT).show()

                // Actualiza el puntaje y escoge una nueva palabra
                textopuntaje.text = "Puntaje: $puntaje"
                palabraActual = palabras.random()
                textPalabraAdivina.text = palabraActual.second

                // Limpia el campo de entrada
                editintento.text.clear()
            } else {
                // No adivinó la palabra, resta una vida y muestra un mensaje
                vidas--
                Toast.makeText(this, "Incorrecto. Vidas restantes: $vidas", Toast.LENGTH_SHORT).show()

                // Actualiza las vidas
                textovidad.text = "Vidas restantes: $vidas"

                if (vidas == 0) {
                    // Si se quedó sin vidas, deshabilita el botón y el campo de entrada
                    botonvalidar.isEnabled = false
                    editintento.isEnabled = false
                }

                // Limpia el campo de entrada
                editintento.text.clear()
            }
        }

        // Solicitar permisos de ubicación
        Dexter.withActivity(this)
            .withPermissions(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
            .withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport) {
                    if (report.areAllPermissionsGranted()) {
                        // Los permisos fueron otorgados, obtén la ubicación del usuario
                        obtenerUbicacion()
                    } else {
                        // no se dio los permisos
                    }
                }

                //si el usrio necesita una explicacio adicional
                override fun onPermissionRationaleShouldBeShown(
                    permissions: MutableList<PermissionRequest>,
                    token: PermissionToken
                ) {
                    token.continuePermissionRequest()
                }
            })
            .check()
    }

    private fun obtenerUbicacion() {

        // Crear una instancia de FusedLocationProviderClient
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        // Verificar si tienes permiso para acceder a la ubicación
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            // Obtener la última ubicación conocida del usuario
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location: Location? ->
                    location?.let {
                        val latitud = it.latitude
                        val longitud = it.longitude
                        Toast.makeText(
                            this,
                            "Latitud: $latitud, Longitud: $longitud",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
        }
    }
}