package mx.unam.ciencias.edd;

import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * <p>
 * Clase genérica para listas doblemente ligadas.
 * </p>
 *
 * <p>
 * Las listas nos permiten agregar elementos al inicio o final de la lista,
 * eliminar elementos de la lista, comprobar si un elemento está o no en la
 * lista, y otras operaciones básicas.
 * </p>
 *
 * <p>
 * Las listas no aceptan a <code>null</code> como elemento.
 * </p>
 *
 * @param <T> El tipo de los elementos de la lista.
 */
public class Lista<T> implements Coleccion<T> {

    /* Clase interna privada para nodos. */
    private class Nodo {
        /* El elemento del nodo. */
        public T elemento;
        /* El nodo anterior. */
        public Nodo anterior;
        /* El nodo siguiente. */
        public Nodo siguiente;

        /* Construye un nodo con un elemento. */
        public Nodo(T elemento) {
            this.elemento = elemento;
            // Aquí va su código.
        }
    }

    /* Clase interna privada para iteradores. */
    private class Iterador implements IteradorLista<T> {
        /* El nodo anterior. */
        public Nodo anterior;
        /* El nodo siguiente. */
        public Nodo siguiente;

        /* Construye un nuevo iterador. */
        public Iterador() {
            anterior = null;
            siguiente = cabeza;
            // Aquí va su código.
        }

        /* Nos dice si hay un elemento siguiente. */
        @Override
        public boolean hasNext() {
            return siguiente != null;
            // Aquí va su código.
        }

        /* Nos da el elemento siguiente. */
        @Override
        public T next() {
            if (siguiente == null) {
                throw new NoSuchElementException();
            } else {
                anterior = siguiente;
                siguiente = siguiente.siguiente;
                return anterior.elemento;
            }
            // Aquí va su código.
        }

        /* Nos dice si hay un elemento anterior. */
        @Override
        public boolean hasPrevious() {
            return anterior != null;
            // Aquí va su código.
        }

        /* Nos da el elemento anterior. */
        @Override
        public T previous() {
            if (anterior == null) {
                throw new NoSuchElementException();
            } else {
                siguiente = anterior;
                anterior = anterior.anterior;
                return siguiente.elemento;
            }
            // Aquí va su código.
        }

        /* Mueve el iterador al inicio de la lista. */
        @Override
        public void start() {
            siguiente = cabeza;
            anterior = null;
            // Aquí va su código.
        }

        /* Mueve el iterador al final de la lista. */
        @Override
        public void end() {
            siguiente = null;
            anterior = rabo;
            // Aquí va su código.
        }
    }

    /* Primer elemento de la lista. */
    private Nodo cabeza;
    /* Último elemento de la lista. */
    private Nodo rabo;
    /* Número de elementos en la lista. */
    private int longitud;

    /**
     * Regresa la longitud de la lista. El método es idéntico a
     * {@link #getElementos}.
     * 
     * @return la longitud de la lista, el número de elementos que contiene.
     */
    public int getLongitud() {
        // Aquí va su código.
        return longitud;
    }

    /**
     * Regresa el número elementos en la lista. El método es idéntico a
     * {@link #getLongitud}.
     * 
     * @return el número elementos en la lista.
     */
    @Override
    public int getElementos() {
        // Aquí va su código.
        return longitud;
    }

    /**
     * Nos dice si la lista es vacía.
     * 
     * @return <code>true</code> si la lista es vacía, <code>false</code> en otro
     *         caso.
     */
    @Override
    public boolean esVacia() {
        return rabo == null;
        // Aquí va su código.
    }

    /**
     * Agrega un elemento a la lista. Si la lista no tiene elementos, el elemento a
     * agregar será el primero y último. El método es idéntico a
     * {@link #agregaFinal}.
     * 
     * @param elemento el elemento a agregar.
     * @throws IllegalArgumentException si <code>elemento</code> es
     *                                  <code>null</code>.
     */
    @Override
    public void agrega(T elemento) {
        if (elemento == null) {
            throw new IllegalArgumentException();
        }
        Nodo n = new Nodo(elemento);
        longitud++;
        if (rabo == null) {
            cabeza = rabo = n;
        } else {
            rabo.siguiente = n;
            n.anterior = rabo;
            rabo = n;
        }
    }

    /**
     * Agrega un elemento al final de la lista. Si la lista no tiene elementos, el
     * elemento a agregar será el primero y último.
     * 
     * @param elemento el elemento a agregar.
     * @throws IllegalArgumentException si <code>elemento</code> es
     *                                  <code>null</code>.
     */
    public void agregaFinal(T elemento) {
        if (elemento == null) {
            throw new IllegalArgumentException();
        }
        Nodo n = new Nodo(elemento);
        if (rabo == null) {
            cabeza = rabo = n;
            longitud = 1;
        } else {
            rabo.siguiente = n;
            n.anterior = rabo;
            rabo = n;
            longitud++;
        }
    }

