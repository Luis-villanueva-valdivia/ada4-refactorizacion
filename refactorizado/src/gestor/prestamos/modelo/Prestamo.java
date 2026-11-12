package gestor.prestamos.modelo;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * Representa el préstamo de una herramienta a una persona, con sus fechas,
 * el retraso acumulado y la penalización correspondiente.
 */
public final class Prestamo {
     // ===== Técnica 4  Move Method (ISS-08) =====
    // Antes: GestorPrestamos dónde estaba el valor
    // Cambio: Prestamo a dónde se movió y por qué el Gestor la conserva
    /** Penalización en euros por cada día de retraso. */
    public static final double PENALIZACION_POR_DIA = 2.0;
    private final int id;
    private final Herramienta herramienta;
    private final String prestatario;
    private final PeriodoPrestamo periodo;
    private LocalDate fechaDevolucion;
    private int diasRetraso;
    private double penalizacion;

    // ===== Técnica 8 · Introduce Parameter Object (ISS-12) =====
    // Antes: el constructor recibía fechaPrestamo y fechaLimite como dos parámetros
    // y comprobaba aquí que existieran y que el límite no fuera anterior al préstamo.
    // Cambio: recibe un PeriodoPrestamo, que ya trae esa validación, y lo guarda en
    // un solo campo. getFechaPrestamo() y getFechaLimite() se conservan y lo consultan.
    /**
     * Crea un préstamo. Se usa tanto para préstamos nuevos como para restaurar
     * préstamos guardados; en un préstamo nuevo, la fecha de devolución es nula.
     *
     * @param id identificador único, mayor que cero
     * @param herramienta herramienta prestada; no puede ser nula
     * @param prestatario persona que recibe la herramienta; no puede estar vacío
     * @param periodo fechas de préstamo y límite, ya validadas; no puede ser nulo
     * @param fechaDevolucion fecha real de devolución, o nula si sigue activo
     * @param diasRetraso días de retraso registrados en la devolución; no negativo
     * @param penalizacion penalización cobrada en euros; no negativa
     * @throws IllegalArgumentException si alguno de los datos no es válido
     */
    public Prestamo(int id, Herramienta herramienta, String prestatario, PeriodoPrestamo periodo,
                    LocalDate fechaDevolucion, int diasRetraso, double penalizacion) {
        if (id <= 0) {
            throw new IllegalArgumentException("El id del préstamo debe ser positivo");
        }
        if (herramienta == null) {
            throw new IllegalArgumentException("La herramienta es obligatoria");
        }
        if (prestatario == null || prestatario.isBlank()) {
            throw new IllegalArgumentException("El prestatario es obligatorio");
        }
        if (periodo == null) {
            throw new IllegalArgumentException("Las fechas de préstamo y límite son obligatorias");
        }
        if (fechaDevolucion != null && fechaDevolucion.isBefore(periodo.inicio())) {
            throw new IllegalArgumentException("La devolución no puede ser anterior al préstamo");
        }
        if (diasRetraso < 0 || penalizacion < 0) {
            throw new IllegalArgumentException("El retraso y la penalización no pueden ser negativos");
        }
        this.id = id;
        this.herramienta = herramienta;
        this.prestatario = prestatario.trim();
        this.periodo = periodo;
        this.fechaDevolucion = fechaDevolucion;
        this.diasRetraso = diasRetraso;
        this.penalizacion = penalizacion;
    }

    /**
     * Indica si el préstamo sigue abierto, es decir, si la herramienta no se ha devuelto.
     *
     * @return {@code true} si no hay fecha de devolución
     */
    public boolean estaActivo() {
        return fechaDevolucion == null;
    }

    /**
     * Calcula la situación del préstamo en la fecha indicada.
     *
     * @param hoy fecha de referencia
     * @return {@link EstadoPrestamo#DEVUELTO} si ya se devolvió;
     *         {@link EstadoPrestamo#ATRASADO} si está abierto y la fecha límite ha pasado;
     *         {@link EstadoPrestamo#ACTIVO} en otro caso
     */
    public EstadoPrestamo estado(LocalDate hoy) {
        if (!estaActivo()) {
            return EstadoPrestamo.DEVUELTO;
        }
        return hoy.isAfter(periodo.limite()) ? EstadoPrestamo.ATRASADO : EstadoPrestamo.ACTIVO;
    }

    /**
     * Devuelve los días de retraso a fecha de referencia. Si el préstamo ya se devolvió,
     * devuelve el retraso registrado; si sigue abierto, lo calcula hasta la fecha indicada.
     *
     * @param hoy fecha de referencia para préstamos activos
     * @return número de días de retraso, nunca negativo
     */
    public long getDiasRetrasoA(LocalDate hoy) {
        if (!estaActivo()) {
            return diasRetraso;
        }
        return Math.max(0, ChronoUnit.DAYS.between(periodo.limite(), hoy));
    }

