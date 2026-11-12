# Registro de pruebas — Gestor de Préstamos

**Issue:** ISS-04 · Ejecutar las pruebas del Anexo A sobre el proyecto original  
**Fecha de ejecución:** 11/11/2026  
**Versión probada:** `original/`  
**Resultado global:** 44 de 44 casos pasan.

Este registro documenta la línea base del programa original. Los 44 casos se ejecutaron sobre `original/` el 11/11/2026. Durante la revisión del 12/11/2026 se detectó que CP-03-04 y CP-04-08 no tenían precondiciones que permitieran comprobar sus resultados esperados. Ese mismo día se corrigieron sus archivos de precondición (PR #28) y se volvieron a ejecutar. La columna **Después (refactorizado)** se deja vacía para que sea completada en la ISS-15.

## Pruebas generales

| ID | Precondición | Entrada | Resultado esperado | Antes (original) | Después (refactorizado) |
|---|---|---|---|---|---|
| CP-G-01 | Ninguna | `9`, `abc`, entrada vacía y luego `0` | Muestra «Opción no válida.» para cada entrada inválida y vuelve a pedir la opción. | Pasa — 11/11/2026 | |
| CP-G-02 | Ninguna; cerrar la entrada estándar (stdin). | Fin de entrada (cerrar stdin). | Muestra «Entrada finalizada» y termina sin errores. | Pasa — 11/11/2026 | |
| CP-G-03 | Ninguna | Primera ejecución: `2`, `Taladro`, `0`; reiniciar y segunda ejecución: `1`. | Taladro aparece en el listado después de reiniciar el programa. | Pasa — 11/11/2026 | |

## Opción 0 — Salir

| ID | Precondición | Entrada | Resultado esperado | Antes (original) | Después (refactorizado) |
|---|---|---|---|---|---|
| CP-00-01 | Ninguna | `0` | Muestra «¡Hasta luego!» y el programa termina. | Pasa — 11/11/2026 | |

## Opción 1 — Consultar herramientas

| ID | Precondición | Entrada | Resultado esperado | Antes (original) | Después (refactorizado) |
|---|---|---|---|---|---|
| CP-01-01 | Sin herramientas | `1` | Muestra «No hay herramientas registradas». | Pasa — 11/11/2026 | |
| CP-01-02 | Taladro activa; Escalera dada de baja | `1` | Taladro: Disponible. Escalera: Dada de baja. | Pasa — 11/11/2026 | |
| CP-01-03 | Taladro prestada a Ana hasta `2026-11-12` | `1` | Taladro: «Prestada a Ana (hasta 2026-11-12)». | Pasa — 11/11/2026 | |

## Opción 2 — Registrar herramientas

| ID | Precondición | Entrada | Resultado esperado | Antes (original) | Después (refactorizado) |
|---|---|---|---|---|---|
| CP-02-01 | Ninguna | `2`, `Taladro` | Muestra «añadida con ID 1» y guarda `1,Taladro,false`. | Pasa — 11/11/2026 | |
| CP-02-02 | Ninguna | `2`, entrada vacía, `Escalera` | Muestra «El campo no puede estar vacío» y después añade la herramienta con ID 1. | Pasa — 11/11/2026 | |
| CP-02-03 | Ninguna | `2`, entrada con solo espacios, `Escalera` | Se comporta igual que CP-02-02: rechaza el campo vacío y después añade la herramienta con ID 1. | Pasa — 11/11/2026 | |
| CP-02-04 | Dos herramientas existentes | `2`, `Sierra` | La nueva herramienta recibe el ID 3. | Pasa — 11/11/2026 | |
| CP-02-05 | Ninguna | `2`, `  Escalera  ` | Se muestra el nombre «Escalera» sin espacios sobrantes. | Pasa — 11/11/2026 | |

## Opción 3 — Registrar préstamos

| ID | Precondición | Entrada | Resultado esperado | Antes (original) | Después (refactorizado) |
|---|---|---|---|---|---|
| CP-03-01 | Sin herramientas disponibles | `3` | Muestra «No hay herramientas disponibles para prestar». | Pasa — 11/11/2026 | |
| CP-03-02 | Taladro disponible | `3`, `1`, `Ana`, Enter, Enter | La fecha de inicio es hoy (`2026-11-11`) y la fecha límite es hoy + 7 días (`2026-11-18`). | Pasa — 11/11/2026 | |
| CP-03-03 | Taladro disponible | `3`, `1`, `Ana`, `2026-11-01`, `2026-11-08` | Se guardan exactamente las fechas indicadas. | Pasa — 11/11/2026 | |
| CP-03-04 | Taladro prestada; Escalera disponible. | `3` y después intentar introducir el ID `1` | Taladro no aparece; si se introduce su ID: «Opción no válida.». | Pasa — 12/11/2026: solo lista «2. Escalera» y con el ID `1` muestra «Opción no válida.». | |
| CP-03-05 | Taladro disponible | `3`, `1`, entrada vacía | Muestra «El campo no puede estar vacío». | Pasa — 11/11/2026 | |
| CP-03-06 | Taladro disponible | `3`, `1`, `Ana`, `01/11/2026` | Muestra «Formato inválido. Usa AAAA-MM-DD». | Pasa — 11/11/2026 | |
| CP-03-07 | Taladro disponible | `3`, `1`, `Ana`, `2026-11-05`, `2026-11-01` | Muestra «La fecha límite no puede ser anterior al préstamo» y no guarda el préstamo. | Pasa — 11/11/2026 | |
| CP-03-08 | Taladro disponible | `3`, `1`, `Ana`, `2026-11-05`, `2026-11-05` | Acepta el préstamo con fecha límite igual a la fecha de inicio. | Pasa — 11/11/2026 | |

## Opción 4 — Registrar devoluciones

| ID | Precondición | Entrada | Resultado esperado | Antes (original) | Después (refactorizado) |
|---|---|---|---|---|---|
| CP-04-01 | Sin préstamos activos | `4` | Muestra «No hay préstamos pendientes de devolución». | Pasa — 11/11/2026 | |
| CP-04-02 | Fecha límite `2026-11-08` | `4`, `1`, `2026-11-07` | Muestra «Devuelta a tiempo, sin penalización». | Pasa — 11/11/2026 | |
| CP-04-03 | Fecha límite `2026-11-08` | `4`, `1`, `2026-11-11` | Muestra «Retraso de 3 día(s). Penalización: 6.00 €». | Pasa — 11/11/2026 | |
| CP-04-04 | Fecha límite `2026-11-08` | `4`, `1`, `2026-11-08` | Sin retraso; muestra «Devuelta a tiempo, sin penalización». | Pasa — 11/11/2026 | |
| CP-04-05 | Fecha de inicio `2026-11-05` | `4`, `1`, `2026-11-01` | Muestra «La devolución no puede ser anterior al préstamo» y el préstamo sigue activo. | Pasa — 11/11/2026 | |
| CP-04-06 | Préstamo activo con ID 1 | `4`, `5` | Muestra «Opción no válida». | Pasa — 11/11/2026 | |
| CP-04-07 | Préstamo activo; fecha de préstamo anterior o igual al día de ejecución | `4`, `1`, Enter | La fecha de devolución corresponde a hoy (`2026-11-11`); el retraso se calcula con respecto a la fecha límite del préstamo. | Pasa — 11/11/2026 | |
| CP-04-08 | Préstamo ya devuelto (`fecha_devolucion` 2026-11-05). | `4` | El préstamo devuelto no aparece en la lista; si no hay otros préstamos activos, muestra «No hay préstamos pendientes de devolución.». | Pasa — 12/11/2026: muestra «No hay préstamos pendientes de devolución.». | |

## Opción 5 — Consultar préstamos activos

| ID | Precondición | Entrada | Resultado esperado | Antes (original) | Después (refactorizado) |
|---|---|---|---|---|---|
| CP-05-01 | Sin préstamos activos | `5` | Muestra «No hay préstamos activos». | Pasa — 11/11/2026 | |
| CP-05-02 | Fecha límite `2026-12-01` | `5` | Muestra «Al día, vence 2026-12-01». | Pasa — 11/11/2026 | |
| CP-05-03 | Fecha límite ajustada a `2026-11-05` (seis días antes de la ejecución del `2026-11-11`) | `5` | Muestra «ATRASADO 6 día(s), penalización estimada 12.00 €». | Pasa — 11/11/2026 | |
| CP-05-04 | Préstamo devuelto | `5` | El préstamo devuelto no aparece en la lista. | Pasa — 11/11/2026 | |

## Opción 6 — Consultar devoluciones

| ID | Precondición | Entrada | Resultado esperado | Antes (original) | Después (refactorizado) |
|---|---|---|---|---|---|
| CP-06-01 | Sin devoluciones | `6` | Muestra «No hay devoluciones registradas». | Pasa — 11/11/2026 | |
| CP-06-02 | Dos devoluciones: una con 3 días de retraso y otra con 0 días; la primera tiene penalización de 6.00 € | `6` | Muestra dos líneas y «Total de penalizaciones cobradas: 6.00 €». | Pasa — 11/11/2026 | |
| CP-06-03 | Un préstamo activo y uno devuelto | `6` | Solo aparece el préstamo devuelto. | Pasa — 11/11/2026 | |

## Opción 7 — Dar de baja herramientas

| ID | Precondición | Entrada | Resultado esperado | Antes (original) | Después (refactorizado) |
|---|---|---|---|---|---|
| CP-07-01 | Todas las herramientas dadas de baja | `7` | Muestra «No hay herramientas que dar de baja». | Pasa — 11/11/2026 | |
| CP-07-02 | Escalera disponible | `7`, `2` | Muestra «dada de baja. Su historial se conserva»; guarda `true` y deja de aparecer en la opción 3. | Pasa — 11/11/2026 | |
| CP-07-03 | Taladro prestada | `7`, `1` | Muestra «No se puede dar de baja: la herramienta está prestada» y no realiza cambios. | Pasa — 11/11/2026 | |
| CP-07-04 | Escalera ya dada de baja | `7` y después intentar introducir el ID `2` | Escalera no aparece en la lista; si se introduce su ID, muestra «Opción no válida». | Pasa — 11/11/2026 | |
| CP-07-05 | Herramienta con préstamos antiguos | `7`, `1` | Los préstamos anteriores permanecen en el historial. | Pasa — 11/11/2026 | |

## Opción 8 — Reactivar herramientas

| ID | Precondición | Entrada | Resultado esperado | Antes (original) | Después (refactorizado) |
|---|---|---|---|---|---|
| CP-08-01 | Ninguna herramienta dada de baja | `8` | Muestra «No hay herramientas dadas de baja». | Pasa — 11/11/2026 | |
| CP-08-02 | Escalera dada de baja | `8`, `2` | Muestra «reactivada y disponible para préstamo» y guarda `false`. | Pasa — 11/11/2026 | |
| CP-08-03 | Taladro activa | `8` y después intentar introducir el ID `1` | Taladro no aparece en la lista; si se introduce su ID, muestra «Opción no válida». | Pasa — 11/11/2026 | |
| CP-08-04 | Herramienta con historial | `8`, `1` | El historial consultado en la opción 6 no cambia. | Pasa — 11/11/2026 | |

## Resumen

| Resultado | Cantidad |
|---|---:|
| Pasa | 44 |
| No pasa | 0 |
| Total ejecutado | 44 |

**Observaciones:**
- Las pruebas se ejecutaron sobre `original/`; no se modificó el proyecto original.
- CP-G-02 se ejecutó cerrando la entrada estándar para comprobar el comportamiento ante EOF.
- CP-G-03 se ejecutó en dos ejecuciones consecutivas, conservando los archivos de `datos/` entre ambas.
- CP-03-04 y CP-04-08 se corrigieron en el PR #28 y se volvieron a ejecutar el 12/11/2026.
- CP-G-01 registra el mensaje exacto «Opción no válida.»; el `LEEME.md` del caso debe sincronizarse por su responsable de ISS-03.
- CP-03-02, CP-04-07 y CP-05-03 dependen de la fecha de ejecución. Se consideró el día 11/11/2026; para CP-05-03 se utilizó como fecha límite `2026-11-05`, seis días antes.
- La columna **Después (refactorizado)** permanece vacía para la ISS-15.