package com.example.parcialapp

import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.util.Patterns
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val correo = findViewById<TextInputEditText>(R.id.edtCorreo)
        val contraseña = findViewById<TextInputEditText>(R.id.edtContraseña)
        val btnIngresar = findViewById<Button>(R.id.btnIngresar)
        val txtRegistro = findViewById<TextView>(R.id.txtRegistro)

        txtRegistro.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        btnIngresar.setOnClickListener {

            val textoCorreo = correo.text.toString().trim()
            val textoContraseña = contraseña.text.toString()

            when {
                textoCorreo.isEmpty() -> {
                    correo.error = "Ingresa tu correo"
                    correo.requestFocus()
                }

                !Patterns.EMAIL_ADDRESS.matcher(textoCorreo).matches() -> {
                    correo.error = "Ingresa un correo válido"
                    correo.requestFocus()
                }

                textoContraseña.isEmpty() -> {
                    contraseña.error = "Ingresa tu contraseña"
                    contraseña.requestFocus()
                }

                else -> {
                    val usuario = UsuariosTemporales.autenticar(
                        textoCorreo,
                        textoContraseña
                    )

                    if (usuario != null) {
                        Toast.makeText(
                            this,
                            "Bienvenido, ${usuario.nombre}",
                            Toast.LENGTH_SHORT
                        ).show()

                        val intent = Intent(
                            this,
                            HomeActivity::class.java
                        )

                        intent.putExtra("nombreUsuario", usuario.nombre)
                        intent.putExtra("correoUsuario", usuario.correo)

                        startActivity(intent)
                        finish()

                    } else {
                        Toast.makeText(
                            this,
                            "Correo o contraseña incorrectos",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
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
}
