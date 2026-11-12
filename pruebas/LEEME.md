# Casos de Prueba — Gestor de Préstamos

Esta carpeta contiene los 44 casos de prueba funcionales (`pruebas/casos/CP-G-01` hasta `CP-08-04`) derivados del Anexo A. Cada subcarpeta incluye:
* `LEEME.md`: Precondición, entradas y resultado esperado del caso.
* `entrada.txt`: Flujo de entradas por línea para ejecutar la prueba de forma automatizada.
* `herramientas.csv` y `prestamos.csv`: Datos iniciales (solo en los casos que requieren precondición).

## Cómo ejecutar un caso de prueba

Antes de correr las pruebas, asegúrate de tener compilada la versión que deseas probar (`original/` o `refactorizado/`) dentro de una carpeta `out/`.

### En Linux / Mac / Git Bash
Desde la raíz del repositorio (ejemplo con `CP-04-03`):
```bash
rm -rf datos && mkdir datos && cp ./pruebas/casos/CP-04-03/*.csv datos/ 2>/dev/null
java -Dfile.encoding=UTF-8 -cp out gestor.prestamos.Main datos < ./pruebas/casos/CP-04-03/entrada.txt
```

### En Windows (PowerShell)
Desde la raíz del repositorio (ejemplo con `CP-04-03`):
```powershell
if (Test-Path datos) { Remove-Item -Recurse -Force datos }
New-Item -ItemType Directory -Path datos | Out-Null
Copy-Item .\pruebas\casos\CP-04-03\*.csv datos\ -ErrorAction SilentlyContinue
Get-Content .\pruebas\casos\CP-04-03\entrada.txt | java -Dfile.encoding=UTF-8 -cp out gestor.prestamos.Main datos
```

## Consideraciones especiales
* **Casos sin precondición:** No incluyen archivos `.csv`; el programa creará los archivos vacíos al iniciarse sobre la carpeta `datos/`.
* **Casos dependientes de la fecha actual (`CP-03-02`, `CP-04-07`, `CP-05-03`):** Utilizan la fecha del sistema («hoy»). Revisar el `LEEME.md` de cada uno para ajustar las fechas en `prestamos.csv` el día de la ejecución.
* **Cierre de entrada estándar (`CP-G-02`):** Su `entrada.txt` omite intencionalmente el `0` final para validar el mensaje `«Entrada finalizada.»`.
* **Reinicio y persistencia (`CP-G-03`):** Requiere ejecutar el programa dos veces consecutivas sobre la misma carpeta `datos/` sin borrarla entre ejecuciones.