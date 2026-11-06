package com.example.parcialapp

import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import com.google.android.material.textfield.TextInputEditText
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.XYTileSource
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import java.util.Calendar
import java.util.Locale

class RegistroReservaActivity : AppCompatActivity() {

    private data class DestinoVisual(
        val nombre: String,
        val ubicacion: String,
        val imagen: Int,
        val latitud: Double,
        val longitud: Double
    )

    private lateinit var mapaMini: MapView

    private var marcadorActual: Marker? = null
    private var puntoSeleccionado: PuntoEncuentro? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Configuration.getInstance().load(
            applicationContext,
            getSharedPreferences("osmdroid", MODE_PRIVATE)
        )

        Configuration.getInstance().userAgentValue =
            "PerTourAndroid/1.0 ($packageName)"

        setContentView(R.layout.activity_registro_reserva)

        val edtNombre = findViewById<TextInputEditText>(
            R.id.edtClienteNombre
        )

        val edtTelefono = findViewById<TextInputEditText>(
            R.id.edtTelefonoReserva
        )

        val edtDestino =
            findViewById<MaterialAutoCompleteTextView>(
                R.id.edtDestinoReserva
            )

        val edtFecha = findViewById<TextInputEditText>(
            R.id.edtFechaReserva
        )

        val edtCantidad = findViewById<TextInputEditText>(
            R.id.edtCantidadPersonas
        )

        val edtPunto =
            findViewById<MaterialAutoCompleteTextView>(
                R.id.edtPuntoEncuentro
            )

        val contenedorDestino = findViewById<View>(
            R.id.contenedorDestinoSeleccionado
        )

        val imgDestino = findViewById<ImageView>(
            R.id.imgDestinoSeleccionado
        )

        val txtNombreDestino = findViewById<TextView>(
            R.id.txtNombreDestinoSeleccionado
        )

        val txtUbicacionDestino = findViewById<TextView>(
            R.id.txtUbicacionDestinoSeleccionado
        )

        val btnVerMapaDestino = findViewById<MaterialButton>(
            R.id.btnVerMapaDestino
        )

        val contenedorMapa = findViewById<View>(
            R.id.contenedorMiniMapa
        )

        val txtNombrePunto = findViewById<TextView>(
            R.id.txtNombrePunto
        )

        val btnVerMapa = findViewById<MaterialButton>(
            R.id.btnVerMapaCompleto
        )

        val btnGuardar = findViewById<MaterialButton>(
            R.id.btnGuardarReserva
        )

        val btnCancelar = findViewById<MaterialButton>(
            R.id.btnCancelarReserva
        )

        mapaMini = findViewById(R.id.mapaMiniReserva)

        val fuenteMapa = XYTileSource(
            "PerTourOSM",
            0,
            19,
            256,
            ".png",
            arrayOf("https://tile.openstreetmap.org/"),
            "© OpenStreetMap contributors"
        )

        mapaMini.setTileSource(fuenteMapa)
        mapaMini.setMultiTouchControls(false)
        mapaMini.controller.setZoom(17.0)

        val destinos = listOf(
            DestinoVisual(
                "Machu Picchu",
                "Urubamba, Cusco, Perú",
                R.drawable.machu_picchu,
                -13.1631,
                -72.5456
            ),
            DestinoVisual(
                "Laguna Humantay",
                "Mollepata, Cusco, Perú",
                R.drawable.laguna_humantay,
                -13.3790,
                -72.5850
            ),
            DestinoVisual(
                "Montaña de Siete Colores",
                "Vinicunca, Cusco, Perú",
                R.drawable.montana_colores,
                -13.8679,
                -71.3030
            )
        )

        var destinoSeleccionado: DestinoVisual? = null

        val nombresDestinos = destinos.map {
            it.nombre
        }

        val adaptadorDestinos = ArrayAdapter(
            this,
            android.R.layout.simple_dropdown_item_1line,
            nombresDestinos
        )

        edtDestino.setAdapter(adaptadorDestinos)

        edtDestino.setOnClickListener {
            edtDestino.showDropDown()
        }

        edtDestino.setOnItemClickListener { _, _, posicion, _ ->

            val destino = destinos[posicion]

            destinoSeleccionado = destino

            contenedorDestino.visibility = View.VISIBLE

            imgDestino.setImageResource(destino.imagen)

            imgDestino.contentDescription =
                "Fotografía de ${destino.nombre}"

            txtNombreDestino.text = destino.nombre
            txtUbicacionDestino.text = destino.ubicacion
        }

        btnVerMapaDestino.setOnClickListener {

            val destino = destinoSeleccionado
                ?: return@setOnClickListener

            val intent = Intent(
                this,
                MapaActivity::class.java
            )

            intent.putExtra("latitud", destino.latitud)
            intent.putExtra("longitud", destino.longitud)
            intent.putExtra("destino", destino.nombre)
            intent.putExtra("puntoEncuentro", destino.nombre)

            startActivity(intent)
        }

