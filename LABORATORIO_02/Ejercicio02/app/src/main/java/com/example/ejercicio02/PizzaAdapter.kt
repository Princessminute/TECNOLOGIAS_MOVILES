package com.example.ejercicio02

import android.content.Context
import android.graphics.Color
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.*

class PizzaAdapter(private val context: Context, private val pizzas: List<Pizza>) : BaseAdapter() {

    // Set para controlar qué pizzas están seleccionadas
    private val selectedPositions = mutableSetOf<Int>()

    override fun getCount(): Int = pizzas.size
    override fun getItem(position: Int): Any = pizzas[position]
    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        // Tarjeta principal
        val cardLayout = LinearLayout(context)
        cardLayout.orientation = LinearLayout.VERTICAL
        cardLayout.gravity = Gravity.CENTER
        cardLayout.setPadding(16, 16, 16, 16)
        cardLayout.setBackgroundResource(android.R.drawable.dialog_holo_light_frame)

        // FrameLayout para imagen + overlay + texto
        val frame = FrameLayout(context)
        frame.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )

        // Imagen de pizza
        val imageView = ImageView(context)
        imageView.setImageResource(pizzas[position].imageRes)
        imageView.layoutParams = FrameLayout.LayoutParams(400, 400, Gravity.CENTER)
        imageView.scaleType = ImageView.ScaleType.CENTER_CROP

        // Overlay semitransparente
        val overlay = View(context)
        overlay.setBackgroundColor(Color.parseColor("#80000000"))
        overlay.layoutParams = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.MATCH_PARENT,
            FrameLayout.LayoutParams.MATCH_PARENT,
            Gravity.CENTER
        )

        // Texto centrado
        val textView = TextView(context)
        textView.text = pizzas[position].name
        textView.gravity = Gravity.CENTER
        textView.setTextColor(context.resources.getColor(R.color.white))
        textView.textSize = 18f
        textView.layoutParams = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.MATCH_PARENT,
            FrameLayout.LayoutParams.MATCH_PARENT,
            Gravity.CENTER
        )

        // Inicializar visibilidad según el estado
        val isSelected = selectedPositions.contains(position)
        overlay.visibility = if (isSelected) View.VISIBLE else View.INVISIBLE
        textView.visibility = if (isSelected) View.VISIBLE else View.INVISIBLE

        // Click: alternar selección
        frame.setOnClickListener {
            val nowSelected = !selectedPositions.contains(position)
            if (nowSelected) {
                selectedPositions.add(position)
                overlay.visibility = View.VISIBLE
                textView.visibility = View.VISIBLE
                Toast.makeText(context, "Has elegido: ${pizzas[position].name}", Toast.LENGTH_SHORT).show()
            } else {
                selectedPositions.remove(position)
                overlay.visibility = View.INVISIBLE
                textView.visibility = View.INVISIBLE
                Toast.makeText(context, "Has desmarcado: ${pizzas[position].name}", Toast.LENGTH_SHORT).show()
            }
        }

        // Añadir views
        frame.addView(imageView)
        frame.addView(overlay)
        frame.addView(textView)

        cardLayout.addView(frame)
        return cardLayout
    }
}