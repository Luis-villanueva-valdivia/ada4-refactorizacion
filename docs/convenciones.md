# Convenciones del proyecto — ADA 4 · Refactorización del gestor de préstamos

Reglas con las que trabaja el equipo. Si algo no está aquí, se decide en el issue correspondiente y se agrega a este archivo por PR.

---

## 1. Entorno y versiones

Las cinco computadoras compilan con la misma versión mínima de Java.

| Componente | Versión | Notas |
|---|---|---|
| JDK | **17 o superior** (se recomienda **21 LTS**) | El código usa *text blocks*, `switch` con flechas, *pattern matching* en `instanceof` y `Stream.toList()` |
| Nivel de lenguaje | **Java 17** | Se compila con `--release 17` para que nadie use algo que no compile en otra computadora |
| Codificación | **UTF-8** | Los mensajes llevan acentos, `ñ` y `€` |
| IDE | Libre (IntelliJ, VS Code, NetBeans) | Los archivos del IDE no se suben (ver `.gitignore`) |
| Git | Cualquier versión reciente | |

**Cómo verificarlo:** `java -version` y `javac -version`. Cada integrante comenta el resultado en el issue #1.

### 1.1 Compilar y ejecutar

Igual en Windows, macOS y Linux. Se compila desde la carpeta del proyecto que se quiere probar (`original/` o `refactorizado/`):

```bash
cd refactorizado
javac -encoding UTF-8 --release 17 -d out -sourcepath src src/gestor/prestamos/Main.java
java -Dstdout.encoding=UTF-8 -cp out gestor.prestamos.Main datos
```

- `out/` (las `.class`) y `datos/` (los CSV que genera el programa) están en `.gitignore`.
- El argumento `datos` es la carpeta donde el programa lee y escribe `herramientas.csv` y `prestamos.csv`. Para preparar una precondición del Anexo A se copian ahí los CSV del caso (`pruebas/casos/CP-XX/`).
- En Windows, si los acentos salen mal en la terminal, ejecutar antes `chcp 65001`.

---

## 2. Estructura del repositorio

| Carpeta | Qué va ahí |
|---|---|
| `original/` | Copia **intacta** del proyecto que nos dieron. **Nadie la modifica** (procedimiento, paso 1). Sirve para comparar y para la columna *Antes* del registro de pruebas |
| `refactorizado/` | Copia de trabajo. Aquí se aplican las diez técnicas |
| `pruebas/casos/` | Una carpeta por caso del Anexo A (`CP-03-02/`, …) con los CSV de la precondición y las entradas |
| `docs/pruebas/` | Registro de pruebas del Anexo A (antes y después) y el PDF de entrega |
| `docs/` | Convenciones, planeación y el PDF de la actividad |
| `.github/` | Plantilla de pull request |

---

## 3. Ramas

```
main      ← solo recibe develop al entregar (con la etiqueta v1.0.0)
develop   ← rama de integración y predeterminada
  └── tipo/iss-XX-descripcion   ← una rama por issue
```

- **Todo issue tiene su propia rama**, que sale de `develop` actualizado.
- **Nunca se hacen cambios directamente en `develop` ni en `main`.** Ambas están protegidas: no aceptan push directo y piden 1 aprobación para fusionar.
- Formato: `tipo/iss-XX-descripcion`, con `XX` a dos dígitos y la descripción en minúsculas, sin acentos, con guiones.

| Tipo | Uso | Ejemplo |
|---|---|---|
| `refactor` | Aplicar una técnica de refactorización | `refactor/iss-05-extract-method-prestar` |
| `test` | Casos y ejecución de las pruebas del Anexo A | `test/iss-03-casos-anexo-a` |
| `docs` | Documentación | `docs/iss-15-registro-pruebas` |
| `fix` | Corrección de un error (por ejemplo, una prueba que dejó de pasar) | `fix/iss-17-mensaje-devolucion` |
| `chore` | Configuración, estructura, mantenimiento | `chore/iss-02-estructura` |

