package com.example.proyectou1p2

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.Switch
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var cuentaMesa: CuentaMesa
    private lateinit var pastelItem: ItemMenu
    private lateinit var cazuelaItem: ItemMenu

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        pastelItem = ItemMenu("Pastel de Choclo", 12000)
        cazuelaItem = ItemMenu("Cazuela", 10000)
        cuentaMesa = CuentaMesa(1)

        val pastelQuantity = findViewById<EditText>(R.id.pastelQuantity)
        val cazuelaQuantity = findViewById<EditText>(R.id.cazuelaQuantity)
        val propinaSwitch = findViewById<Switch>(R.id.propinaSwitch)

        val pastelSubtotal = findViewById<TextView>(R.id.pastelSubtotal)
        val cazuelaSubtotal = findViewById<TextView>(R.id.cazuelaSubtotal)
        val comidaTotal = findViewById<TextView>(R.id.comidaTotal)
        val propinaTotal = findViewById<TextView>(R.id.propinaTotal)
        val total = findViewById<TextView>(R.id.total)

        pastelQuantity.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val cantidad = s.toString().toIntOrNull() ?: 0
                cuentaMesa.agregarItem(pastelItem, cantidad)
                pastelSubtotal.text = "$${cuentaMesa.items[0].calcularSubtotal()}"
                actualizarTotales(comidaTotal, propinaTotal, total)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        cazuelaQuantity.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val cantidad = s.toString().toIntOrNull() ?: 0
                cuentaMesa.agregarItem(cazuelaItem, cantidad)
                cazuelaSubtotal.text = "$${cuentaMesa.items[1].calcularSubtotal()}"
                actualizarTotales(comidaTotal, propinaTotal, total)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        propinaSwitch.setOnCheckedChangeListener { _, isChecked ->
            cuentaMesa.aceptaPropina = isChecked
            actualizarTotales(comidaTotal, propinaTotal, total)
        }
    }

    private fun actualizarTotales(comidaTotal: TextView, propinaTotal: TextView, total: TextView) {
        comidaTotal.text = "$${cuentaMesa.calcularTotalSinPropina()}"
        propinaTotal.text = "$${cuentaMesa.calcularPropina()}"
        total.text = "$${cuentaMesa.calcularTotalConPropina()}"
    }
}
