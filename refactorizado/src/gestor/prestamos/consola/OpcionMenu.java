package gestor.prestamos.consola;

/**
 * Representa una opción ejecutable del menú principal de la consola.
 * Cada implementación encapsula la descripción mostrada al usuario y la acción a realizar.
 */
public interface OpcionMenu {

    /**
     * Devuelve el texto descriptivo de la opción que se muestra en el menú.
     *
     * @return descripción de la opción
     */
    String descripcion();

    /**
     * Ejecuta la acción correspondiente a esta opción.
     *
     * @return {@code true} si la aplicación debe finalizar tras ejecutarla, {@code false} en caso contrario
     */
    boolean ejecutar();
}