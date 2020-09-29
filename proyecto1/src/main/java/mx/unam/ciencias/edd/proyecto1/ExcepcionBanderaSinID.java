package mx.unam.ciencias.edd.proyecto1;

/**
 * Clase para excepciones de bandera invalidas
 */

public class ExcepcionBanderaSinID extends Exception {

    /**
     * Constructor por omisión
     */
    public ExcepcionBanderaSinID(){}

    /**
     * Constructor con mensaje
     * @param mensaje el mensaje de la excepcion
     */
    public ExcepcionBanderaSinID(String mensaje) {
        super(mensaje);
    }
}