    /**
     * Agrega un elemento al inicio de la lista. Si la lista no tiene elementos, el
     * elemento a agregar será el primero y último.
     * 
     * @param elemento el elemento a agregar.
     * @throws IllegalArgumentException si <code>elemento</code> es
     *                                  <code>null</code>.
     */
    public void agregaInicio(T elemento) {
        if (elemento == null) {
            throw new IllegalArgumentException();
        }
        Nodo n = new Nodo(elemento);
        if (rabo == null) {
            cabeza = rabo = n;
            longitud = 1;
        } else {
            longitud++;
            cabeza.anterior = n;
            n.siguiente = cabeza;
            cabeza = n;
        }
    }

    /**
     * Inserta un elemento en un índice explícito.
     *
     * Si el índice es menor o igual que cero, el elemento se agrega al inicio de la
     * lista. Si el índice es mayor o igual que el número de elementos en la lista,
     * el elemento se agrega al fina de la misma. En otro caso, después de mandar
     * llamar el método, el elemento tendrá el índice que se especifica en la lista.
     * 
     * @param i        el índice dónde insertar el elemento. Si es menor que 0 el
     *                 elemento se agrega al inicio de la lista, y si es mayor o
     *                 igual que el número de elementos en la lista se agrega al
     *                 final.
     * @param elemento el elemento a insertar.
     * @throws IllegalArgumentException si <code>elemento</code> es
     *                                  <code>null</code>.
     */
    public void inserta(int i, T elemento) {
        if (elemento == null) {
            throw new IllegalArgumentException();
        }
        if (i <= 0) {
            agregaInicio(elemento);
        } else if (i >= longitud) {
            agregaFinal(elemento);
        } else {
            longitud++;
            Nodo n = new Nodo(elemento);
            int j = 0;
            Nodo m = cabeza;
            while (j++ < i) {
                m = m.siguiente;
            }
            m.anterior.siguiente = n;
            n.anterior = m.anterior;
            n.siguiente = m;
            m.anterior = n;
        }
    }

    private Nodo busca(T elemento) {
        if (elemento == null) {
            return null;
        } else {
            Nodo m = cabeza;
            while (m != null) {
                if (m.elemento.equals(elemento)) {
                    return m;
                } else {
                    m = m.siguiente;
                }
            }
            return m;
        }
    }

    /**
     * Elimina un elemento de la lista. Si el elemento no está contenido en la
     * lista, el método no la modifica.
     * 
     * @param elemento el elemento a eliminar.
     */
    @Override
    public void elimina(T elemento) {
        Nodo m = busca(elemento);
        if (m == cabeza && cabeza == rabo) {
            cabeza = null;
            rabo = null;
            longitud = 0;
        } else if (m == rabo && rabo != cabeza) {
            longitud--;
            rabo.anterior.siguiente = null;
            rabo = rabo.anterior;
        } else if (m == cabeza && cabeza != rabo) {
            longitud--;
            cabeza.siguiente.anterior = null;
            cabeza = cabeza.siguiente;
        } else if (m != rabo && m != cabeza) {
            longitud--;
            m.siguiente.anterior = m.anterior;
            m.anterior.siguiente = m.siguiente;
        }
    }

    /**
     * Elimina el primer elemento de la lista y lo regresa.
     * 
     * @return el primer elemento de la lista antes de eliminarlo.
     * @throws NoSuchElementException si la lista es vacía.
     */
    public T eliminaPrimero() {
        if (cabeza == null) {
            throw new NoSuchElementException();
        }
        if (cabeza == rabo) {
            longitud = 0;
            T regreso = cabeza.elemento;
            cabeza = null;
            rabo = null;
            return regreso;
        } else {
            longitud--;
            T regreso = cabeza.elemento;
            cabeza.siguiente.anterior = null;
            cabeza = cabeza.siguiente;
            return regreso;
        }

        // Aquí va su código.
    }

    /**
     * Elimina el último elemento de la lista y lo regresa.
     * 
     * @return el último elemento de la lista antes de eliminarlo.
     * @throws NoSuchElementException si la lista es vacía.
     */
    public T eliminaUltimo() {
        if (cabeza == null) {
            throw new NoSuchElementException();
        }
        if (cabeza == rabo) {
            T regreso = cabeza.elemento;
            cabeza = null;
            rabo = null;
            longitud = 0;
            return regreso;
        } else {
            longitud--;
            T regreso = rabo.elemento;
            rabo.anterior.siguiente = null;
            rabo = rabo.anterior;
            return regreso;
        }
        // Aquí va su código.
    }

