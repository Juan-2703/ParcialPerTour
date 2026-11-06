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

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val nombre = findViewById<TextInputEditText>(R.id.edtNombre)
        val correo = findViewById<TextInputEditText>(R.id.edtCorreoRegistro)
        val contraseña = findViewById<TextInputEditText>(R.id.edtContraseñaRegistro)
        val confirmarContraseña = findViewById<TextInputEditText>(R.id.edtConfirmarContraseña)
        val btnCrearCuenta = findViewById<Button>(R.id.btnCrearCuenta)
        val txtVolverLogin = findViewById<TextView>(R.id.txtVolverLogin)

        btnCrearCuenta.setOnClickListener {

            val textoNombre = nombre.text.toString().trim()
            val textoCorreo = correo.text.toString().trim()
            val textoContraseña = contraseña.text.toString()
            val textoConfirmar = confirmarContraseña.text.toString()

            when {
                textoNombre.isEmpty() -> {
                    nombre.error = "Ingresa tu nombre"
                    nombre.requestFocus()
                }

                textoCorreo.isEmpty() -> {
                    correo.error = "Ingresa tu correo"
                    correo.requestFocus()
                }

                !Patterns.EMAIL_ADDRESS.matcher(textoCorreo).matches() -> {
                    correo.error = "Ingresa un correo válido"
                    correo.requestFocus()
                }

                textoContraseña.length < 6 -> {
                    contraseña.error = "Mínimo 6 caracteres"
                    contraseña.requestFocus()
                }

                textoContraseña != textoConfirmar -> {
                    confirmarContraseña.error = "Las contraseñas no coinciden"
                    confirmarContraseña.requestFocus()
                }

                else -> {
                    val usuario = Usuario(
                        textoNombre,
                        textoCorreo,
                        textoContraseña
                    )

                    val registrado = UsuariosTemporales.registrar(usuario)

                    if (registrado) {

                        Toast.makeText(
                            this,
                            "Cuenta creada correctamente",
                            Toast.LENGTH_SHORT
                        ).show()

                        val intent = Intent(
                            this,
                            HomeActivity::class.java
                        )

                        intent.putExtra(
                            "nombreUsuario",
                            usuario.nombre
                        )

                        intent.putExtra(
                            "correoUsuario",
                            usuario.correo
                        )

                        intent.flags =
                            Intent.FLAG_ACTIVITY_NEW_TASK or
                                    Intent.FLAG_ACTIVITY_CLEAR_TASK

                        startActivity(intent)

                    } else {
                        correo.error = "Este correo ya está registrado"
                        correo.requestFocus()
                    }
                }
            }
        }

        txtVolverLogin.setOnClickListener {
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
}
