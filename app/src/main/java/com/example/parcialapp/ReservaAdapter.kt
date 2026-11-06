package com.example.parcialapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ReservaAdapter(
    private val reservas: List<Reserva>,
    private val alSeleccionar: (Reserva) -> Unit
) : RecyclerView.Adapter<ReservaAdapter.ReservaViewHolder>() {

    class ReservaViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val imagen: ImageView = view.findViewById(R.id.imgReserva)
        val destino: TextView = view.findViewById(R.id.txtDestinoReserva)
        val cliente: TextView = view.findViewById(R.id.txtClienteReserva)
        val fecha: TextView = view.findViewById(R.id.txtFechaReserva)
        val estado: TextView = view.findViewById(R.id.txtEstadoReserva)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ReservaViewHolder {

        val vista = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_reserva, parent, false)

        return ReservaViewHolder(vista)
    }

    override fun onBindViewHolder(
        holder: ReservaViewHolder,
        position: Int
    ) {

        val reserva = reservas[position]

        holder.destino.text = reserva.destino
        holder.cliente.text = "Cliente: ${reserva.clienteNombre}"
        holder.fecha.text = "Fecha: ${reserva.fechaViaje}"
        holder.estado.text = reserva.estadoReserva

        val imagen = when (reserva.destino) {
            "Machu Picchu" -> R.drawable.machu_picchu
            "Laguna Humantay" -> R.drawable.laguna_humantay
            "Montaña de Siete Colores" -> R.drawable.montana_colores
            else -> R.drawable.logo_alt
        }

        holder.imagen.setImageResource(imagen)

        holder.itemView.setOnClickListener {
            alSeleccionar(reserva)
        }
    }

    override fun getItemCount(): Int {
        return reservas.size
    }
}