    /**
     * Nos dice si un elemento está en la lista.
     * 
     * @param elemento el elemento que queremos saber si está en la lista.
     * @return <code>true</code> si <code>elemento</code> está en la lista,
     *         <code>false</code> en otro caso.
     */
    @Override
    public boolean contiene(T elemento) {
        if (busca(elemento) != null) {
            return true;
        } else {
            return false;
        }

        // Aquí va su código.
    }

    /**
     * Regresa la reversa de la lista.
     * 
     * @return una nueva lista que es la reversa la que manda llamar el método.
     */
    public Lista<T> reversa() {
        Lista<T> reverse = new Lista<>();
        Nodo recorre = cabeza;
        while (recorre != null) {
            reverse.agregaInicio(recorre.elemento);
            recorre = recorre.siguiente;
        }
        return reverse;
    }

    // Aquí va su código.

    /**
     * Regresa una copia de la lista. La copia tiene los mismos elementos que la
     * lista que manda llamar el método, en el mismo orden.
     * 
     * @return una copiad de la lista.
     */
    public Lista<T> copia() {
        Lista<T> nuevaCopia = new Lista<>();
        Nodo recorre = cabeza;
        while (recorre != null) {
            nuevaCopia.agregaFinal(recorre.elemento);
            recorre = recorre.siguiente;
        }
        return nuevaCopia;
        // Aquí va su código.
    }

    /**
     * Limpia la lista de elementos, dejándola vacía.
     */
    @Override
    public void limpia() {
        cabeza = rabo = null;
        longitud = 0;
        // Aquí va su código.
    }

    /**
     * Regresa el primer elemento de la lista.
     * 
     * @return el primer elemento de la lista.
     * @throws NoSuchElementException si la lista es vacía.
     */
    public T getPrimero() {
        if (cabeza == null) {
            throw new NoSuchElementException();
        } else {
            return cabeza.elemento;
        }
        // Aquí va su código.
    }

    /**
     * Regresa el último elemento de la lista.
     * 
     * @return el primer elemento de la lista.
     * @throws NoSuchElementException si la lista es vacía.
     */
    public T getUltimo() {
        if (rabo == null) {
            throw new NoSuchElementException();
        } else {
            return rabo.elemento;
        }
        // Aquí va su código.
    }

    /**
     * Regresa el <em>i</em>-ésimo elemento de la lista.
     * 
     * @param i el índice del elemento que queremos.
     * @return el <em>i</em>-ésimo elemento de la lista.
     * @throws ExcepcionIndiceInvalido si <em>i</em> es menor que cero o mayor o
     *                                 igual que el número de elementos en la lista.
     */
    public T get(int i) {
        if (i < 0 || i >= longitud) {
            throw new ExcepcionIndiceInvalido();
        } else {
            Nodo m = cabeza;
            int j = 0;
            while (j++ < i) {
                m = m.siguiente;
            }
            return m.elemento;
        }
        // Aquí va su código.
    }

    /**
     * Regresa el índice del elemento recibido en la lista.
     * 
     * @param elemento el elemento del que se busca el índice.
     * @return el índice del elemento recibido en la lista, o -1 si el elemento no
     *         está contenido en la lista.
     */
    public int indiceDe(T elemento) {
        if (elemento == null) {
            throw new IllegalArgumentException();
        } else {
            int contador = 0;
            Nodo m = cabeza;
            while (m != null) {
                if (m.elemento.equals(elemento)) {
                    return contador;
                } else {
                    contador++;
                    m = m.siguiente;
                }
            }
            return -1;
        }
    }

    /**
     * Regresa una representación en cadena de la lista.
     * 
     * @return una representación en cadena de la lista.
     */
    @Override
    public String toString() {
        String list = "[";
        if (rabo == null) {
            return list + "]";
        } else {
            Nodo m = cabeza;
            while (m != null) {
                if (m != rabo) {
                    list = list + m.elemento + ", ";
                }
                if (m == rabo) {
                    list = list + m.elemento + "]";
                }
                m = m.siguiente;
            }
        }
        return list;
        // Aquí va su código.
    }

    /**
     * Nos dice si la lista es igual al objeto recibido.
     * 
     * @param objeto el objeto con el que hay que comparar.
     * @return <code>true</code> si la lista es igual al objeto recibido;
     *         <code>false</code> en otro caso.
     */
    @Override
    public boolean equals(Object objeto) {
        if (objeto == null || getClass() != objeto.getClass())
            return false;
        @SuppressWarnings("unchecked")
        Lista<T> lista = (Lista<T>) objeto;
        if (longitud != lista.longitud) {
            return false;
        } else {
            boolean iguales = true;
            Nodo recorreParametro = lista.cabeza;
            Nodo recorreLista = cabeza;
            while (recorreLista != null) {
                if (recorreLista.elemento.equals(recorreParametro.elemento)) {
                    recorreParametro = recorreParametro.siguiente;
                    recorreLista = recorreLista.siguiente;
                } else {
                    return false;
                }
            }
            return iguales;
        }
    }

