
## Sección Básicos (Preguntas 1–3)

**1. ¿Cuál es la diferencia entre `val` y `var` en Kotlin, y cuándo usar cada uno?**

`val` declara una referencia de solo lectura (inmutable): una vez asignado un valor, no se puede reasignar. `var` declara una variable mutable cuyo valor puede cambiar después de su inicialización.

Regla general: usar siempre `val` por defecto, y solo usar `var` cuando el valor realmente necesite cambiar durante la ejecución del programa.

```kotlin
val nombre = "Ana"        // no se puede reasignar
var edad = 21             // se puede reasignar: edad = 22
```

**2. ¿Cómo se declaran los tipos en Kotlin y qué es la inferencia de tipos?**

Kotlin es un lenguaje tipado estáticamente. El programador puede declarar el tipo explícitamente (`var x: Int = 5`) u omitirlo y dejar que el compilador lo infiera a partir del valor asignado (`var x = 5`). En este último caso, el tipo se fija en la compilación y no puede cambiar después.

```kotlin
val edad: Int = 21            // tipo explícito
val estatura = 1.68           // inferido como Double
```

**3. ¿Qué son las plantillas de cadena y cómo se insertan expresiones en ellas?**

Las plantillas de cadena son cadenas con el prefijo `$` que permiten insertar variables y expresiones directamente dentro del texto. Una variable simple se escribe como `$nombre`; una expresión más compleja se envuelve en `${expresión}`.

```kotlin
val nombre = "Ana"
val imc = 22.14
println("$nombre tiene un IMC de ${"%.2f".format(imc)}")
// Salida: Ana tiene un IMC de 22.14
```

---

## Sección Condicionales (Preguntas 4–6)

**4. ¿En qué se diferencia `if` como sentencia de `if` como expresión en Kotlin?**

En Kotlin, `if` es una expresión: devuelve un valor. Eso significa que se puede asignar directamente el resultado de un `if` a una variable sin necesidad de un operador ternario (que Kotlin no tiene).

```kotlin
val envio = if (subtotal >= 150_000) 0 else 12_000
```

**5. ¿Cómo funciona `when` y cuándo es preferible a una cadena de `if-else`?**

`when` es la estructura de ramificación múltiple de Kotlin. Puede usarse sin argumento (como una cadena de condiciones booleanas) o con un sujeto (comparando un valor contra varias opciones). Es preferible cuando hay tres o más condiciones mutuamente excluyentes sobre el mismo valor, porque es más legible y el compilador puede advertir si faltan ramas cuando se usa con `else` omitido sobre tipos sealed o enum.

```kotlin
fun clasificarIMC(imc: Double): String = when {
    imc < 18.5 -> "Bajo peso"
    imc < 25.0 -> "Normal"
    imc < 30.0 -> "Sobrepeso"
    else       -> "Obesidad"
}
```

**6. ¿Es obligatoria la rama `else` en `when`?**

Cuando `when` se usa como expresión (su resultado se asigna o se retorna), sí es obligatoria la rama `else` a menos que el compilador pueda verificar que todas las ramas posibles están cubiertas (por ejemplo, con `enum class` o `sealed class`). Cuando se usa como sentencia (solo por sus efectos secundarios), `else` es opcional.

---

## Sección Nulabilidad (Preguntas 7–9)

**7. ¿Qué significa el operador `?` en un tipo como `String?` y qué es una llamada segura `?.`?**

El `?` indica que la variable puede contener `null` además del tipo declarado (`String?` acepta `String` o `null`). La llamada segura `?.` permite acceder a una propiedad o llamar a un método solo si el objeto no es `null`; si es `null`, toda la expresión devuelve `null` sin lanzar excepción.

```kotlin
val cupon: String? = "ENVIOGRATIS"
val esGratis = cupon?.uppercase() == "ENVIOGRATIS"   // si cupon es null, esGratis es false
```

**8. ¿Qué hace el operador Elvis `?:` y dé un ejemplo.**

El operador Elvis `?:` proporciona un valor por defecto cuando el lado izquierdo es `null`. Si la izquierda no es `null`, se usa su valor; si es `null`, se usa el valor de la derecha.

```kotlin
val nombreMostrar = cupon?.uppercase() ?: "Sin cupón"
```

**9. ¿Por qué no se debe usar el operador `!!` (not-null assertion)?**

El operador `!!` fuerza al compilador a tratar un valor nullable como si no fuera nulo. Si en tiempo de ejecución el valor es `null`, lanza una `NullPointerException` y puede hacer caer la aplicación. Es preferible usar `?.`, `?:`, `let` o chequeos explícitos (`if (x != null)`) para manejar la nulabilidad de forma segura y predecible.

---

## Sección POO (Preguntas 10–12)

**10. ¿Qué es una `data class` y qué métodos genera automáticamente?**

Una `data class` es una clase diseñada para contener datos. El compilador genera automáticamente `equals()`, `hashCode()`, `toString()`, `componentN()` (para desestructuración) y `copy()` basándose en las propiedades declaradas en el constructor principal.

```kotlin
data class Producto(val nombre: String, val precio: Int, var stock: Int)
val p = Producto("Teclado", 120_000, 15)
println(p)  // Producto(nombre=Teclado, precio=120000, stock=15)
```

**11. ¿Qué hace el método `copy()` y por qué es útil?**

`copy()` crea una instancia nueva de la data class con los mismos valores, permitiendo sobrescribir propiedades específicas. Es útil para crear variantes de un objeto sin mutar el original, favoreciendo la inmutabilidad.

```kotlin
val original = Producto("Teclado", 120_000, 15)
val enOferta = original.copy(precio = 95_000)
// original sigue valiendo 120.000; enOferta vale 95.000
```

**12. ¿Qué es una `sealed class` y qué ventaja ofrece al usarla con `when`?**

Una `sealed class` restringe la herencia de una clase a sus subclases definidas dentro del mismo módulo y archivo. Su ventaja principal es que el compilador puede verificar exhaustividad en `when`: si se cubren todas las subclases, no se necesita rama `else`.

---

## Sección Lambdas (Preguntas 13–15)

**13. ¿Qué es la variable implícita `it` en una lambda?**

`it` es el nombre implícito del primer parámetro de una lambda cuando ese parámetro es el único. Permite escribir lambdas más concisas sin declarar explícitamente el nombre del parámetro.

```kotlin
val disponibles = catalogo.filter { it.disponible }
// Equivalente a: catalogo.filter { producto -> producto.disponible }
```

**14. ¿Qué es una función de orden superior (HOF) y dé un ejemplo.**

Una función de orden superior es una función que recibe otra función (lambda) como parámetro y/o devuelve una función como resultado. En el taller, `filtrarProductos` recibe un criterio `(Producto) -> Boolean` y lo aplica sobre la lista.

```kotlin
fun filtrarProductos(
    productos: List<Producto>,
    criterio: (Producto) -> Boolean
): List<Producto> = productos.filter(criterio)
```

**15. ¿Qué es una función de extensión y cómo se declara?**

Una función de extensión permite agregar nuevos métodos a una clase existente sin heredar de ella. Se declara especificando el tipo receptor antes del nombre de la función, y dentro se puede acceder a los miembros de ese tipo mediante `this` o implícitamente.

```kotlin
fun <T> List<T>.contarSi(criterio: (T) -> Boolean): Int = count(criterio)

val n = catalogo.contarSi { it.disponible }   // 3
```
