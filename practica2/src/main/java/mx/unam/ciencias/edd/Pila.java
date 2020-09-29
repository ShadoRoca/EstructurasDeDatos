package mx.unam.ciencias.edd;

/**
 * Clase para pilas genéricas.
 */
public class Pila<T> extends MeteSaca<T> {

    /**
     * Regresa una representación en cadena de la pila.
     * 
     * @return una representación en cadena de la pila.
     */
    @Override
    public String toString() {
        String regreso = "";

        if (cabeza == null)
            return regreso;
        Nodo m = cabeza;
        while (m != null) {

            regreso += m.elemento + "\n";
            m = m.siguiente;

        }
        return regreso;
    }

    /**
     * Agrega un elemento al tope de la pila.
     * 
     * @param elemento el elemento a agregar.
     * @throws IllegalArgumentException si <code>elemento</code> es
     *                                  <code>null</code>.
     */
    @Override
    public void mete(T elemento) {
        if (elemento == null) 
            throw new IllegalArgumentException();

            Nodo aMeter = new Nodo(elemento);
            if (cabeza == null) {
                cabeza = rabo = aMeter;
            } else {
                aMeter.siguiente = cabeza;
                cabeza = aMeter;
                
            }

        
    }
}
