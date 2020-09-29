package mx.unam.ciencias.edd;

import java.util.NoSuchElementException;

/**
 * Clase para montículos de Dijkstra con arreglos.
 */
public class MonticuloArreglo<T extends ComparableIndexable<T>> implements MonticuloDijkstra<T> {

    /* Número de elementos en el arreglo. */
    private int elementos;
    /* Usamos un truco para poder utilizar arreglos genéricos. */
    private T[] arreglo;

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
     * Constructor para montículo de Dijkstra con un arreglo a partir de una
     * colección.
     * 
     * @param coleccion la colección a partir de la cuál queremos construir el
     *                  montículo.
     */
    public MonticuloArreglo(Coleccion<T> coleccion) {
        this(coleccion, coleccion.getElementos());
    }

    /**
     * Construye un nuevo para montículo de Dijkstra con arreglo a partir de un
     * iterable.
     * 
     * @param iterable el iterable a partir de la cual construir el montículo.
     * @param n        el número de elementos en el iterable.
     */
    public MonticuloArreglo(Iterable<T> iterable, int n) {
        arreglo = nuevoArreglo(n);
        int i = 0;
        for (T t : iterable) {
            arreglo[i] = t;
            arreglo[i].setIndice(i);
            i++;
        }
        elementos = i;
    }

    /**
     * Elimina el elemento mínimo del montículo.
     * 
     * @return el elemento mínimo del montículo.
     * @throws IllegalStateException si el montículo es vacío.
     */
    @Override
    public T elimina() {
        if (elementos == 0) {
            throw new IllegalStateException("El monticulo ya es vacío");
        } else {
            int indice = 0;
            while(arreglo[indice] == null) {
                indice++;
            }
            for (int i = 0; i < arreglo.length; i++) {
                if (arreglo[i] == null) {
                    continue;
                } else if (arreglo[i].compareTo(arreglo[indice]) < 0) {
                    indice = i;
                }
            }
            T elemento = arreglo[indice];
            arreglo[indice].setIndice(-1);
            arreglo[indice] = null;
            elementos--;
            return elemento;
        }

    }

    /**
     * Regresa el <i>i</i>-ésimo elemento del arreglo.
     * 
     * @param i el índice del elemento que queremos.
     * @return el <i>i</i>-ésimo elemento del arreglo.
     * @throws NoSuchElementException si i es menor que cero, o mayor o igual que el
     *                                número de elementos.
     */
    @Override
    public T get(int i) {
        if (i < 0 || i >= elementos) {
            throw new NoSuchElementException();
        } else {
            return arreglo[i];
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
        return elementos == 0 ? true : false;
    }

    /**
     * Regresa el número de elementos en el montículo.
     * 
     * @return el número de elementos en el montículo.
     */
    @Override
    public int getElementos() {
        return elementos;
    }
}