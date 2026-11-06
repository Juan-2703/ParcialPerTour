package com.example.parcialapp

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.textfield.TextInputEditText

class ContactosFragment : Fragment(R.layout.fragment_contactos) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val txtNombre = view.findViewById<TextView>(R.id.txtNombreContacto)
        val txtCorreo = view.findViewById<TextView>(R.id.txtCorreoContacto)
        val asunto = view.findViewById<TextInputEditText>(R.id.edtAsuntoContacto)
        val mensaje = view.findViewById<TextInputEditText>(R.id.edtMensajeContacto)
        val btnEnviarConsulta = view.findViewById<Button>(R.id.btnEnviarConsulta)

        val nombreUsuario = requireActivity().intent
            .getStringExtra("nombreUsuario") ?: "Usuario"

        val correoUsuario = requireActivity().intent
            .getStringExtra("correoUsuario") ?: "No disponible"

        txtNombre.text = nombreUsuario
        txtCorreo.text = correoUsuario

        btnEnviarConsulta.setOnClickListener {

            val textoAsunto = asunto.text.toString().trim()
            val textoMensaje = mensaje.text.toString().trim()

            when {
                textoAsunto.isEmpty() -> {
                    asunto.error = "Ingresa el asunto"
                    asunto.requestFocus()
                }

                textoMensaje.isEmpty() -> {
                    mensaje.error = "Escribe tu consulta"
                    mensaje.requestFocus()
                }

                else -> {

                    val contenido = """
                        Nombre: $nombreUsuario
                        Correo: $correoUsuario

                        Consulta:
                        $textoMensaje
                    """.trimIndent()

                    val intent = Intent(Intent.ACTION_SENDTO).apply {
                        data = Uri.parse("mailto:")
                        putExtra(Intent.EXTRA_SUBJECT, textoAsunto)
                        putExtra(Intent.EXTRA_TEXT, contenido)
                    }

                    try {
                        startActivity(intent)
                    } catch (e: ActivityNotFoundException) {
                        Toast.makeText(
                            requireContext(),
                            "No hay una aplicación de correo disponible",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
        }
    }
}
