# CP-04-07
* **Precondición:** Préstamo activo
* **Entrada:** 4, 1, (Enter)
* **Resultado esperado:** Fecha de devolución = hoy; retraso calculado según hoy
* **Nota de ejecución:** Este caso depende de la fecha actual («hoy»). Antes de ejecutarlo, asegúrate de que `fecha_prestamo` en `prestamos.csv` sea anterior o igual al día de hoy, y ajusta `fecha_limite` según si deseas probar devolución a tiempo o con retraso.