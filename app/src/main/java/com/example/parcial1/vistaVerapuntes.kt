package com.example.parcial1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView

class vistaVerapuntes : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vista_verapuntes)

        val vistapuntaje: ListView = findViewById(R.id.vista_lista_puntajes)

        val listaNombres = intent.getStringArrayListExtra("listaNombres")

        if (listaNombres != null) {
            val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, listaNombres)

            vistapuntaje.adapter = adapter
        }
    }
}

