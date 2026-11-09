# ADA 4 · Refactorización del gestor de préstamos

Actividad de Aprendizaje 4 de **Construcción de Software** (Facultad de Matemáticas, UADY).

El programa es un gestor de préstamos de herramientas por consola, en Java, que guarda sus datos en CSV. Funciona bien, pero su diseño es difícil de leer y mantener. El objetivo es aplicar **diez técnicas de refactorización** sin cambiar el comportamiento que percibe el usuario, y comprobarlo con las pruebas de caja negra del Anexo A antes y después.

## Equipo

| Integrante | Técnicas |
|---|---|
| Luis ([@Luis-villanueva-valdivia](https://github.com/Luis-villanueva-valdivia)) | 3 Introduce Explaining Variable · 4 Move Method · coordinación |
| Paola | 1 Extract Method · 2 Replace Temp with Query |
| Oswaldo | 5 Separate Query from Modifier · 6 Remove Parameter |
| José | 7 Replace Exception with Test · 8 Introduce Parameter Object |
| Katheri | 9 Decompose Conditional · 10 Replace Conditional with Polymorphism |

## Estructura

```
├── original/        # proyecto tal como se entregó (no se modifica)
├── refactorizado/   # proyecto con las diez técnicas aplicadas
├── pruebas/casos/   # precondición (CSV) y entradas de cada caso del Anexo A
├── docs/
│   ├── convenciones.md   # cómo trabajamos: ramas, commits, PR, comentarios de técnica
│   ├── planeacion.md     # issues, responsables, dependencias y fechas
│   ├── pruebas/          # registro de pruebas antes/después y PDF de entrega
│   └── actividad/        # PDF con las instrucciones
└── .github/         # plantilla de pull request
```

## Requisitos

- JDK 17 o superior (recomendado 21 LTS)
- Git

## Compilar y ejecutar

Desde `original/` o desde `refactorizado/`:

```bash
javac -encoding UTF-8 --release 17 -d out -sourcepath src src/gestor/prestamos/Main.java
java -Dstdout.encoding=UTF-8 -cp out gestor.prestamos.Main datos
```

El último argumento (`datos`) es la carpeta donde el programa lee y escribe `herramientas.csv` y `prestamos.csv`. Si no existe, se crea al guardar.

> En Windows, si los acentos se ven mal en la terminal, ejecutar `chcp 65001` antes.

## Técnicas aplicadas

| # | Técnica | Archivo | Issue |
|---|---|---|---|
| 1 | Extract Method | `consola/MenuConsola.java` | ISS-05 |
| 2 | Replace Temp with Query | `consola/MenuConsola.java` | ISS-06 |
| 3 | Introduce Explaining Variable | `persistencia/RepositorioCsv.java` | ISS-07 |
| 4 | Move Method | `servicio/GestorPrestamos.java` → `modelo/Prestamo.java` | ISS-08 |
| 5 | Separate Query from Modifier | `servicio/GestorPrestamos.java` | ISS-09 |
| 6 | Remove Parameter | `modelo/Prestamo.java` | ISS-10 |
| 7 | Replace Exception with Test | `consola/Entrada.java` | ISS-11 |
| 8 | Introduce Parameter Object | `servicio/GestorPrestamos.java` y `modelo/Prestamo.java` | ISS-12 |
| 9 | Decompose Conditional | `consola/MenuConsola.java` | ISS-13 |
| 10 | Replace Conditional with Polymorphism | `consola/MenuConsola.java` | ISS-14 |

En el código, cada técnica tiene un comentario `===== Técnica N · Nombre (ISS-XX) =====` donde se aplicó.

## Contribuir

Ramas, commits, pull requests, revisión cruzada y comentarios de técnica: [docs/convenciones.md](docs/convenciones.md).