```bash
git switch develop
git pull
git switch -c refactor/iss-05-extract-method-prestar
```

---

## 4. Commits

Formato: **`tipo: descripción (iss-XX)`**

- `tipo` en minúsculas, de la lista de abajo.
- Descripción en español, en presente e imperativo, sin punto final, de 72 caracteres o menos.
- Siempre termina con el issue entre paréntesis.

| Tipo | Uso |
|---|---|
| `refactor` | Cambio interno que **no** cambia el comportamiento (todas las técnicas) |
| `test` | Casos, CSV de precondición o resultados de pruebas |
| `fix` | Corrección de un error |
| `docs` | Solo documentación |
| `chore` | Configuración, estructura de carpetas, `.gitignore`, etc. |

Ejemplos:

```
refactor: extrae la seleccion de herramienta de prestar (iss-05)
refactor: mueve el calculo de la penalizacion a Prestamo (iss-08)
test: agrega los casos de la opcion 4 del anexo A (iss-03)
docs: registra los resultados antes de refactorizar (iss-04)
```

**Un paso, un commit.** La actividad pide aplicar cada técnica con un cambio pequeño y compilar después de cada uno. Si una técnica necesita varios pasos (por ejemplo, extraer tres métodos), cada paso es un commit que compila.

**Squash merge.** Los PR se fusionan con *Squash and merge*, así cada issue queda como **un solo commit** en `develop`. Por eso el **título del PR** sigue el mismo formato, porque se convierte en el mensaje del commit final.

---

## 5. Flujo de trabajo y pull requests

