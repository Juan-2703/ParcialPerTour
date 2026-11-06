package com.example.parcialapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment

class HomeFragment : Fragment(R.layout.fragment_home) {

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        val txtSaludo = view.findViewById<TextView>(
            R.id.txtSaludoHome
        )

        val nombre = requireActivity().intent
            .getStringExtra("nombreUsuario")

        txtSaludo.text = if (nombre.isNullOrBlank()) {
            "¡Hola, viajero!"
        } else {
            "¡Hola, $nombre!"
        }

        fun abrirDestinos() {
            parentFragmentManager.beginTransaction()
                .replace(
                    R.id.contenedorFragments,
                    DestinosFragment()
                )
                .addToBackStack(null)
                .commit()
        }

        view.findViewById<View>(
            R.id.txtVerTodos
        ).setOnClickListener {
            abrirDestinos()
        }

        view.findViewById<View>(
            R.id.cardMachuPicchu
        ).setOnClickListener {
            abrirDestinos()
        }

        view.findViewById<View>(
            R.id.cardHumantay
        ).setOnClickListener {
            abrirDestinos()
        }

        view.findViewById<View>(
            R.id.cardColores
        ).setOnClickListener {
            abrirDestinos()
        }

        view.findViewById<View>(
            R.id.btnVerDestinos
        ).setOnClickListener {
            abrirDestinos()
        }

        view.findViewById<View>(
            R.id.btnRegistrarReserva
        ).setOnClickListener {
            val intent = Intent(
                requireContext(),
                RegistroReservaActivity::class.java
            )
            startActivity(intent)
        }

        view.findViewById<View>(
            R.id.btnMisReservas
        ).setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(
                    R.id.contenedorFragments,
                    ReservasFragment()
                )
                .addToBackStack(null)
                .commit()
        }

        view.findViewById<View>(
            R.id.btnMapa
        ).setOnClickListener {
            val intent = Intent(
                requireContext(),
                MapaActivity::class.java
            )
            startActivity(intent)
        }

        view.findViewById<View>(
            R.id.btnSalario
        ).setOnClickListener {
            val intent = Intent(
                requireContext(),
                SalarioActivity::class.java
            )
            startActivity(intent)
        }
    }
}
