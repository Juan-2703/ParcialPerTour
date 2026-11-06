package com.example.parcialapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ReservasFragment : Fragment(R.layout.fragment_reservas) {

    private lateinit var recyclerReservas: RecyclerView
    private lateinit var txtSinReservas: TextView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerReservas = view.findViewById(R.id.recyclerReservas)
        txtSinReservas = view.findViewById(R.id.txtSinReservas)

        val fabNuevaReserva = view.findViewById<FloatingActionButton>(
            R.id.fabNuevaReserva
        )

        recyclerReservas.layoutManager = LinearLayoutManager(
            requireContext()
        )

        fabNuevaReserva.setOnClickListener {
            val intent = Intent(
                requireContext(),
                RegistroReservaActivity::class.java
            )
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        mostrarReservas()
    }

    private fun mostrarReservas() {

        val reservas = ReservasTemporales.obtenerTodas()

        if (reservas.isEmpty()) {
            txtSinReservas.visibility = View.VISIBLE
        } else {
            txtSinReservas.visibility = View.GONE
        }

        recyclerReservas.adapter = ReservaAdapter(reservas) { reserva ->

            val intent = Intent(
                requireContext(),
                DetalleReservaActivity::class.java
            )

            intent.putExtra("idReserva", reserva.id)
            startActivity(intent)
        }
    }
}
