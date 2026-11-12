package gestor.prestamos.consola;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Collection;
import java.util.Objects;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Lectura de datos desde la consola con validación. Repite la petición hasta
 * obtener un valor correcto.
 */
public final class Entrada {

    /** Formato {@code AAAA-MM-DD}; los grupos 1, 2 y 3 son el año, el mes y el día. */
    private static final Pattern FORMATO_FECHA = Pattern.compile("(\\d{4})-(\\d{2})-(\\d{2})");

    private final Scanner scanner;

    /**
     * Crea una entrada que lee del escáner indicado.
     *
     * @param scanner fuente de las líneas introducidas por el usuario; no puede ser nula
     */
    public Entrada(Scanner scanner) {
        this.scanner = Objects.requireNonNull(scanner, "scanner");
    }

    /**
     * Pide un texto no vacío.
     *
     * @param mensaje texto que se muestra antes de leer
     * @return el texto introducido, sin espacios sobrantes
     */
    public String pedirTexto(String mensaje) {
        while (true) {
            String valor = leer(mensaje).trim();
            if (!valor.isEmpty()) {
                return valor;
            }
            System.out.println("  El campo no puede estar vacío.");
        }
    }

    /**
     * Pide un número entero que debe pertenecer a un conjunto de opciones válidas.
     *
     * @param mensaje texto que se muestra antes de leer
     * @param validas números aceptados
     * @return el número elegido
     */
    public int pedirOpcion(String mensaje, Collection<Integer> validas) {
        while (true) {
            String texto = leer(mensaje).trim();
            try {
                int numero = Integer.parseInt(texto);
                if (validas.contains(numero)) {
                    return numero;
                }
            } catch (NumberFormatException e) {
                // El texto no es un número: se informa abajo y se vuelve a pedir.
            }
            System.out.println("  Opción no válida.");
        }
    }

    /**
     * Pide una fecha en formato {@code AAAA-MM-DD}. Si el usuario pulsa Enter y hay un valor
     * por defecto, se devuelve ese valor.
     *
     * @param mensaje texto que se muestra antes de leer
     * @param valorPorDefecto fecha que se usa al pulsar Enter, o {@code null} si no hay
     * @return la fecha introducida o el valor por defecto
     */
    // ===== Técnica 7 · Replace Exception with Test (ISS-11) =====
    // Antes: pedirFecha() llamaba a LocalDate.parse() dentro de un try/catch y usaba la
    // DateTimeParseException para saber si el usuario había escrito mal la fecha.
    // Cambio: un error de escritura es un caso esperado, no excepcional; ahora se comprueba
    // primero con esFechaValida() y solo se convierte el texto cuando la fecha es válida.
    // Se aceptan y rechazan las mismas fechas y el mensaje de error no cambia.
    public LocalDate pedirFecha(String mensaje, LocalDate valorPorDefecto) {
        while (true) {
            String texto = leer(mensaje).trim();
            if (texto.isEmpty() && valorPorDefecto != null) {
                return valorPorDefecto;
            }
            if (esFechaValida(texto)) {
                return LocalDate.parse(texto);
            }
            System.out.println("  Formato inválido. Usa AAAA-MM-DD.");
        }
    }

    /**
     * Indica si el texto es una fecha {@code AAAA-MM-DD} que existe en el calendario.
     * Acepta y rechaza los mismos textos que {@link LocalDate#parse(CharSequence)}.
     *
     * @param texto texto introducido por el usuario, sin espacios sobrantes
     * @return {@code true} si el formato es correcto, el mes está entre 1 y 12
     *         y el día existe en ese mes
     */
    private static boolean esFechaValida(String texto) {
        Matcher partes = FORMATO_FECHA.matcher(texto);
        if (!partes.matches()) {
            return false;
        }
        int anio = Integer.parseInt(partes.group(1));
        int mes = Integer.parseInt(partes.group(2));
        int dia = Integer.parseInt(partes.group(3));
        boolean mesExiste = mes >= 1 && mes <= 12;
        return mesExiste && YearMonth.of(anio, mes).isValidDay(dia);
    }

    private String leer(String mensaje) {
        System.out.print(mensaje);
        return scanner.nextLine();
    }
}