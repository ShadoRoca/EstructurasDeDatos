package mx.unam.ciencias.edd.proyecto3;

/**
 * Clase para excepciones de banderas invalidas
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