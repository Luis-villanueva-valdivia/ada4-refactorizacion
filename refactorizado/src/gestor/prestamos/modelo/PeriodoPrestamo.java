package gestor.prestamos.modelo;

import java.time.LocalDate;

// ===== Técnica 8 · Introduce Parameter Object (ISS-12) =====
// Antes: la fecha de préstamo y la fecha límite viajaban siempre juntas como dos
// LocalDate sueltos en MenuConsola, GestorPrestamos.prestar(), RepositorioCsv y el
// constructor de Prestamo, que además validaba entre ambas.
// Cambio: se agrupan en este objeto, que también valida que existan y que la fecha
// límite no sea anterior al préstamo, con los mismos mensajes de error que antes.
/**
 * Periodo de un préstamo: desde la fecha en que se presta la herramienta hasta la
 * fecha máxima de devolución.
 *
 * @param inicio fecha en que se presta la herramienta; no puede ser nula
 * @param limite fecha máxima de devolución; no puede ser nula ni anterior a {@code inicio}
 */
public record PeriodoPrestamo(LocalDate inicio, LocalDate limite) {

    /**
     * Crea el periodo y comprueba que las fechas sean válidas.
     *
     * @throws IllegalArgumentException si falta alguna fecha o la fecha límite es
     *         anterior a la fecha de préstamo
     */
    public PeriodoPrestamo {
        if (inicio == null || limite == null) {
            throw new IllegalArgumentException("Las fechas de préstamo y límite son obligatorias");
        }
        if (limite.isBefore(inicio)) {
            throw new IllegalArgumentException("La fecha límite no puede ser anterior al préstamo");
        }
    }
}
