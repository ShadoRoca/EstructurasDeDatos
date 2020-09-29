package mx.unam.ciencias.edd;

import java.rmi.server.LogStream;
import java.util.Comparator;
import java.util.Iterator;

/**
 * <p>
 * Clase para árboles binarios ordenados. Los árboles son genéricos, pero
 * acotados a la interfaz {@link Comparable}.
 * </p>
 *
 * <p>
 * Un árbol instancia de esta clase siempre cumple que:
 * </p>
 * <ul>
 * <li>Cualquier elemento en el árbol es mayor o igual que todos sus
 * descendientes por la izquierda.</li>
 * <li>Cualquier elemento en el árbol es menor o igual que todos sus
 * descendientes por la derecha.</li>
 * </ul>
 */
public class ArbolBinarioOrdenado<T extends Comparable<T>> extends ArbolBinario<T> {

    /* Clase interna privada para iteradores. */
    private class Iterador implements Iterator<T> {

        /* Pila para recorrer los vértices en DFS in-order. */
        private Pila<Vertice> pila;

        /* Inicializa al iterador. */
        public Iterador() {
            pila = new Pila<>();
            Vertice v = raiz;
            while (v != null) {
                pila.mete(v);
                v = v.izquierdo;
            }
        }

        /* Nos dice si hay un elemento siguiente. */
        @Override
        public boolean hasNext() {
            if (pila.esVacia()) {
                return false;
            } else {
                return true;
            }
        }

        /* Regresa el siguiente elemento en orden DFS in-order. */
        @Override
        public T next() {
            Vertice v = pila.saca();
            T elem = v.elemento;
            if (v.derecho != null) {
                Vertice u = v.derecho.izquierdo;
                pila.mete(v.derecho);
                while (u != null) {
                    pila.mete(u);
                    u = u.izquierdo;
                }
            }
            return elem;
        }
    }

    /**
     * El vértice del último elemento agegado. Este vértice sólo se puede garantizar
     * que existe <em>inmediatamente</em> después de haber agregado un elemento al
     * árbol. Si cualquier operación distinta a agregar sobre el árbol se ejecuta
     * después de haber agregado un elemento, el estado de esta variable es
     * indefinido.
     */
    protected Vertice ultimoAgregado;

    /**
     * Constructor sin parámetros. Para no perder el constructor sin parámetros de
     * {@link ArbolBinario}.
     */
    public ArbolBinarioOrdenado() {
        super();
    }

    /**
     * Construye un árbol binario ordenado a partir de una colección. El árbol
     * binario ordenado tiene los mismos elementos que la colección recibida.
     * 
     * @param coleccion la colección a partir de la cual creamos el árbol binario
     *                  ordenado.
     */
    public ArbolBinarioOrdenado(Coleccion<T> coleccion) {
        super(coleccion);
    }

    private void agregaAux(Vertice actual, Vertice nuevo) {
        if (actual == null) {
            return;
        }
        if (nuevo.elemento.compareTo(actual.elemento) <= 0) {
            if (actual.izquierdo == null) {
                nuevo.padre = actual;
                actual.izquierdo = nuevo;
            } else {
                agregaAux(actual.izquierdo, nuevo);
            }
        } else {
            if (actual.derecho == null) {
                nuevo.padre = actual;
                actual.derecho = nuevo;
            } else {
                agregaAux(actual.derecho, nuevo);
            }
        }
    }

    /**
     * Agrega un nuevo elemento al árbol. El árbol conserva su orden in-order.
     * 
     * @param elemento el elemento a agregar.
     */
    @Override
    public void agrega(T elemento) {
        if (elemento == null) {
            throw new IllegalArgumentException();
        }
        Vertice nuevo = nuevoVertice(elemento);
        ultimoAgregado = nuevo;
        elementos++;
        if (raiz == null) {
            raiz = nuevo;
        } else {
            agregaAux(raiz, nuevo);
        }
    }

    // metodo para encontrar el elemento más grande de un subarbol
    private Vertice maximoSubarbol(Vertice v) {
        if (v.derecho == null) {
            return v;
        } else {
            return maximoSubarbol(v.derecho);
        }
    }

