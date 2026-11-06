package com.example.parcialapp

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class DestinosFragment : Fragment(R.layout.fragment_destinos) {

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        // Botón para regresar a Inicio
        val btnVolverDestinos = view.findViewById<Button>(
            R.id.btnVolverDestinos
        )

        btnVolverDestinos.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        // Lista de destinos turísticos
        val recyclerDestinos = view.findViewById<RecyclerView>(
            R.id.recyclerDestinos
        )

        val destinos = listOf(
            Destino(
                "Machu Picchu",
                "Cusco, Perú",
                "Descubre la histórica ciudadela inca, rodeada de impresionantes montañas y paisajes naturales.",
                R.drawable.machu_picchu
            ),
            Destino(
                "Laguna Humantay",
                "Cusco, Perú",
                "Visita una hermosa laguna de aguas turquesas y disfruta de una aventura rodeada de montañas.",
                R.drawable.laguna_humantay
            ),
            Destino(
                "Montaña de Siete Colores",
                "Cusco, Perú",
                "Explora la montaña Vinicunca y observa sus diferentes colores naturales.",
                R.drawable.montana_colores
            )
        )

        recyclerDestinos.layoutManager = LinearLayoutManager(
            requireContext()
        )

        recyclerDestinos.adapter = DestinoAdapter(destinos) { destino ->

            MaterialAlertDialogBuilder(requireContext())
                .setTitle(destino.nombre)
                .setMessage(
                    "${destino.ubicacion}\n\n${destino.descripcion}"
                )
                .setPositiveButton("Cerrar", null)
                .show()
        }
    }
}
