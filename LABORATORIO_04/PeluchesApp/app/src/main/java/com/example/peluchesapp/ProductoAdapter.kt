package com.example.peluchesapp


import android.content.Intent
import android.view.*
import android.widget.*
import androidx.recyclerview.widget.RecyclerView

class ProductoAdapter(
    private val lista: List<Producto>,
    private val onAgregar: (Producto) -> Unit
) : RecyclerView.Adapter<ProductoAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nombre = view.findViewById<TextView>(R.id.txtNombre)
        val precio = view.findViewById<TextView>(R.id.txtPrecio)
        val imagen = view.findViewById<ImageView>(R.id.imgProducto)
        val boton = view.findViewById<Button>(R.id.btnAgregar)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_producto, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = lista.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val producto = lista[position]

        holder.nombre.text = producto.nombre
        holder.precio.text = "S/ ${producto.precio}"
        holder.imagen.setImageResource(producto.imagen)

        // Botón agregar
        holder.boton.setOnClickListener {
            onAgregar(producto)
        }

        holder.itemView.setOnClickListener {

            val intent = Intent(holder.itemView.context, DetalleActivity::class.java)
            intent.putExtra("nombre", producto.nombre)
            intent.putExtra("precio", producto.precio)
            intent.putExtra("cantidad", producto.cantidad)
            intent.putExtra("imagen", producto.imagen)

            holder.itemView.context.startActivity(intent)
        }
    }
}