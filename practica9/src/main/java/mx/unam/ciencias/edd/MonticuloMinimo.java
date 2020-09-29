package mx.unam.ciencias.edd;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Clase para montículos mínimos (<i>min heaps</i>).
 */
public class MonticuloMinimo<T extends ComparableIndexable<T>> implements Coleccion<T>, MonticuloDijkstra<T> {

    /* Clase interna privada para iteradores. */
    private class Iterador implements Iterator<T> {

        /* Índice del iterador. */
        private int indice;

        /* Nos dice si hay un siguiente elemento. */
        @Override
        public boolean hasNext() {
            return indice < elementos;
        }

        /* Regresa el siguiente elemento. */
        @Override
        public T next() {
            if (indice >= elementos || indice < 0) {
                throw new NoSuchElementException();
            }
            T elemento = arbol[indice];
            indice++;
            return elemento;
        }
    }

    /* Clase estática privada para adaptadores. */
    private static class Adaptador<T extends Comparable<T>> implements ComparableIndexable<Adaptador<T>> {

        /* El elemento. */
        private T elemento;
        /* El índice. */
        private int indice;

        /* Crea un nuevo comparable indexable. */
        public Adaptador(T elemento) {
            this.elemento = elemento;
            indice = -1;
        }

        /* Regresa el índice. */
        @Override
        public int getIndice() {
            return indice;
        }

        /* Define el índice. */
        @Override
        public void setIndice(int indice) {
            this.indice = indice;
        }

        /* Compara un adaptador con otro. */
        @Override
        public int compareTo(Adaptador<T> adaptador) {
            return elemento.compareTo(adaptador.elemento);
        }
    }

    /* El número de elementos en el arreglo. */
    private int elementos;
    /* Usamos un truco para poder utilizar arreglos genéricos. */
    private T[] arbol;

    /*
     * Truco para crear arreglos genéricos. Es necesario hacerlo así por cómo Java
     * implementa sus genéricos; de otra forma obtenemos advertencias del
     * compilador.
     */
    @SuppressWarnings("unchecked")
    private T[] nuevoArreglo(int n) {
        return (T[]) (new ComparableIndexable[n]);
    }

    /**
     * Constructor sin parámetros. Es más eficiente usar
     * {@link #MonticuloMinimo(Coleccion)} o {@link #MonticuloMinimo(Iterable,int)},
     * pero se ofrece este constructor por completez.
     */
    public MonticuloMinimo() {
        arbol = nuevoArreglo(100);
        elementos = 0;
    }

    /**
     * Constructor para montículo mínimo que recibe una colección. Es más barato
     * construir un montículo con todos sus elementos de antemano (tiempo
     * <i>O</i>(<i>n</i>)), que el insertándolos uno por uno (tiempo
     * <i>O</i>(<i>n</i> log <i>n</i>)).
     * 
     * @param coleccion la colección a partir de la cuál queremos construir el
     *                  montículo.
     */
    public MonticuloMinimo(Coleccion<T> coleccion) {
        this(coleccion, coleccion.getElementos());
    }

    /**
     * Constructor para montículo mínimo que recibe un iterable y el número de
     * elementos en el mismo. Es más barato construir un montículo con todos sus
     * elementos de antemano (tiempo <i>O</i>(<i>n</i>)), que el insertándolos uno
     * por uno (tiempo <i>O</i>(<i>n</i> log <i>n</i>)).
     * 
     * @param iterable el iterable a partir de la cuál queremos construir el
     *                 montículo.
     * @param n        el número de elementos en el iterable.
     */
    public MonticuloMinimo(Iterable<T> iterable, int n) {
        arbol = nuevoArreglo(n);
        int i = 0;
        for (T t : iterable) {
            t.setIndice(i);
            arbol[i] = t;
            i++;
        }
        elementos = i;
        for (int j = (n - 1) / 2; j >= 0; j--)
            heapifyDown(j);
    }

