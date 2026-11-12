# CP-03-02
* **Precondición:** Taladro disponible
* **Entrada:** 3, 1, Ana, (Enter), (Enter)
* **Resultado esperado:** Inicio hoy y límite hoy + 7 días
* **Nota de ejecución:** Este caso depende de la fecha del día en que se ejecute («hoy»). Al verificar el resultado en `prestamos.csv`, comprobar que `fecha_prestamo` sea la fecha actual del sistema y `fecha_limite` sea exactamente 7 días después