    // metodo para determinar si un vertice es izquierdo
    private boolean esIzquierdo(Vertice v) {
        if (v.padre != null) {
            if (v.equals(v.padre.izquierdo)) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * Elimina un elemento. Si el elemento no está en el árbol, no hace nada; si
     * está varias veces, elimina el primero que encuentre (in-order). El árbol
     * conserva su orden in-order.
     * 
     * @param elemento el elemento a eliminar.
     */
    @Override
    public void elimina(T elemento) {
        Vertice vertice = (Vertice) busca(elemento);
        if (vertice == null) {
            return;
        } else {
            if (vertice.izquierdo != null && vertice.derecho != null) {
                elementos--;
                Vertice maximo = intercambiaEliminable(vertice);
                eliminaVertice(maximo);
            } else {
                elementos--;
                eliminaVertice(vertice);
            }
        }
    }

    /**
     * Intercambia el elemento de un vértice con dos hijos distintos de
     * <code>null</code> con el elemento de un descendiente que tenga a lo más un
     * hijo.
     * 
     * @param vertice un vértice con dos hijos distintos de <code>null</code>.
     * @return el vértice descendiente con el que vértice recibido se intercambió.
     *         El vértice regresado tiene a lo más un hijo distinto de
     *         <code>null</code>.
     */
    protected Vertice intercambiaEliminable(Vertice vertice) {
        T elem = vertice.elemento;
        Vertice ultimo = maximoSubarbol(vertice.izquierdo);
        T elem2 = ultimo.elemento;
        ultimo.elemento = elem;
        vertice.elemento = elem2;
        return ultimo;
    }

    /**
     * Elimina un vértice que a lo más tiene un hijo distinto de <code>null</code>
     * subiendo ese hijo (si existe).
     * 
     * @param vertice el vértice a eliminar; debe tener a lo más un hijo distinto de
     *                <code>null</code>.
     */
    protected void eliminaVertice(Vertice vertice) {
        if (vertice.derecho == null && vertice.izquierdo == null) {
            if (vertice.padre == null) {
                raiz = null;
            } else {
                if (vertice == vertice.padre.izquierdo) { //
                    vertice.padre.izquierdo = null;
                    vertice.padre = null;
                } else {
                    vertice.padre.derecho = null;
                    vertice.padre = null;
                }
            }
        }
        if (vertice.derecho != null && vertice.izquierdo == null) {
            if (vertice.padre == null) {
                raiz = vertice.derecho;
                vertice.derecho.padre = null;
            } else {
                if (vertice == vertice.padre.izquierdo) { //
                    vertice.derecho.padre = vertice.padre;
                    vertice.padre.izquierdo = vertice.derecho;
                } else {
                    vertice.derecho.padre = vertice.padre;
                    vertice.padre.derecho = vertice.derecho;
                }
            }
        }
        if (vertice.izquierdo != null && vertice.derecho == null) {
            if (vertice.padre == null) {
                raiz = vertice.izquierdo;
                vertice.izquierdo.padre = null;
            } else {
                if (vertice == vertice.padre.izquierdo) { //
                    vertice.izquierdo.padre = vertice.padre;
                    vertice.padre.izquierdo = vertice.izquierdo;
                } else {
                    vertice.izquierdo.padre = vertice.padre;
                    vertice.padre.derecho = vertice.izquierdo;
                }
            }
        }
    }

    /**
     * Busca un elemento en el árbol recorriéndolo in-order. Si lo encuentra,
     * regresa el vértice que lo contiene; si no, regresa <code>null</code>.
     * 
     * @param elemento el elemento a buscar.
     * @return un vértice que contiene al elemento buscado si lo encuentra;
     *         <code>null</code> en otro caso.
     */
    @Override
    public VerticeArbolBinario<T> busca(T elemento) {
        return buscaAux(elemento, raiz);
    }

    private VerticeArbolBinario<T> buscaAux(T elemento, Vertice v) {
        if (v == null || elemento == null) {
            return null;
        } else if (v.elemento.compareTo(elemento) == 0) {
            return v;
        } else if (elemento.compareTo(v.elemento) <= 0) {
            return buscaAux(elemento, v.izquierdo);
        }
        return buscaAux(elemento, v.derecho);
    }

    /**
     * Regresa el vértice que contiene el último elemento agregado al árbol. Este
     * método sólo se puede garantizar que funcione <em>inmediatamente</em> después
     * de haber invocado al método {@link agrega}. Si cualquier operación distinta a
     * agregar sobre el árbol se ejecuta después de haber agregado un elemento, el
     * comportamiento de este método es indefinido.
     * 
     * @return el vértice que contiene el último elemento agregado al árbol, si el
     *         método es invocado inmediatamente después de agregar un elemento al
     *         árbol.
     */
    public VerticeArbolBinario<T> getUltimoVerticeAgregado() {
        return ultimoAgregado;
    }

    /**
     * Gira el árbol a la derecha sobre el vértice recibido. Si el vértice no tiene
     * hijo izquierdo, el método no hace nada.
     * 
     * @param vertice el vértice sobre el que vamos a girar.
     */
    public void giraDerecha(VerticeArbolBinario<T> vertice) {
        if (vertice == null || vertice.izquierdo() == null) {
            return;
        }
        Vertice q = (Vertice) vertice;
        Vertice p = q.izquierdo;
        p.padre = q.padre;
        if (q == raiz) {
            raiz = p;
        } else {
            if (q.padre.izquierdo == q) {
                p.padre.izquierdo = p;
            } else {
                p.padre.derecho = p;
            }
        }
        q.izquierdo = p.derecho;
        if (p.derecho != null) {
            p.derecho.padre = q;
        }
        q.padre = p;
        p.derecho = q;
    }

    /**
     * Gira el árbol a la izquierda sobre el vértice recibido. Si el vértice no
     * tiene hijo derecho, el método no hace nada.
     * 
     * @param vertice el vértice sobre el que vamos a girar.
     */
    public void giraIzquierda(VerticeArbolBinario<T> vertice) {
        if (vertice == null || vertice.derecho() == null) {
            return;
        }
        Vertice p = (Vertice) vertice;
        Vertice q = p.derecho;
        q.padre = p.padre;
        if (p == raiz) {
            raiz = q;
        } else {
            if (p.padre.izquierdo == p) {
                q.padre.izquierdo = q;
            } else {
                q.padre.derecho = q;
            }
        }
        p.derecho = q.izquierdo;
        if (q.izquierdo != null) {
            q.izquierdo.padre = p;
        }
        p.padre = q;
        q.izquierdo = p;
    }

    private void dfsPreOrderAux(AccionVerticeArbolBinario<T> accion, Vertice a) {
        if (a == null) {
            return;
        } else {
            accion.actua(a);
            dfsPreOrderAux(accion, a.izquierdo);
            dfsPreOrderAux(accion, a.derecho);
        }
    }

    /**
     * Realiza un recorrido DFS <em>pre-order</em> en el árbol, ejecutando la acción
     * recibida en cada elemento del árbol.
     * 
     * @param accion la acción a realizar en cada elemento del árbol.
     */
    public void dfsPreOrder(AccionVerticeArbolBinario<T> accion) {
        dfsPreOrderAux(accion, raiz);
    }

    private void dfsInOrderAux(AccionVerticeArbolBinario<T> accion, Vertice a) {
        if (a == null) {
            return;
        } else {
            dfsInOrderAux(accion, a.izquierdo);
            accion.actua(a);
            dfsInOrderAux(accion, a.derecho);
        }
    }

    /**
     * Realiza un recorrido DFS <em>in-order</em> en el árbol, ejecutando la acción
     * recibida en cada elemento del árbol.
     * 
     * @param accion la acción a realizar en cada elemento del árbol.
     */
    public void dfsInOrder(AccionVerticeArbolBinario<T> accion) {
        dfsInOrderAux(accion, raiz);
    }

    private void dfsPostOrderAux(AccionVerticeArbolBinario<T> accion, Vertice a) {
        if (a == null) {
            return;
        } else {
            dfsPostOrderAux(accion, a.izquierdo);
            dfsPostOrderAux(accion, a.derecho);
            accion.actua(a);
        }
    }

    /**
     * Realiza un recorrido DFS <em>post-order</em> en el árbol, ejecutando la
     * acción recibida en cada elemento del árbol.
     * 
     * @param accion la acción a realizar en cada elemento del árbol.
     */
    public void dfsPostOrder(AccionVerticeArbolBinario<T> accion) {
        dfsPostOrderAux(accion, raiz);
    }

    /**
     * Regresa un iterador para iterar el árbol. El árbol se itera en orden.
     * 
     * @return un iterador para iterar el árbol.
     */
    @Override
    public Iterator<T> iterator() {
        return new Iterador();
    }
}
