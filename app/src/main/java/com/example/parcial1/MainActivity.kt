package com.example.parcial1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val botonJugar: Button = findViewById(R.id.boton_jugar)
        val botonPuntaje: Button = findViewById(R.id.boton_puntaje)
        val editNombre: EditText = findViewById(R.id.edit_text_nombre)

        val listaNombres = mutableListOf<String>() // Lista para almacenar nombres

        editNombre.filters = arrayOf(android.text.InputFilter.AllCaps())

        botonJugar.setOnClickListener {
            val nombre = editNombre.text.toString()
            listaNombres.add(nombre) // Agregar el nombre a la lista

            val intent = Intent(this, vistaJugar::class.java)
            intent.putExtra("nombre", nombre) // Agregar el nombre como un extra
            startActivity(intent)
        }

        botonPuntaje.setOnClickListener {
            val intent = Intent(this, vistaVerapuntes::class.java)
            intent.putStringArrayListExtra("listaNombres", ArrayList(listaNombres)) // Pasar la lista de nombres como un extra
            startActivity(intent)
        }
    }
}
