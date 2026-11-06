package com.example.parcialapp

object ReservasTemporales {

    private val reservas = mutableListOf<Reserva>()
    private var siguienteId = 1

    fun registrar(
        clienteNombre: String,
        telefono: String,
        destino: String,
        fechaViaje: String,
        cantidadPersonas: Int,
        puntoEncuentro: String,
        latitud: Double,
        longitud: Double
    ): Reserva {

        val nuevaReserva = Reserva(
            id = siguienteId,
            clienteNombre = clienteNombre,
            telefono = telefono,
            destino = destino,
            fechaViaje = fechaViaje,
            cantidadPersonas = cantidadPersonas,
            estadoReserva = "PENDIENTE",
            puntoEncuentro = puntoEncuentro,
            latitud = latitud,
            longitud = longitud
        )

        reservas.add(nuevaReserva)
        siguienteId++

        return nuevaReserva
    }

    fun obtenerTodas(): List<Reserva> {
        return reservas.toList()
    }

    fun obtenerPorId(id: Int): Reserva? {
        return reservas.find { it.id == id }
    }
}
