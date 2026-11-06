package com.example.parcialapp

import android.Manifest
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.appbar.MaterialToolbar
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourcePolicy
import org.osmdroid.tileprovider.tilesource.XYTileSource
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay

class MapaActivity : AppCompatActivity() {

    private lateinit var mapa: MapView

    private var capaUbicacion: MyLocationNewOverlay? = null

    private val solicitarUbicacion = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permisos ->

        if (permisos.values.any { it }) {
            mostrarMiUbicacion()
        } else {
            Toast.makeText(
                this,
                "No se concedió el permiso de ubicación",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Configuration.getInstance().load(
            applicationContext,
            getSharedPreferences("osmdroid", MODE_PRIVATE)
        )

        Configuration.getInstance().userAgentValue =
            "PerTourAndroidIDAT/1.0"

        setContentView(R.layout.activity_mapa)

        supportActionBar?.hide()

        val toolbar = findViewById<MaterialToolbar>(
            R.id.toolbarMapa
        )

        mapa = findViewById(R.id.mapaPerTour)

        val txtPunto = findViewById<TextView>(
            R.id.txtPuntoMapa
        )

        val txtCoordenadas = findViewById<TextView>(
            R.id.txtCoordenadasMapa
        )

        val btnMiUbicacion = findViewById<Button>(
            R.id.btnMiUbicacion
        )

        val btnAbrirGoogleMaps = findViewById<Button>(
            R.id.btnAbrirGoogleMaps
        )

        toolbar.navigationIcon?.setTint(
            ContextCompat.getColor(this, R.color.blanco)
        )

        toolbar.setNavigationOnClickListener {
            finish()
        }

        val latitudRecibida = intent.getDoubleExtra(
            "latitud",
            -13.5160
        )

        val longitudRecibida = intent.getDoubleExtra(
            "longitud",
            -71.9785
        )

        val tieneReserva =
            intent.hasExtra("latitud") &&
                    intent.hasExtra("longitud") &&
                    latitudRecibida.isFinite() &&
                    longitudRecibida.isFinite() &&
                    latitudRecibida in -90.0..90.0 &&
                    longitudRecibida in -180.0..180.0

        val latitud = if (tieneReserva) {
            latitudRecibida
        } else {
            -13.5160
        }

        val longitud = if (tieneReserva) {
            longitudRecibida
        } else {
            -71.9785
        }

        val destino = intent.getStringExtra("destino")
            ?: "Cusco"

        val puntoEncuentro = intent.getStringExtra(
            "puntoEncuentro"
        ) ?: "Centro de Cusco"

        val politica = TileSourcePolicy(
            2,
            TileSourcePolicy.FLAG_NO_BULK or
                    TileSourcePolicy.FLAG_NO_PREVENTIVE or
                    TileSourcePolicy.FLAG_USER_AGENT_MEANINGFUL
        )

        val fuenteMapa = XYTileSource(
            "PerTourOSM",
            0,
            19,
            256,
            ".png",
            arrayOf("https://tile.openstreetmap.org/"),
            "© OpenStreetMap contributors",
            politica
        )

        mapa.setTileSource(fuenteMapa)
        mapa.setMultiTouchControls(true)

        val ubicacion = GeoPoint(latitud, longitud)

        mapa.controller.setZoom(
            if (tieneReserva) 16.0 else 13.0
        )

        mapa.controller.setCenter(ubicacion)

        val marcador = Marker(mapa)

        marcador.position = ubicacion

        marcador.title = if (tieneReserva) {
            puntoEncuentro
        } else {
            "Cusco, Perú"
        }

        marcador.snippet = destino

        marcador.setAnchor(
            Marker.ANCHOR_CENTER,
            Marker.ANCHOR_BOTTOM
        )

        mapa.overlays.add(marcador)
        mapa.invalidate()

        txtPunto.text = if (tieneReserva) {

            if (puntoEncuentro == destino) {
                destino
            } else {
                "$puntoEncuentro - $destino"
            }

        } else {
            "Explora Cusco o selecciona una reserva para ver su punto de encuentro."
        }

        txtCoordenadas.text =
            "Latitud: $latitud\nLongitud: $longitud"

        btnMiUbicacion.setOnClickListener {

            if (tienePermisoUbicacion()) {
                mostrarMiUbicacion()
            } else {
                solicitarUbicacion.launch(
                    arrayOf(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    )
                )
            }
        }

        btnAbrirGoogleMaps.setOnClickListener {

            val enlace = Uri.parse(
                "https://www.google.com/maps/search/"
            ).buildUpon()
                .appendQueryParameter("api", "1")
                .appendQueryParameter(
                    "query",
                    "$latitud,$longitud"
                )
                .build()

            val intentGoogleMaps = Intent(
                Intent.ACTION_VIEW,
                enlace
            ).apply {
                setPackage("com.google.android.apps.maps")
            }

            try {
                startActivity(intentGoogleMaps)

            } catch (e: ActivityNotFoundException) {

                val intentNavegador = Intent(
                    Intent.ACTION_VIEW,
                    enlace
                )

                try {
                    startActivity(intentNavegador)

                } catch (e: ActivityNotFoundException) {
                    Toast.makeText(
                        this,
                        "No se pudo abrir Google Maps",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    private fun tienePermisoUbicacion(): Boolean {

        val permisoPreciso = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        )

        val permisoAproximado = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )

        return permisoPreciso == PackageManager.PERMISSION_GRANTED ||
                permisoAproximado == PackageManager.PERMISSION_GRANTED
    }

    private fun mostrarMiUbicacion() {

        if (!tienePermisoUbicacion()) {
            return
        }

        if (capaUbicacion == null) {

            capaUbicacion = MyLocationNewOverlay(
                GpsMyLocationProvider(this),
                mapa
            )

            mapa.overlays.add(capaUbicacion!!)
        }

        val ubicacionActual = capaUbicacion ?: return

        if (ubicacionActual.enableMyLocation()) {

            ubicacionActual.enableFollowLocation()
            mapa.controller.setZoom(16.0)

            Toast.makeText(
                this,
                "Buscando tu ubicación...",
                Toast.LENGTH_SHORT
            ).show()

        } else {
            Toast.makeText(
                this,
                "Activa la ubicación del dispositivo",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    override fun onResume() {
        super.onResume()
        mapa.onResume()
    }

    override fun onPause() {
        capaUbicacion?.disableMyLocation()
        mapa.onPause()
        super.onPause()
    }

    override fun onDestroy() {
        mapa.onDetach()
        super.onDestroy()
    }
}
