package com.example.parcialapp

data class Reserva(
    val id: Int,
    val clienteNombre: String,
    val telefono: String,
    val destino: String,
    val fechaViaje: String,
    val cantidadPersonas: Int,
    var estadoReserva: String,
    val puntoEncuentro: String,
    val latitud: Double,
    val longitud: Double
)
