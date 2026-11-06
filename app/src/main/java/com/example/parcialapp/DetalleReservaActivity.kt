package com.example.parcialapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class DetalleReservaActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_reserva)

        supportActionBar?.hide()

        val idReserva = intent.getIntExtra("idReserva", -1)

        val reserva = ReservasTemporales.obtenerPorId(idReserva)

        if (reserva == null) {
            Toast.makeText(
                this,
                "No se encontró la reserva",
                Toast.LENGTH_SHORT
            ).show()

            finish()
            return
        }

        val imagen = findViewById<ImageView>(R.id.imgDestinoDetalle)
        val destino = findViewById<TextView>(R.id.txtDestinoDetalle)
        val informacion = findViewById<TextView>(R.id.txtInformacionDetalle)
        val estado = findViewById<TextView>(R.id.txtEstadoDetalle)
        val punto = findViewById<TextView>(R.id.txtPuntoDetalle)
        val coordenadas = findViewById<TextView>(R.id.txtCoordenadasDetalle)

        val btnCambiarEstado = findViewById<Button>(R.id.btnCambiarEstado)
        val btnVerMapa = findViewById<Button>(R.id.btnVerMapaDetalle)
        val btnVolver = findViewById<Button>(R.id.btnVolverDetalle)

        val imagenDestino = when (reserva.destino) {
            "Machu Picchu" -> R.drawable.machu_picchu
            "Laguna Humantay" -> R.drawable.laguna_humantay
            "Montaña de Siete Colores" -> R.drawable.montana_colores
            else -> R.drawable.logo_alt
        }

        imagen.setImageResource(imagenDestino)

        destino.text = reserva.destino

        informacion.text = """
            ID: ${reserva.id}

            Cliente: ${reserva.clienteNombre}

            Teléfono: ${reserva.telefono}

            Fecha de viaje: ${reserva.fechaViaje}

            Cantidad de personas: ${reserva.cantidadPersonas}
        """.trimIndent()

        estado.text = "Estado: ${reserva.estadoReserva}"

        punto.text = reserva.puntoEncuentro

        coordenadas.text = """
            Latitud: ${reserva.latitud}

            Longitud: ${reserva.longitud}
        """.trimIndent()

        btnCambiarEstado.setOnClickListener {

            val estados = arrayOf(
                "PENDIENTE",
                "CONFIRMADO",
                "FINALIZADO"
            )

            AlertDialog.Builder(this)
                .setTitle("Cambiar estado de reserva")
                .setSingleChoiceItems(
                    estados,
                    estados.indexOf(reserva.estadoReserva)
                ) { dialogo, posicion ->

                    reserva.estadoReserva = estados[posicion]

                    estado.text = "Estado: ${reserva.estadoReserva}"

                    Toast.makeText(
                        this,
                        "Estado actualizado correctamente",
                        Toast.LENGTH_SHORT
                    ).show()

                    dialogo.dismiss()
                }
                .setNegativeButton("Cancelar", null)
                .show()
        }

        btnVerMapa.setOnClickListener {

            val intent = Intent(
                this,
                MapaActivity::class.java
            )

            intent.putExtra("latitud", reserva.latitud)
            intent.putExtra("longitud", reserva.longitud)
            intent.putExtra("puntoEncuentro", reserva.puntoEncuentro)
            intent.putExtra("destino", reserva.destino)

            startActivity(intent)
        }

        btnVolver.setOnClickListener {
            finish()
        }
    }
}
