package com.example.monedacalculadora

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    lateinit var etCantidad: EditText
    lateinit var spOrigen: Spinner
    lateinit var spDestino: Spinner
    lateinit var btnConvertir: Button
    lateinit var tvResultado: TextView

    val monedas = arrayOf(
        "Soles", "Dólar", "Euro", "Libra",
        "Rupia", "Real", "Peso", "Yuan", "Yen"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etCantidad = findViewById(R.id.etCantidad)
        spOrigen = findViewById(R.id.spMonedaOrigen)
        spDestino = findViewById(R.id.spMonedaDestino)
        btnConvertir = findViewById(R.id.btnConvertir)
        tvResultado = findViewById(R.id.tvResultado)

        val adapter = ArrayAdapter(this,
            android.R.layout.simple_spinner_item, monedas)

        adapter.setDropDownViewResource(
            android.R.layout.simple_spinner_dropdown_item)

        spOrigen.adapter = adapter
        spDestino.adapter = adapter

        btnConvertir.setOnClickListener {
            convertir()
        }
    }

    private fun convertir() {

        val cantidadTexto = etCantidad.text.toString()

        if (cantidadTexto.isEmpty()) {
            Toast.makeText(this,"Ingrese una cantidad",Toast.LENGTH_SHORT).show()
            return
        }

        val cantidad = cantidadTexto.toDouble()

        val origen = spOrigen.selectedItem.toString()
        val destino = spDestino.selectedItem.toString()

        if (origen == destino) {
            tvResultado.text = "Seleccione monedas diferentes"
            return
        }

        val tasa = obtenerTasa(origen, destino)

        val resultado = cantidad * tasa

        tvResultado.text =
            "%.2f %s = %.2f %s".format(cantidad, origen, resultado, destino)
    }
    private fun obtenerTasa(origen:String, destino:String):Double {

        val tasas = mapOf(
            "Soles" to 1.0,
            "Dólar" to 3.65,
            "Euro" to 3.95,
            "Libra" to 4.60,
            "Rupia" to 0.044,
            "Real" to 0.73,
            "Peso" to 0.21,
            "Yuan" to 0.50,
            "Yen" to 0.025
        )

        val base = tasas[origen] ?: 1.0
        val destinoTasa = tasas[destino] ?: 1.0

        return base / destinoTasa
    }
}