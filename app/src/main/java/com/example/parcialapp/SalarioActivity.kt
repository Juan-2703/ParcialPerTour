package com.example.parcialapp

import android.graphics.Rect
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import java.text.NumberFormat
import java.util.Locale

class SalarioActivity : AppCompatActivity() {

    private val puestos = arrayOf(
        "Guía turístico",
        "Conductor turístico",
        "Asesor de reservas",
        "Asistente de operaciones"
    )

    private val tarifas = doubleArrayOf(
        20.0,
        18.0,
        16.0,
        15.0
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_salario)

        val spPuesto = findViewById<Spinner>(R.id.spPuesto)

        val edtHorasNormales = findViewById<TextInputEditText>(
            R.id.edtHorasNormales
        )

        val edtHorasExtra25 = findViewById<TextInputEditText>(
            R.id.edtHorasExtra25
        )

        val edtHorasExtra35 = findViewById<TextInputEditText>(
            R.id.edtHorasExtra35
        )

        val txtTarifa = findViewById<TextView>(
            R.id.txtTarifaHora
        )

        val txtBase = findViewById<TextView>(
            R.id.txtSueldoBase
        )

        val txtExtras = findViewById<TextView>(
            R.id.txtPagoExtras
        )

        val txtBruto = findViewById<TextView>(
            R.id.txtSueldoBruto
        )

        val txtOnp = findViewById<TextView>(
            R.id.txtDescuentoOnp
        )

        val txtEsSalud = findViewById<TextView>(
            R.id.txtEsSalud
        )

        val txtNeto = findViewById<TextView>(
            R.id.txtSueldoNeto
        )

        val txtEstado = findViewById<TextView>(
            R.id.txtEstadoCalculo
        )

        val btnVolver = findViewById<MaterialButton>(
            R.id.btnVolverSalario
        )

        val moneda = NumberFormat.getCurrencyInstance(
            Locale.forLanguageTag("es-PE")
        )

        val adaptador = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            puestos
        )

        adaptador.setDropDownViewResource(
            android.R.layout.simple_spinner_dropdown_item
        )

        spPuesto.adapter = adaptador

        fun leerHoras(campo: TextInputEditText): Double? {
            val texto = campo.text.toString()
                .trim()
                .replace(",", ".")

            return if (texto.isEmpty()) {
                0.0
            } else {
                texto.toDoubleOrNull()
            }
        }

        fun limpiarResultados() {
            txtBase.text = "—"
            txtExtras.text = "—"
            txtBruto.text = "—"
            txtOnp.text = "—"
            txtEsSalud.text = "—"
            txtNeto.text = "—"
        }

        fun actualizarCalculo() {

            val posicion = spPuesto.selectedItemPosition

            if (posicion < 0) {
                return
            }

            val pagoPorHora = tarifas[posicion]

            txtTarifa.text =
                "${moneda.format(pagoPorHora)} por hora"

            val horasNormales = leerHoras(edtHorasNormales)
            val horasExtra25 = leerHoras(edtHorasExtra25)
            val horasExtra35 = leerHoras(edtHorasExtra35)

            if (edtHorasNormales.text.toString().isBlank()) {
                limpiarResultados()
                txtEstado.text = "Ingresa las horas normales."
                return
            }

            if (
                horasNormales == null ||
                horasExtra25 == null ||
                horasExtra35 == null ||
                !horasNormales.isFinite() ||
                !horasExtra25.isFinite() ||
                !horasExtra35.isFinite() ||
                horasNormales < 0 ||
                horasExtra25 < 0 ||
                horasExtra35 < 0 ||
                horasNormales + horasExtra25 + horasExtra35 > 744
            ) {
                limpiarResultados()
                txtEstado.text =
                    "Revisa las horas ingresadas."
                return
            }

            val sueldoBase = horasNormales * pagoPorHora

            val pagoExtra25 =
                horasExtra25 * pagoPorHora * 1.25

            val pagoExtra35 =
                horasExtra35 * pagoPorHora * 1.35

            val totalExtras = pagoExtra25 + pagoExtra35

            val sueldoBruto = sueldoBase + totalExtras

            val descuentoOnp = sueldoBruto * 0.13

            val sueldoNeto = sueldoBruto - descuentoOnp

            val aporteEsSalud =
                maxOf(sueldoBruto, 1230.0) * 0.09

            txtBase.text = moneda.format(sueldoBase)
            txtExtras.text = moneda.format(totalExtras)
            txtBruto.text = moneda.format(sueldoBruto)
            txtOnp.text = "- ${moneda.format(descuentoOnp)}"
            txtEsSalud.text = moneda.format(aporteEsSalud)
            txtNeto.text = moneda.format(sueldoNeto)

            txtEstado.text =
                "Estimación para trabajador afiliado a ONP."
        }

        val observador = object : TextWatcher {

            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence?,
                start: Int,
                before: Int,
                count: Int
            ) {
            }

            override fun afterTextChanged(s: Editable?) {
                actualizarCalculo()
            }
        }

        edtHorasNormales.addTextChangedListener(observador)
        edtHorasExtra25.addTextChangedListener(observador)
        edtHorasExtra35.addTextChangedListener(observador)

        spPuesto.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    actualizarCalculo()
                }

                override fun onNothingSelected(
                    parent: AdapterView<*>?
                ) {
                }
            }

        btnVolver.setOnClickListener {
            finish()
        }

        actualizarCalculo()
    }

    override fun dispatchTouchEvent(evento: MotionEvent): Boolean {

        if (evento.action == MotionEvent.ACTION_DOWN) {

            val campoActivo = currentFocus

            if (campoActivo is EditText) {

                val areaCampo = Rect()
                campoActivo.getGlobalVisibleRect(areaCampo)

                if (!areaCampo.contains(
                        evento.rawX.toInt(),
                        evento.rawY.toInt()
                    )
                ) {
                    val teclado = getSystemService(
                        INPUT_METHOD_SERVICE
                    ) as InputMethodManager

                    teclado.hideSoftInputFromWindow(
                        campoActivo.windowToken,
                        0
                    )

                    val pantalla = findViewById<View>(
                        android.R.id.content
                    )

                    pantalla.isFocusableInTouchMode = true
                    pantalla.requestFocus()
                }
            }
        }

        return super.dispatchTouchEvent(evento)
    }
}
