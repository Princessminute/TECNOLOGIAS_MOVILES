package com.ucsm.campusmarket.utils

import java.text.DecimalFormat

// 💰 Formatear precio bonito
fun Double.toPrecio(): String {
    val formato = DecimalFormat("#,###.00")
    return "S/ ${formato.format(this)}"
}

// 💰 Si tu precio viene como String
fun String.toPrecioDouble(): Double {
    return this.toDoubleOrNull() ?: 0.0
}

// ✨ Capitalizar nombre del artículo
fun String.capitalizar(): String {
    return this.lowercase()
        .replaceFirstChar { it.uppercase() }
}