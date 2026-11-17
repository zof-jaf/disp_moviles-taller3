# README — Mini-retro Taller Evaluativo Kotlin (Taller 2 oficial)

**Curso:** Programación para Dispositivos Móviles — Universidad de Caldas  
**Unidad:** 1: Fundamentos de Kotlin (Unit 1 Pathway 1 + Unit 2 Pathway 1)  
**Entregable:** Taller Evaluativo Kotlin — Actividades 1 a 4  

---

## 1. Estructura de la entrega

```
taller-kotlin/
  actividad1_basicos/A1_basicos.kt        # Perfil y métricas (variables, funciones, when, plantillas)
  actividad2_condicionales_null/A2_cond_null.kt   # Tarifa de envío segura (?. / ?: / let, sin !!)
  actividad3_poo/A3_poo.kt                # Inventario mini (data class, vender/reponer, valorInventario, copy)
  actividad4_lambdas/A4_lambdas.kt        # Filtro flexible (HOF, lambda criterio, contarSi extensión)
  README.md                               # Este archivo (mini-retro)
RESPUESTAS.md                             # Cuestionario de comprensión (15 preguntas)
```

## 2. Cómo compilar y ejecutar

Cada actividad es un archivo Kotlin independiente con su propia función `main()`. Para ejecutar desde consola con `kotlinc`:

```bash
kotlinc taller-kotlin/actividad1_basicos/A1_basicos.kt -include-runtime -out A1_basicos.jar
java -jar A1_basicos.jar
```

O ejecutar directamente cada archivo en un IDE (IntelliJ IDEA / Android Studio) abriendo el proyecto y ejecutando el `main()` de cada archivo.

> Nota: este taller usa Kotlin puro (sin Android Gradle), por tanto no requiere el módulo `app/` de la plantilla. Los archivos de configuración de Gradle (`build.gradle.kts`, `settings.gradle.kts`, `gradle.properties`) del repositorio base se conservan únicamente como referencia de la plantilla del curso.

## 3. Dificultades

**Uso de `val` vs `var`.** Lo que más me costó fue mantener la disciplina de usar `val` por defecto y recurrir a `var` solo cuando el estado realmente debía mutar (por ejemplo, el `stock` de `Producto` en la actividad 3). Inicialmente tendía a declarar todo como `var` por costumbre de otros lenguajes; revisé cada variable y pregunté si su valor cambiaba tras la creación. Resultado: solo `stock` se declaró como `var`, el resto es `val`.

**Nulabilidad segura sin `!!`.** En la actividad 2 tuve que manejar un cupón opcional (`String?`) garantizando que el programa no falle si es `null`. La solución combina `?.` (llamada segura), `?:` (operador Elvis para valor por defecto) y `let` (imprimir solo si existe), evitando estrictamente el operador `!!`, que lanzaría `NullPointerException` en tiempo de ejecución.

**Verificación de checkpoints.** Cada actividad tiene puntos de control (por ejemplo, `clasificarIMC(17.0)` → "Bajo peso", `clasificarIMC(31.0)` → "Obesidad"; `vender(100)` → `false` sin cambiar el stock; total del inventario = 7.890.000). Depurar paso a paso fue más rápido que depurar todo junto.

## 4. Aplicación en Android real — ¿dónde usarías `sealed class` en una app de verdad?

Usaría una `sealed class` para modelar el **estado de una llamada de red / resultado de una operación asíncrona** en la capa de datos o dominio de una app Android. Ejemplo:

```kotlin
sealed class ResultadoApi<out T> {
    data class Exito<T>(val datos: T) : ResultadoApi<T>()
    data class Error(val mensaje: String, val codigo: Int) : ResultadoApi<Nothing>()
    object Cargando : ResultadoApi<Nothing>()
}
```

Esto permite que la UI reaccione de forma **exhaustiva y segura** con un `when` en el ViewModel o en Compose: si aparecen `Exito`, `Error` y `Cargando`, el compilador obliga a cubrir los tres casos, evitando olvidar manejar el estado de error o de carga. También es útil para representar eventos de navegación entre pantallas, estados de un formulario (válido / inválido / editando) o tipos de notificación push.

## 5. Tres aprendizajes clave

- Usar `val` por defecto y `var` solo cuando sea estrictamente necesario hace el código más predecible y fácil de razonar.
- El manejo de nulabilidad con `?.`, `?:` y `let` elimina la mayoría de los `NullPointerException` sin necesidad de recurrir a `!!`.
- Las funciones de orden superior y las funciones de extensión permiten escribir lógica de filtro y conteo reutilizable y concisa, separando el criterio del mecanismo de recorrido.
