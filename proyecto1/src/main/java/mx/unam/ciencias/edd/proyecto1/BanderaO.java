package mx.unam.ciencias.edd.proyecto1;

/**
 * Clase para identificar bandera -o así como su identificador y comprobar si es válida
 */

public class BanderaO {

    /**
     * Nos dice si hay una bandera -o
     * @param arreglo el arreglo del main
     * @return <code>true</code> si hay bandera -o
     *         <code>false</code> si no hay bandera -o 
     */
    public static boolean hayBanderaO(String[] arreglo) {
        for (String string : arreglo) {
            if (string.equals("-o")) {
                return true;
            }
        }
        return false;
    }

    /**
     * Nos regresa el identificador de la bandera -o
     * @param arreglo el arreglo del main
     * @return El identificador de -o
     * @throws ExcepcionBanderaSinID Si no hay bandera identificador
     */
    public static String RegresaIdentificador(String[] arreglo) throws ExcepcionBanderaSinID {
        for (int i = 0; i < arreglo.length; i++) {
            if (arreglo[i].equals("-o")) {
                if (i == arreglo.length - 1) {
                    throw new ExcepcionBanderaSinID("No hay un identificador para hacer la operacion -o");
                } else {
                    return arreglo[i + 1];
                }
            }
        }
        throw new ExcepcionBanderaSinID("No hay bandera -o");
    }

    /**
     * Nos dice si hay identificador después de una bandera -o
     * @param arreglo arreglo de strings
     * @return  <code>true</code> si hay identificador después de una bandera -o
     *          <code>false</code> si no hay identificador después de una badnera -o
     */
    public static boolean hayID(String[] arreglo) {
        for (int i = 0; i < arreglo.length; i++) {
            if (arreglo[i].equals("-o")) {
                if (i == arreglo.length - 1) {
                    return false;
                } else {
                    return true;
                }
            }
        }
        return false;
    }


}