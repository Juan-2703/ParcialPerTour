package com.example.parcialapp

object UsuariosTemporales {

    private val usuarios = mutableListOf<Usuario>()

    fun registrar(usuario: Usuario): Boolean {
        val existe = usuarios.any {
            it.correo.equals(usuario.correo, ignoreCase = true)
        }

        if (existe) {
            return false
        }

        usuarios.add(usuario)
        return true
    }

    fun autenticar(correo: String, contraseña: String): Usuario? {
        return usuarios.find {
            it.correo.equals(correo, ignoreCase = true) &&
                    it.contraseña == contraseña
        }
    }
}
