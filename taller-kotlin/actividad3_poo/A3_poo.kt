data class Producto(
    val nombre: String,
    val precio: Int,
    var stock: Int
)

fun Producto.vender(cantidad: Int): Boolean {
    if (cantidad <= 0) return false
    if (cantidad > stock) return false
    stock -= cantidad
    return true
}

fun Producto.reponer(cantidad: Int): Boolean {
    if (cantidad <= 0) return false
    stock += cantidad
    return true
}

val Producto.valorInventario: Int get() = precio * stock

fun main() {
    val inventario = mutableListOf(
        Producto("Teclado", 120_000, 15),
        Producto("Mouse", 45_000, 30),
        Producto("Monitor", 850_000, 4)
    )

    inventario[0].vender(3)
    inventario[1].vender(50)   // debe fallar
    inventario[2].reponer(2)

    inventario.forEach {
        println("${it.nombre}: stock=${it.stock}, valor=${it.valorInventario}")
    }

    val total = inventario.sumOf { it.valorInventario }
    println("Valor total del inventario: $total")

    val original = Producto("Teclado", 120_000, 15)
    val enOferta = original.copy(precio = 95_000)

    println(original)   // precio 120000
    println(enOferta)   // precio 95000
}