    private int Derecho(int indice) {
        return indice * 2 + 2;
    }

    private int Izquierdo(int indice) {
        return indice * 2 + 1;
    }

    private void intercambia(T elemento1, T elemento2) {
        int i = elemento1.getIndice();
        int j = elemento2.getIndice();
        this.arbol[i] = elemento2;
        this.arbol[j] = elemento1;
        elemento2.setIndice(i);
        elemento1.setIndice(j);
    }

    private void heapifyUp(int indice) {
        if (indice == 0) {
            return;
        }
        int padre = (indice - 1) / 2;
        if (padre < 0) {
            return;
        }
        if (arbol[padre].compareTo(arbol[indice]) < 0)
            return;
        intercambia(arbol[padre], arbol[indice]);
        heapifyUp(padre);
    }

    private void heapifyDown(int indice) {
        if (indice < 0 || Izquierdo(indice) >= elementos) {
            return;
        }
        int izquierdo = Izquierdo(indice);
        int derecho = Derecho(indice);
        if (elementos <= derecho || arbol[izquierdo].compareTo(arbol[derecho]) <= 0) {
            if (arbol[indice].compareTo(arbol[izquierdo]) > 0) {
                intercambia(arbol[indice], arbol[izquierdo]);
            }
            heapifyDown(izquierdo);
        } else {
            if (arbol[indice].compareTo(arbol[derecho]) > 0) {
                intercambia(arbol[indice], arbol[derecho]);
            }
            heapifyDown(derecho);
        }
    }

    /**
     * Agrega un nuevo elemento en el montículo.
     * 
     * @param elemento el elemento a agregar en el montículo.
     */
    @Override
    public void agrega(T elemento) {
        if (elementos == arbol.length) {
            T[] nuevoArreglo = nuevoArreglo(elementos * 2);
            for (int i = 0; i < this.arbol.length; i++)
                nuevoArreglo[i] = arbol[i];
            arbol = nuevoArreglo;
        }
        elemento.setIndice(elementos);
        arbol[elementos] = elemento;
        elementos++;
        this.heapifyUp(this.elementos - 1);
    }

    /**
     * Elimina el elemento mínimo del montículo.
     * 
     * @return el elemento mínimo del montículo.
     * @throws IllegalStateException si el montículo es vacío.
     */
    @Override
    public T elimina() {
        if (esVacia()) {
            throw new IllegalStateException("El montículo es vacío");
        }
        intercambia(arbol[0], arbol[elementos - 1]);
        arbol[elementos - 1].setIndice(-1);
        elementos--;
        heapifyDown(0);
        return arbol[elementos];
    }

    /**
     * Elimina un elemento del montículo.
     * 
     * @param elemento a eliminar del montículo.
     */
    @Override
    public void elimina(T elemento) {
        if (elemento.getIndice() < 0 || elementos <= elemento.getIndice()) {
            return;
        } else {
            int indice = elemento.getIndice();
            intercambia(arbol[indice], arbol[elementos - 1]);
            arbol[elementos - 1].setIndice(-1);
            // arbol[elementos - 1] = null;
            elementos--;
            heapifyDown(indice);
            heapifyUp(indice);
        }
    }

    /**
     * Nos dice si un elemento está contenido en el montículo.
     * 
     * @param elemento el elemento que queremos saber si está contenido.
     * @return <code>true</code> si el elemento está contenido, <code>false</code>
     *         en otro caso.
     */
    @Override
    public boolean contiene(T elemento) {
        if (elemento.getIndice() < 0 || elemento.getIndice() >= elementos) {
            return false;
        } else {
            if (arbol[elemento.getIndice()].compareTo(elemento) == 0) {
                return true;
            } else {
                return false;
            }
        }
    }