        val nombresPuntos = PuntosEncuentro.lista.map {
            it.nombre
        }

        val adaptadorPuntos = ArrayAdapter(
            this,
            android.R.layout.simple_dropdown_item_1line,
            nombresPuntos
        )

        edtPunto.setAdapter(adaptadorPuntos)

        edtPunto.setOnClickListener {
            edtPunto.showDropDown()
        }

        edtPunto.setOnItemClickListener { _, _, posicion, _ ->

            val punto = PuntosEncuentro.lista[posicion]

            puntoSeleccionado = punto

            contenedorMapa.visibility = View.VISIBLE

            txtNombrePunto.text = punto.nombre

            val ubicacion = GeoPoint(
                punto.latitud,
                punto.longitud
            )

            marcadorActual?.let {
                mapaMini.overlays.remove(it)
            }

            val marcador = Marker(mapaMini)

            marcador.position = ubicacion
            marcador.title = punto.nombre

            marcador.setAnchor(
                Marker.ANCHOR_CENTER,
                Marker.ANCHOR_BOTTOM
            )

            mapaMini.overlays.add(marcador)
            marcadorActual = marcador

            mapaMini.controller.setZoom(17.0)
            mapaMini.controller.setCenter(ubicacion)
            mapaMini.invalidate()
        }

        edtFecha.setOnClickListener {

            val calendario = Calendar.getInstance()

            val año = calendario.get(Calendar.YEAR)
            val mes = calendario.get(Calendar.MONTH)
            val dia = calendario.get(Calendar.DAY_OF_MONTH)

            val selector = DatePickerDialog(
                this,
                { _, añoElegido, mesElegido, diaElegido ->

                    val fecha = String.format(
                        Locale.getDefault(),
                        "%02d/%02d/%04d",
                        diaElegido,
                        mesElegido + 1,
                        añoElegido
                    )

                    edtFecha.setText(fecha)
                },
                año,
                mes,
                dia
            )

            selector.datePicker.minDate =
                System.currentTimeMillis() - 1000

            selector.show()
        }

        btnVerMapa.setOnClickListener {

            val punto = puntoSeleccionado
                ?: return@setOnClickListener

            val intent = Intent(
                this,
                MapaActivity::class.java
            )

            intent.putExtra("latitud", punto.latitud)
            intent.putExtra("longitud", punto.longitud)
            intent.putExtra("puntoEncuentro", punto.nombre)

            intent.putExtra(
                "destino",
                destinoSeleccionado?.nombre ?: ""
            )

            startActivity(intent)
        }

        btnGuardar.setOnClickListener {

            val nombre = edtNombre.text.toString().trim()
            val telefono = edtTelefono.text.toString().trim()
            val fecha = edtFecha.text.toString().trim()

            val cantidad = edtCantidad.text.toString()
                .toIntOrNull()

            val destino = destinoSeleccionado
            val punto = puntoSeleccionado

            when {

                nombre.isEmpty() -> {
                    edtNombre.error = "Ingresa tu nombre"
                    edtNombre.requestFocus()
                }

                !telefono.matches(Regex("9[0-9]{8}")) -> {
                    edtTelefono.error =
                        "Ingresa un celular válido de 9 dígitos"
                    edtTelefono.requestFocus()
                }

                destino == null -> {
                    Toast.makeText(
                        this,
                        "Selecciona un destino turístico",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                fecha.isEmpty() -> {
                    edtFecha.error = "Selecciona una fecha"
                }

                cantidad == null || cantidad <= 0 -> {
                    edtCantidad.error =
                        "Ingresa una cantidad válida"
                    edtCantidad.requestFocus()
                }

                punto == null -> {
                    Toast.makeText(
                        this,
                        "Selecciona un punto de encuentro",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                else -> {

                    ReservasTemporales.registrar(
                        clienteNombre = nombre,
                        telefono = telefono,
                        destino = destino.nombre,
                        fechaViaje = fecha,
                        cantidadPersonas = cantidad,
                        puntoEncuentro = punto.nombre,
                        latitud = punto.latitud,
                        longitud = punto.longitud
                    )

                    Toast.makeText(
                        this,
                        "Reserva registrada correctamente",
                        Toast.LENGTH_SHORT
                    ).show()

                    finish()
                }
            }
        }

        btnCancelar.setOnClickListener {
            finish()
        }
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

    override fun onResume() {
        super.onResume()

        if (::mapaMini.isInitialized) {
            mapaMini.onResume()
        }
    }

    override fun onPause() {

        if (::mapaMini.isInitialized) {
            mapaMini.onPause()
        }

        super.onPause()
    }

    override fun onDestroy() {

        if (::mapaMini.isInitialized) {
            mapaMini.onDetach()
        }

        super.onDestroy()
    }
}