    /**
     * Calcula la penalización estimada a fecha de referencia para un préstamo abierto.
     *
     * @param hoy fecha de referencia
     * @param precioPorDia importe en euros por cada día de retraso
     * @return penalización estimada en euros
     */
    public double calcularPenalizacion(LocalDate hoy, double precioPorDia) {
        return getDiasRetrasoA(hoy) * precioPorDia;
    }

       // ===== Técnica 6 · Remove Parameter (ISS-10) =====
    // Antes: registrarDevolucion() recibía penalizacionPorDia,
    // pero no utilizaba ese parámetro.
    // Cambio: se eliminó penalizacionPorDia porque el método
    // solamente necesita la fecha para registrar la devolución.
        // ===== Técnica 4 · Move Method (ISS-08) =====
    // Antes: este método solo guardaba la fecha de devolución; el retraso y la
    // penalización los calculaba GestorPrestamos.devolver().
    // Cambio: ahora, después de guardar la fecha, llama a registrarRetrasoYPenalizacion()
    // para que el mismo préstamo calcule y guarde esos valores.
    /**
     * Registra la devolución de la herramienta y guarda los días de retraso
     * y la penalización que le corresponden.
     * @param fecha fecha real de devolución; no puede ser anterior al préstamo
     * @throws IllegalStateException si el préstamo ya estaba devuelto
     * @throws IllegalArgumentException si la fecha es nula o anterior al préstamo
     */
    public void registrarDevolucion(LocalDate fecha) {
        if (!estaActivo()) {
            throw new IllegalStateException("El préstamo ya fue devuelto");
        }
        if (fecha == null) {
            throw new IllegalArgumentException("La fecha de devolución es obligatoria");
        }
        if (fecha.isBefore(periodo.inicio())) {
            throw new IllegalArgumentException("La devolución no puede ser anterior al préstamo");
        }
        this.fechaDevolucion = fecha;
        registrarRetrasoYPenalizacion(fecha);
    }

    // ===== Técnica 4 · Move Method (ISS-08) =====
    // Antes: este cálculo estaba en GestorPrestamos.devolver(), que le pedía la
    // fecha límite al préstamo y luego le ponía el retraso y la penalización con setters.
    // Cambio: se movió aquí porque solo usa datos del préstamo; la fórmula es la misma.
    // Como ya nadie los usaba, se eliminaron setDiasRetraso() y setPenalizacion().
    /**
     * Calcula los días de retraso de la devolución y la penalización que le
     * corresponde, y los guarda en el préstamo.
     *
     * @param fecha fecha real de devolución
     */
    private void registrarRetrasoYPenalizacion(LocalDate fecha) {
        long retraso = Math.max(0, ChronoUnit.DAYS.between(periodo.limite(), fecha));
        this.diasRetraso = (int) retraso;
        this.penalizacion = Math.round(retraso * PENALIZACION_POR_DIA * 100.0) / 100.0;
    }

    /**
     * Devuelve el identificador del préstamo.
     *
     * @return el id
     */
    public int getId() {
        return id;
    }

    /**
     * Devuelve la herramienta prestada.
     *
     * @return la herramienta
     */
    public Herramienta getHerramienta() {
        return herramienta;
    }

    /**
     * Devuelve el nombre de la persona que recibe la herramienta.
     *
     * @return el prestatario
     */
    public String getPrestatario() {
        return prestatario;
    }

    /**
     * Devuelve la fecha en que se prestó la herramienta.
     *
     * @return fecha de préstamo
     */
    public LocalDate getFechaPrestamo() {
        return periodo.inicio();
    }

    /**
     * Devuelve la fecha máxima de devolución.
     *
     * @return fecha límite
     */
    public LocalDate getFechaLimite() {
        return periodo.limite();
    }

    /**
     * Devuelve la fecha real de devolución.
     *
     * @return fecha de devolución, o {@code null} si el préstamo sigue activo
     */
    public LocalDate getFechaDevolucion() {
        return fechaDevolucion;
    }

    /**
     * Devuelve los días de retraso registrados en la devolución.
     *
     * @return días de retraso, 0 si aún no se ha devuelto
     */
    public int getDiasRetraso() {
        return diasRetraso;
    }

    /**
     * Devuelve la penalización registrada en la devolución.
     *
     * @return penalización en euros, 0 si aún no se ha devuelto
     */
    public double getPenalizacion() {
        return penalizacion;
    }
}