    /**
     * Nos dice si el montículo es vacío.
     * 
     * @return <code>true</code> si ya no hay elementos en el montículo,
     *         <code>false</code> en otro caso.
     */
    @Override
    public boolean esVacia() {
        return elementos == 0;
    }

    /**
     * Limpia el montículo de elementos, dejándolo vacío.
     */
    @Override
    public void limpia() {
        elementos = 0;
        for (T t : arbol) {
            t = null;
        }
    }

    /**
     * Reordena un elemento en el árbol.
     * 
     * @param elemento el elemento que hay que reordenar.
     */
    @Override
    public void reordena(T elemento) {
        heapifyUp(elemento.getIndice());
        heapifyDown(elemento.getIndice());
    }

    /**
     * Regresa el número de elementos en el montículo mínimo.
     * 
     * @return el número de elementos en el montículo mínimo.
     */
    @Override
    public int getElementos() {
        return elementos;
    }

    /**
     * Regresa el <i>i</i>-ésimo elemento del árbol, por niveles.
     * 
     * @param i el índice del elemento que queremos, en <em>in-order</em>.
     * @return el <i>i</i>-ésimo elemento del árbol, por niveles.
     * @throws NoSuchElementException si i es menor que cero, o mayor o igual que el
     *                                número de elementos.
     */
    @Override
    public T get(int i) {
        if (i < 0 || i >= elementos) {
            throw new NoSuchElementException();
        } else {
            return arbol[i];
        }
    }

    /**
     * Regresa una representación en cadena del montículo mínimo.
     * 
     * @return una representación en cadena del montículo mínimo.
     */
    @Override
    public String toString() {
        String s = "";
        for (T t : arbol) {
            s = s + t.toString() + ", ";
        }
        return s;
    }

    private boolean esLongMinima(MonticuloMinimo<T> monticulo) {
        if (Math.min(this.arbol.length, monticulo.arbol.length) == this.arbol.length) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Nos dice si el montículo mínimo es igual al objeto recibido.
     * 
     * @param objeto el objeto con el que queremos comparar el montículo mínimo.
     * @return <code>true</code> si el objeto recibido es un montículo mínimo igual
     *         al que llama el método; <code>false</code> en otro caso.
     */
    @Override
    public boolean equals(Object objeto) {
        if (objeto == null || getClass() != objeto.getClass())
            return false;
        @SuppressWarnings("unchecked")
        MonticuloMinimo<T> monticulo = (MonticuloMinimo<T>) objeto;
        if (monticulo.elementos == 0 && this.elementos == 0) {
            return true;
        }
        if (this.esLongMinima(monticulo)) {
            for (int i = 0; i < this.arbol.length; i++) {
                if (!this.arbol[i].equals(monticulo.arbol[i])) {
                    return false;
                }
            }
        } else {
            for (int i = 0; i < monticulo.arbol.length; i++) {
                if (!this.arbol[i].equals(monticulo.arbol[i])) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Regresa un iterador para iterar el montículo mínimo. El montículo se itera en
     * orden BFS.
     * 
     * @return un iterador para iterar el montículo mínimo.
     */
    @Override
    public Iterator<T> iterator() {
        return new Iterador();
    }

    /**
     * Ordena la colección usando HeapSort.
     * 
     * @param <T>       tipo del que puede ser el arreglo.
     * @param coleccion la colección a ordenar.
     * @return una lista ordenada con los elementos de la colección.
     */
    public static <T extends Comparable<T>> Lista<T> heapSort(Coleccion<T> coleccion) {
        Lista<Adaptador<T>> l1 = new Lista<>();
        Lista<T> l2 = new Lista<>();
        for (T t : coleccion) {
            l1.agrega(new Adaptador<>(t));
        }
        MonticuloMinimo<Adaptador<T>> monticulo = new MonticuloMinimo<>(l1);

        while (!monticulo.esVacia()) {
            Adaptador<T> a = monticulo.elimina();
            l2.agrega(a.elemento);
        }
        return l2;
    }
}