> [!CAUTION]
> **Dependencias entre issues.** Muchos issues **no se pueden empezar** hasta que otro esté **fusionado en `develop`** (no basta con que tenga PR abierto). Cada issue lo dice al principio con un aviso *⛔ No empieces hasta que se fusione…*, está ligado en GitHub como *Blocked by* y lleva el label `estado:bloqueado` mientras espera. El mapa completo está en [`docs/planeacion.md`](planeacion.md#2-dependencias) y en §8.

1. **Revisar las dependencias del issue.** Si alguna sigue abierta, **no se empieza**: el issue se queda en *Bloqueado* en el tablero. Si te urge, habla con el responsable del issue que te bloquea.
2. Tomar el issue en el tablero de GitHub Projects y moverlo a *En progreso*.
3. Crear la rama desde `develop` actualizado (§3).
4. Aplicar la técnica en pasos pequeños (§4). **Después de cada paso: compilar y correr todas las pruebas del Anexo A** sobre `refactorizado/`.
5. Si una prueba que antes pasaba ahora falla, la técnica cambió el comportamiento: se corrige antes de seguir.
6. Antes de abrir el PR, traer lo último de `develop`:
   ```bash
   git fetch origin
   git merge origin/develop
   ```
7. Abrir el PR **hacia `develop`** con la plantilla (`.github/pull_request_template.md`), incluido `Closes #XX`. Asignar al revisor (§6).
8. El revisor compila la rama en su computadora, corre las pruebas y **aprueba** o **pide cambios**.
9. Con la aprobación, **el autor fusiona su propio PR** con *Squash and merge* (así su trabajo queda visible).
10. **La rama se borra** al fusionar (botón *Delete branch*, y localmente `git branch -d tipo/iss-XX-...`).
11. El issue se cierra solo por el `Closes #XX`.
12. **Desbloquear a los que esperan.** En cada issue que tu issue bloqueaba (*Este issue bloquea a…*): comenta `@responsable ya está fusionado #XX, puedes empezar`, quítale el label `estado:bloqueado` y muévelo a *Por hacer* en el tablero (si ya no le falta otra dependencia).

`main` solo recibe un PR desde `develop` al entregar (ISS-16), con la etiqueta `v1.0.0`.

---

## 6. Revisión cruzada

| PR de | Lo revisa |
|---|---|
| Paola | Luis |
| Oswaldo | Paola |
| José | Oswaldo |
| Katheri | José |
| Luis | Katheri |

- El administrador del repositorio (Luis) puede aprobar cualquier PR.
- Por el plazo de entrega, si el revisor asignado no contesta en **4 h**, puede aprobar cualquier otro integrante.
- Nadie aprueba su propio PR.
- El revisor revisa que compile, que pasen las pruebas del Anexo A, que el comentario de la técnica esté en su lugar (§7) y el checklist de la plantilla.

---

## 7. Comentarios de refactorización (15 % de la calificación)

La actividad pide redactar **en el código**, justo donde se aplicó, la técnica y una breve explicación del cambio. Todos usan el mismo formato, encima del método o del bloque que cambió:

```java
// ===== Técnica 1 · Extract Method (ISS-05) =====
// Antes: prestar() elegía la herramienta, pedía las fechas y registraba el préstamo
// en un solo método de 25 líneas.
// Cambio: se extrajeron elegirHerramientaDisponible(), pedirFechasPrestamo() y
// registrarPrestamo(); prestar() ahora solo los llama en orden.
private void prestar() {
    ...
}
```

Reglas:

- **Siempre** las tres partes: encabezado `===== Técnica N · Nombre (ISS-XX) =====`, `Antes:` y `Cambio:`.
- El nombre de la técnica va **en inglés**, igual que en la tabla de la actividad.
- Si la técnica toca varios lugares (por ejemplo, Move Method en dos clases), va un comentario en **cada** lugar. En el lugar de origen se dice a dónde se movió.
- Si cambia la firma de un método, **se actualiza su Javadoc** (regla de la actividad).
- No se borran los comentarios de técnicas anteriores. Si una técnica posterior modifica el mismo método, se agrega su comentario debajo del anterior.

---

## 8. Orden de las técnicas y dueños de método

Varias técnicas tocan los mismos métodos. Para no pisarnos, **cada issue solo modifica los métodos que indica**. Si necesitas tocar otro, avísalo en el issue a su dueño antes de hacerlo.

| Técnica | Issue | Responsable | Archivo · método | Depende de |
|---|---|---|---|---|
| 1 Extract Method | ISS-05 | Paola | `MenuConsola.prestar()` | ISS-02 |
| 2 Replace Temp with Query | ISS-06 | Paola | `MenuConsola.verPrestamosActivos()` | ISS-02 |
| 3 Introduce Explaining Variable | ISS-07 | Luis | `RepositorioCsv.parsearHerramienta()` | ISS-02 |
| 4 Move Method | ISS-08 | Luis | `GestorPrestamos.devolver()` → `Prestamo` | **ISS-10** |
| 5 Separate Query from Modifier | ISS-09 | Oswaldo | `GestorPrestamos.devolver()` y `MenuConsola.devolver()` | **ISS-08** |
| 6 Remove Parameter | ISS-10 | Oswaldo | `Prestamo.registrarDevolucion()` | ISS-02 |
| 7 Replace Exception with Test | ISS-11 | José | `Entrada.pedirFecha()` | ISS-02 |
| 8 Introduce Parameter Object | ISS-12 | José | Fechas en `GestorPrestamos`, `Prestamo`, `RepositorioCsv` y `MenuConsola` | **ISS-05, ISS-08** |
| 9 Decompose Conditional | ISS-13 | Katheri | `MenuConsola.verPrestamosActivos()` | **ISS-06** |
| 10 Replace Conditional with Polymorphism | ISS-14 | Katheri | `MenuConsola.ejecutarOpcion()` | **ISS-05, ISS-09, ISS-12, ISS-13** |

Cadenas que **no** se pueden empezar en paralelo:

```
ISS-10 (T6, Oswaldo) → ISS-08 (T4, Luis) → ISS-09 (T5, Oswaldo) ─┐
ISS-05 (T1, Paola) ───────────────→ ISS-12 (T8, José) ──────────┤
ISS-06 (T2, Paola) → ISS-13 (T9, Katheri) ────────────────────┴→ ISS-14 (T10, Katheri)
ISS-07 (T3, Luis) y ISS-11 (T7, José): independientes
```

- **T6 antes que T4:** hoy `registrarDevolucion()` recibe `penalizacionPorDia` y no lo usa. Si T4 mueve primero el cálculo a `Prestamo`, ese parámetro empieza a usarse y T6 ya no tendría sentido.
- **T10 al final:** reorganiza todo el menú; si va antes, los demás PR de `MenuConsola` chocan con ella.

---

## 9. Nombres

| Elemento | Convención | Ejemplo |
|---|---|---|
| Paquetes | minúsculas, sin acentos | `gestor.prestamos.consola` |
| Clases, enums, interfaces | `PascalCase`, sustantivo, un archivo por clase con el mismo nombre | `PeriodoPrestamo`, `OpcionMenu` |
| Métodos | `camelCase`, empiezan con verbo; los que devuelven `boolean` se leen como pregunta | `pedirFechasPrestamo()`, `estaDadaDeBaja()` |
| Variables y parámetros | `camelCase`, descriptivas | `fechaLimite`, `diasRetraso` |
| Constantes | `MAYUSCULAS_CON_GUION_BAJO` | `PENALIZACION_POR_DIA` |

- Los identificadores van en español y **sin acentos ni ñ** (`anadirHerramienta`), como ya está el proyecto.
- Regla de la actividad: **el nombre dice qué hace, no cómo lo hace** (`fechaDeHoy()`, no `llamarLocalDateNow()`).

---

## 10. Estándares de código

- Indentación de **4 espacios**, sin tabuladores. UTF-8 sin BOM, salto de línea final.
- Se respeta el estilo del proyecto: llaves en la misma línea, `final` en clases que no se heredan, Javadoc en todo lo público.
- **Toda clase y método nuevo lleva Javadoc** en español con descripción, `@param`, `@return` y `@throws`.
- **Sin números mágicos:** los valores con significado van en constantes (ya existen `DIAS_PRESTAMO_DEFECTO` y `PENALIZACION_POR_DIA`).
- No se deja código comentado ni `System.out.println` de depuración.

### 10.1 Reglas de la actividad (no negociables)

- **No cambia el comportamiento visible:** el menú, los mensajes (letra por letra, con sus espacios), el formato de los CSV y los cálculos de penalización quedan igual.
- **No se eliminan funcionalidades** para simplificar el código.
- **Un cambio pequeño a la vez** y se compila después de cada uno.
- **Si cambia una firma, se actualiza su Javadoc.**

---

## 11. Pruebas del Anexo A

- Cada caso tiene su carpeta en `pruebas/casos/CP-XX-YY/` con:
  - `herramientas.csv` y `prestamos.csv`: la precondición (si el caso no tiene, la carpeta no los trae);
  - `entrada.txt`: lo que se escribe en el teclado, una línea por entrada;
  - `LEEME.md`: precondición, entradas y resultado esperado copiados del Anexo A.
- Para correr un caso: copiar los CSV a `datos/`, ejecutar el programa y escribir las entradas (o redirigir `entrada.txt`).
- El registro vive en `docs/pruebas/registro_pruebas.md`, con una columna **Antes** (sobre `original/`) y una **Después** (sobre `refactorizado/`). Cada resultado es *Pasa* o *No pasa*, con la fecha de ejecución y, si falla, lo que mostró el programa.
- Los casos que dependen de «hoy» se comprueban con la fecha del día en que se ejecutan; se anota esa fecha.

---

## 12. Otras reglas

- **`original/` no se toca.** Un PR que la modifique se rechaza.
- **`.gitkeep`:** al agregar el primer archivo real a una carpeta, se borra su `.gitkeep` en el mismo commit.
- **Archivos generados:** `out/`, `datos/`, `*.class` y archivos del IDE no se suben.
- **Protección de ramas:** `main` y `develop` no aceptan push directo y piden 1 aprobación. Si GitHub dejara de permitirlo, la regla se sigue cumpliendo a mano y el revisor rechaza cualquier PR que no la respete.
