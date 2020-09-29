package mx.unam.ciencias.edd.proyecto1;

/**
 * Clase para identificar bandera -r
 */

public class BanderaR {

    /**
     * Nos dice si hay una bandera -r en los parámetros
     * @param arreglo arreglo del método main
     * @return <code>true</code> si hay bandera -r
     *         <code>false</code> si no hay bandera -r
     */
    public static boolean hayBanderaR(String[] arreglo) {
        for (String string : arreglo) {
            if (string.equals("-r")) {
                return true;
            }
        }
        return false;
    }
}