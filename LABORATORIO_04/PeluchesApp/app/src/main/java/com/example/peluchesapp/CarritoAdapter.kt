package com.example.peluchesapp

import android.view.*
import android.widget.*
import androidx.recyclerview.widget.RecyclerView

class CarritoAdapter(
    private val lista: MutableList<Producto>,
    private val onUpdate: () -> Unit
) : RecyclerView.Adapter<CarritoAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nombre = view.findViewById<TextView>(R.id.txtNombre)
        val precio = view.findViewById<TextView>(R.id.txtPrecio)
        val cantidad = view.findViewById<TextView>(R.id.txtCantidad)
        val imagen = view.findViewById<ImageView>(R.id.imgProducto)
        val sumar = view.findViewById<Button>(R.id.btnSumar)
        val restar = view.findViewById<Button>(R.id.btnRestar)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_carrito, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = lista.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val producto = lista[position]

        holder.nombre.text = producto.nombre
        holder.precio.text = "S/ ${producto.precio}"
        holder.cantidad.text = producto.cantidad.toString()
        holder.imagen.setImageResource(producto.imagen)

        holder.sumar.setOnClickListener {
            producto.cantidad++
            notifyItemChanged(position)
            onUpdate()
        }

        holder.restar.setOnClickListener {
            if (producto.cantidad > 1) {
                producto.cantidad--
                notifyItemChanged(position)
            } else {
                lista.removeAt(position)
                notifyItemRemoved(position)
            }
            onUpdate()
        }
    }
}