    /**
     * Regresa un iterador para recorrer la lista en una dirección.
     * 
     * @return un iterador para recorrer la lista en una dirección.
     */
    @Override
    public Iterator<T> iterator() {
        return new Iterador();
    }

    /**
     * Regresa un iterador para recorrer la lista en ambas direcciones.
     * 
     * @return un iterador para recorrer la lista en ambas direcciones.
     */
    public IteradorLista<T> iteradorLista() {
        return new Iterador();
    }

    private Nodo posicion(int i) {
        Nodo m = cabeza;
        int c = 0;
        while (c++ < i) {
            m = m.siguiente;
        }
        return m;
    }

    private Lista<T> mezcla(Comparator<T> comparador, Lista<T> lista1, Lista<T> lista2) {
        Nodo recorre1 = lista1.cabeza;
        Nodo recorre2 = lista2.cabeza;
        Lista<T> L = new Lista<>();
        while (recorre1 != null && recorre2 != null) {
            if (comparador.compare(recorre1.elemento, recorre2.elemento) <= 0) {
                L.agrega(recorre1.elemento);
                recorre1 = recorre1.siguiente;
            } else {
                L.agrega(recorre2.elemento);
                recorre2 = recorre2.siguiente;
            }
        }
        if (recorre1 == null) {
            while (recorre2 != null) {
                L.agrega(recorre2.elemento);
                recorre2 = recorre2.siguiente;
            }
        }
        if (recorre2 == null) {
            while (recorre1 != null) {
                L.agrega(recorre1.elemento);
                recorre1 = recorre1.siguiente;
            }
        }
        return L;
    }

    /**
     * Regresa una copia de la lista, pero ordenada. Para poder hacer el
     * ordenamiento, el método necesita una instancia de {@link Comparator} para
     * poder comparar los elementos de la lista.
     * 
     * @param comparador el comparador que la lista usará para hacer el
     *                   ordenamiento.
     * @return una copia de la lista, pero ordenada.
     */
    public Lista<T> mergeSort(Comparator<T> comparador) {
        if (longitud < 2) {
            Lista<T> copia = copia();
            return copia;
        }
        Nodo m = posicion(longitud / 2);
        Nodo c = cabeza;
        Lista<T> sub1 = new Lista<>();
        Lista<T> sub2 = new Lista<>();
        while (c != m) {
            sub1.agrega(c.elemento);
            c = c.siguiente;
        }
        while (m != null) {
            sub2.agrega(m.elemento);
            m = m.siguiente;
        }
        return mezcla(comparador, sub1.mergeSort(comparador), sub2.mergeSort(comparador));
    }

    /**
     * Regresa una copia de la lista recibida, pero ordenada. La lista recibida
     * tiene que contener nada más elementos que implementan la interfaz
     * {@link Comparable}.
     * 
     * @param <T>   tipo del que puede ser la lista.
     * @param lista la lista que se ordenará.
     * @return una copia de la lista recibida, pero ordenada.
     */
    public static <T extends Comparable<T>> Lista<T> mergeSort(Lista<T> lista) {
        return lista.mergeSort((a, b) -> a.compareTo(b));
    }

    /**
     * Busca un elemento en la lista ordenada, usando el comparador recibido. El
     * método supone que la lista está ordenada usando el mismo comparador.
     * 
     * @param elemento   el elemento a buscar.
     * @param comparador el comparador con el que la lista está ordenada.
     * @return <code>true</code> si el elemento está contenido en la lista,
     *         <code>false</code> en otro caso.
     */
    public boolean busquedaLineal(T elemento, Comparator<T> comparador) {
        Nodo m = cabeza;
        while (m != null) {
            if (m.elemento.equals(elemento)) {
                return true;
            } else {
                if (comparador.compare(elemento, m.elemento) < 0) {
                    return false;
                }
                m = m.siguiente;
            }
        }
        return false;
        // Aquí va su código.
    }

    /**
     * Busca un elemento en una lista ordenada. La lista recibida tiene que contener
     * nada más elementos que implementan la interfaz {@link Comparable}, y se da
     * por hecho que está ordenada.
     * 
     * @param <T>      tipo del que puede ser la lista.
     * @param lista    la lista donde se buscará.
     * @param elemento el elemento a buscar.
     * @return <code>true</code> si el elemento está contenido en la lista,
     *         <code>false</code> en otro caso.
     */
    public static <T extends Comparable<T>> boolean busquedaLineal(Lista<T> lista, T elemento) {
        return lista.busquedaLineal(elemento, (a, b) -> a.compareTo(b));
    }
}
