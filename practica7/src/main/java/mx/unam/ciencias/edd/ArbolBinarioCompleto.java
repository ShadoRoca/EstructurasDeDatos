package mx.unam.ciencias.edd;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * <p>
 * Clase para árboles binarios completos.
 * </p>
 *
 * <p>
 * Un árbol binario completo agrega y elimina elementos de tal forma que el
 * árbol siempre es lo más cercano posible a estar lleno.
 * </p>
 */
public class ArbolBinarioCompleto<T> extends ArbolBinario<T> {

    /* Clase interna privada para iteradores. */
    private class Iterador implements Iterator<T> {

        /* Cola para recorrer los vértices en BFS. */
        private Cola<Vertice> cola;

        /* Inicializa al iterador. */
        public Iterador() {
            cola = new Cola<>();
            if (raiz != null) {
                cola.mete(raiz);
            }
        }

        /* Nos dice si hay un elemento siguiente. */
        @Override
        public boolean hasNext() {
            if (cola.cabeza != null) {
                return  true;
            } else {
                return false;
            }
        }

        /* Regresa el siguiente elemento en orden BFS. */
        @Override
        public T next() {
            if (cola.esVacia()) {
                throw new NoSuchElementException();
            } else {
                Vertice v = cola.saca();
                if (v.izquierdo != null) {
                    cola.mete(v.izquierdo);
                }
                if (v.derecho != null) {
                    cola.mete(v.derecho);
                }
                return v.elemento;
            }
        }
    }

    /**
     * Constructor sin parámetros. Para no perder el constructor sin parámetros de
     * {@link ArbolBinario}.
     */
    public ArbolBinarioCompleto() {
        super();
    }

    /**
     * Construye un árbol binario completo a partir de una colección. El árbol
     * binario completo tiene los mismos elementos que la colección recibida.
     * 
     * @param coleccion la colección a partir de la cual creamos el árbol binario
     *                  completo.
     */
    public ArbolBinarioCompleto(Coleccion<T> coleccion) {
        super(coleccion);
    }

    /**
     * Agrega un elemento al árbol binario completo. El nuevo elemento se coloca a
     * la derecha del último nivel, o a la izquierda de un nuevo nivel.
     * 
     * @param elemento el elemento a agregar al árbol.
     * @throws IllegalArgumentException si <code>elemento</code> es
     *                                  <code>null</code>.
     */
    @Override
    public void agrega(T elemento) {
        if (elemento == null) {
            throw new IllegalArgumentException();
        }
        elementos++;
        Vertice v = nuevoVertice(elemento);
        if (raiz == null) {
            raiz = v;
        } else {
            Cola<Vertice> cola = new Cola<>();
            cola.mete(raiz);
            while (cola.cabeza != null) {
                Vertice r = cola.saca();
                if (r.izquierdo == null) {
                    r.izquierdo = v;
                    v.padre = r;
                    break;
                } else if (r.derecho == null) {
                    r.derecho = v;
                    v.padre = r;
                    break;
                } else {
                    cola.mete(r.izquierdo);
                    cola.mete(r.derecho);
                }
            }
        }
    }

    /*
     * private VerticeArbolBinario<T> busca(T elemento, Vertice v) { if (v == null
     * || elemento == null) { return null; } else if (v.elemento.equals(elemento)) {
     * return v; } else if (busca(elemento, v.izquierdo) != null) { return
     * busca(elemento, v.izquierdo); } else { return busca(elemento, v.derecho); } }
     */

    /**
     * Elimina un elemento del árbol. El elemento a eliminar cambia lugares con el
     * último elemento del árbol al recorrerlo por BFS, y entonces es eliminado.
     * 
     * @param elemento el elemento a eliminar.
     */
    @Override
    public void elimina(T elemento) {
        Vertice m = (Vertice) busca(elemento);
        if (m == null) {
            return;
        } else {
            elementos--;
            if (elementos == 0) {
                raiz = null;
            } else {
                Vertice u = ultimo();
                m.elemento = u.elemento;
                u.elemento = elemento;
                if (u.padre.derecho == null) {
                    u.padre.izquierdo = null;
                } else {
                    u.padre.derecho = null;
                }
            }
        }
    }

    /**
     * Regresa la altura del árbol. La altura de un árbol binario completo siempre
     * es ⌊log<sub>2</sub><em>n</em>⌋.
     * 
     * @return la altura del árbol.
     */
    @Override
    public int altura() {
        if (raiz == null) {
            return -1;
        }
        return (int) Math.floor(Math.log((double) elementos) / Math.log(2));
    }

    private Vertice ultimo() {
        Cola<Vertice> cola = new Cola<>();
        Vertice v = raiz;
        cola.mete(v);
        while (!(cola.esVacia())) {
            v = cola.saca();
            if (v.izquierdo != null)
                cola.mete(v.izquierdo);
            if (v.derecho != null)
                cola.mete(v.derecho);
        }
        return v;
    }

    /**
     * Realiza un recorrido BFS en el árbol, ejecutando la acción recibida en cada
     * elemento del árbol.
     * 
     * @param accion la acción a realizar en cada elemento del árbol.
     */
    public void bfs(AccionVerticeArbolBinario<T> accion) {
        if (raiz == null) {
            return;
        } else {
            Cola<Vertice> cola = new Cola<>();
            cola.mete(raiz);
            while (!(cola.esVacia())) {
                Vertice v = cola.saca();
                accion.actua(v);
                if (v.izquierdo != null)
                    cola.mete(v.izquierdo);
                if (v.derecho != null)
                    cola.mete(v.derecho);
            }
        }
    }

    /**
     * Regresa un iterador para iterar el árbol. El árbol se itera en orden BFS.
     * 
     * @return un iterador para iterar el árbol.
     */
    @Override
    public Iterator<T> iterator() {
        return new Iterador();
    }
}
