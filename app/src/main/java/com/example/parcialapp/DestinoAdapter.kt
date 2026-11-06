package com.example.parcialapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class DestinoAdapter(
    private val destinos: List<Destino>,
    private val alSeleccionar: (Destino) -> Unit
) : RecyclerView.Adapter<DestinoAdapter.DestinoViewHolder>() {

    class DestinoViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val imagen: ImageView = view.findViewById(R.id.imgDestino)
        val nombre: TextView = view.findViewById(R.id.txtNombreDestino)
        val ubicacion: TextView = view.findViewById(R.id.txtUbicacionDestino)
        val descripcion: TextView = view.findViewById(R.id.txtDescripcionDestino)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DestinoViewHolder {

        val vista = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_destino, parent, false)

        return DestinoViewHolder(vista)
    }

    override fun onBindViewHolder(
        holder: DestinoViewHolder,
        position: Int
    ) {

        val destino = destinos[position]

        holder.imagen.setImageResource(destino.imagen)
        holder.nombre.text = destino.nombre
        holder.ubicacion.text = destino.ubicacion
        holder.descripcion.text = destino.descripcion

        holder.itemView.setOnClickListener {
            alSeleccionar(destino)
        }
    }

    override fun getItemCount(): Int {
        return destinos.size
    }
}
