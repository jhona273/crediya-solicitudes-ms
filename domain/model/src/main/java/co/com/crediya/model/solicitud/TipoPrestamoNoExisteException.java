package co.com.crediya.model.solicitud;

public class TipoPrestamoNoExisteException extends RuntimeException {

    public TipoPrestamoNoExisteException(String nombre) {
        super("El tipo de préstamo no existe: " + nombre);
    }
}
