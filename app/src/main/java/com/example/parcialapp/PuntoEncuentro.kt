package com.example.parcialapp

data class PuntoEncuentro(
    val nombre: String,
    val latitud: Double,
    val longitud: Double
)

object PuntosEncuentro {

    val lista = listOf(

        PuntoEncuentro(
            "Plaza de Armas de Cusco",
            -13.51677,
            -71.97878
        ),

        PuntoEncuentro(
            "Plaza Regocijo",
            -13.51709,
            -71.98020
        ),

        PuntoEncuentro(
            "Plaza San Blas",
            -13.51516,
            -71.97420
        )
    )
}
