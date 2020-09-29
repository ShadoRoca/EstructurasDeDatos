package mx.unam.ciencias.edd;

import java.util.NoSuchElementException;

/**
 * <p>
 * Clase abstracta para árboles binarios genéricos.
 * </p>
 *
 * <p>
 * La clase proporciona las operaciones básicas para árboles binarios, pero deja
 * la implementación de varias en manos de las subclases concretas.
 * </p>
 */
public abstract class ArbolBinario<T> implements Coleccion<T> {

    /**
     * Clase interna protegida para vértices.
     */
    protected class Vertice implements VerticeArbolBinario<T> {

        /** El elemento del vértice. */
        public T elemento;
        /** El padre del vértice. */
        public Vertice padre;
        /** El izquierdo del vértice. */
        public Vertice izquierdo;
        /** El derecho del vértice. */
        public Vertice derecho;

        /**
         * Constructor único que recibe un elemento.
         * 
         * @param elemento el elemento del vértice.
         */
        public Vertice(T elemento) {
            this.elemento = elemento;
        }

        /**
         * Nos dice si el vértice tiene un padre.
         * 
         * @return <code>true</code> si el vértice tiene padre, <code>false</code> en
         *         otro caso.
         */
        @Override
        public boolean hayPadre() {
            return this.padre != null;
        }

        /**
         * Nos dice si el vértice tiene un izquierdo.
         * 
         * @return <code>true</code> si el vértice tiene izquierdo, <code>false</code>
         *         en otro caso.
         */
        @Override
        public boolean hayIzquierdo() {
            return this.izquierdo != null;
        }

        /**
         * Nos dice si el vértice tiene un derecho.
         * 
         * @return <code>true</code> si el vértice tiene derecho, <code>false</code> en
         *         otro caso.
         */
        @Override
        public boolean hayDerecho() {
            return this.derecho != null;
        }

        /**
         * Regresa el padre del vértice.
         * 
         * @return el padre del vértice.
         * @throws NoSuchElementException si el vértice no tiene padre.
         */
        @Override
        public VerticeArbolBinario<T> padre() {
            if (padre == null) {
                throw new NoSuchElementException();
            } else {
                return this.padre;
            }
        }

        /**
         * Regresa el izquierdo del vértice.
         * 
         * @return el izquierdo del vértice.
         * @throws NoSuchElementException si el vértice no tiene izquierdo.
         */
        @Override
        public VerticeArbolBinario<T> izquierdo() {
            if (izquierdo == null) {
                throw new NoSuchElementException();
            } else {
                return this.izquierdo;
            }
        }

        /**
         * Regresa el derecho del vértice.
         * 
         * @return el derecho del vértice.
         * @throws NoSuchElementException si el vértice no tiene derecho.
         */
        @Override
        public VerticeArbolBinario<T> derecho() {
            if (derecho == null) {
                throw new NoSuchElementException();
            } else {
                return this.derecho;
            }
        }

        private int altura(Vertice v) {
            if (v == null) {
                return -1;
            } else {
                return 1 + Math.max(altura(v.izquierdo), altura(v.derecho));
            }
        }

        /**
         * Regresa la altura del vértice.
         * 
         * @return la altura del vértice.
         */
        @Override
        public int altura() {
            return altura(this);
        }

        private int profundo(Vertice v) {
            if (v.padre == null) {
                return 0;
            } else {
                return 1 + profundo(v.padre);
            }
        }

        /**
         * Regresa la profundidad del vértice.
         * 
         * @return la profundidad del vértice.
         */
        @Override
        public int profundidad() {
            return profundo(this);
        }

        /**
         * Regresa el elemento al que apunta el vértice.
         * 
         * @return el elemento al que apunta el vértice.
         */
        @Override
        public T get() {
            return this.elemento;
        }

        private boolean equals(Vertice v, Vertice c) {
            if (v == null && c == null) {
                return true;
            } else if (v == null && c != null) {
                return false;
            } else if (v != null & c == null) {
                return false;
            } else if (v.elemento.equals(c.elemento)) {
                return equals(v.derecho, c.derecho) && equals(v.izquierdo, c.izquierdo);
            } else {
                return false;
            }
        }

        /**
         * Compara el vértice con otro objeto. La comparación es <em>recursiva</em>. Las
         * clases que extiendan {@link Vertice} deben sobrecargar el método
         * {@link Vertice#equals}.
         * 
         * @param objeto el objeto con el cual se comparará el vértice.
         * @return <code>true</code> si el objeto es instancia de la clase
         *         {@link Vertice}, su elemento es igual al elemento de éste vértice, y
         *         los descendientes de ambos son recursivamente iguales;
         *         <code>false</code> en otro caso.
         */
        @Override
        public boolean equals(Object objeto) {
            if (objeto == null || getClass() != objeto.getClass())
                return false;
            @SuppressWarnings("unchecked")
            Vertice vertice = (Vertice) objeto;
            return equals(this, vertice);
        }

        /**
         * Regresa una representación en cadena del vértice.
         * 
         * @return una representación en cadena del vértice.
         */
        public String toString() {
            return elemento.toString();
        }
    }

    /** La raíz del árbol. */
    protected Vertice raiz;
    /** El número de elementos */
    protected int elementos;

    /**
     * Constructor sin parámetros. Tenemos que definirlo para no perderlo.
     */
    public ArbolBinario() {
    }

    /**
     * Construye un árbol binario a partir de una colección. El árbol binario tendrá
     * los mismos elementos que la colección recibida.
     * 
     * @param coleccion la colección a partir de la cual creamos el árbol binario.
     */
    public ArbolBinario(Coleccion<T> coleccion) {
        for (T a : coleccion) {
            agrega(a);
        }
    }

    /**
     * Construye un nuevo vértice, usando una instancia de {@link Vertice}. Para
     * crear vértices se debe utilizar este método en lugar del operador
     * <code>new</code>, para que las clases herederas de ésta puedan sobrecargarlo
     * y permitir que cada estructura de árbol binario utilice distintos tipos de
     * vértices.
     * 
     * @param elemento el elemento dentro del vértice.
     * @return un nuevo vértice con el elemento recibido dentro del mismo.
     */
    protected Vertice nuevoVertice(T elemento) {
        Vertice nuevo = new Vertice(elemento);
        return nuevo;
    }

    /**
     * Regresa la altura del árbol. La altura de un árbol es la altura de su raíz.
     * 
     * @return la altura del árbol.
     */
    public int altura() {
        if (raiz == null) {
            return -1;
        }
        return raiz.altura();
    }

    /**
     * Regresa el número de elementos que se han agregado al árbol.
     * 
     * @return el número de elementos en el árbol.
     */
    @Override
    public int getElementos() {
        return elementos;
    }

    /**
     * Nos dice si un elemento está en el árbol binario.
     * 
     * @param elemento el elemento que queremos comprobar si está en el árbol.
     * @return <code>true</code> si el elemento está en el árbol; <code>false</code>
     *         en otro caso.
     */
    @Override
    public boolean contiene(T elemento) {
        if (busca(elemento) == null) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Busca el vértice de un elemento en el árbol. Si no lo encuentra regresa
     * <code>null</code>.
     * 
     * @param elemento el elemento para buscar el vértice.
     * @return un vértice que contiene el elemento buscado si lo encuentra;
     *         <code>null</code> en otro caso.
     */
    public VerticeArbolBinario<T> busca(T elemento) {
        return buscaAux(elemento, raiz);
    }

    private VerticeArbolBinario<T> buscaAux(T elemento, Vertice v) {
        if (v == null || elemento == null) {
            return null;
        } else if (v.elemento.equals(elemento)) {
            return v;
        } else {
            VerticeArbolBinario<T> a = buscaAux(elemento, v.izquierdo);
            VerticeArbolBinario<T> b = buscaAux(elemento, v.derecho);

            return a != null ? a : b;
        }
    }

    /**
     * Regresa el vértice que contiene la raíz del árbol.
     * 
     * @return el vértice que contiene la raíz del árbol.
     * @throws NoSuchElementException si el árbol es vacío.
     */
    public VerticeArbolBinario<T> raiz() {
        if (raiz == null) {
            throw new NoSuchElementException();
        } else {
            return raiz;
        }
    }

    /**
     * Nos dice si el árbol es vacío.
     * 
     * @return <code>true</code> si el árbol es vacío, <code>false</code> en otro
     *         caso.
     */
    @Override
    public boolean esVacia() {
        return raiz == null;
    }

    /**
     * Limpia el árbol de elementos, dejándolo vacío.
     */
    @Override
    public void limpia() {
        raiz = null;
        elementos = 0;
    }

    private boolean equals(Vertice v, Vertice c) {
        if (v == null && c == null) {
            return true;
        } else if (v == null && c != null) {
            return false;
        } else if (v != null & c == null) {
            return false;
        } else if (v.elemento.equals(c.elemento)) {
            return equals(v.derecho, c.derecho) && equals(v.izquierdo, c.izquierdo);
        } else {
            return false;
        }
    }

    /**
     * Compara el árbol con un objeto.
     * 
     * @param objeto el objeto con el que queremos comparar el árbol.
     * @return <code>true</code> si el objeto recibido es un árbol binario y los
     *         árboles son iguales; <code>false</code> en otro caso.
     */
    @Override
    public boolean equals(Object objeto) {
        if (objeto == null || getClass() != objeto.getClass())
            return false;
        @SuppressWarnings("unchecked")
        ArbolBinario<T> arbol = (ArbolBinario<T>) objeto;
        return equals(arbol.raiz, raiz);
    }

    private String dibujaEspacios(int l, boolean[] arreglo) {
        String cadena = "";
        for (int i = 0; i <= l - 1; i++) {
            if (arreglo[i] == true) {
                cadena = cadena + "│  ";
            } else {
                cadena = cadena + "   ";
            }
        }
        return cadena;
    }

    private String toString(Vertice v, int l, boolean[] arreglo) {
        String s = v.toString() + "\n";
        arreglo[l] = true;

        if (v.izquierdo != null && v.derecho != null) {
            s = s + dibujaEspacios(l, arreglo);
            s = s + "├─›";
            s = s + toString(v.izquierdo, l + 1, arreglo);
            s = s + dibujaEspacios(l, arreglo);
            s = s + "└─»";
            arreglo[l] = false;
            s = s + toString(v.derecho, l + 1, arreglo);
            return s;
        } else if (v.izquierdo != null) {
            s = s + dibujaEspacios(l, arreglo);
            s = s + "└─›";
            arreglo[l] = false;
            s = s + toString(v.izquierdo, l + 1, arreglo);
            return s;
        } else if (v.derecho != null) {
            s = s + dibujaEspacios(l, arreglo);
            s = s + "└─»";
            arreglo[l] = false;
            s = s + toString(v.derecho, l + 1, arreglo);
            return s;
        }
        return s;
    }

    private String toString(ArbolBinario<T> t) {
        if (t.raiz == null) {
            return "";
        } else {
            boolean[] arr = new boolean[t.altura() + 1];
            for (int i = 0; i < t.altura() + 1; i++) {
                arr[i] = false;
            }
            return toString(t.raiz, 0, arr);
        }
    }

    /**
     * Regresa una representación en cadena del árbol.
     * 
     * @return una representación en cadena del árbol.
     */
    @Override
    public String toString() {
        return toString(this);
    }

    /**
     * Convierte el vértice (visto como instancia de {@link VerticeArbolBinario}) en
     * vértice (visto como instancia de {@link Vertice}). Método auxiliar para hacer
     * esta audición en un único lugar.
     * 
     * @param vertice el vértice de árbol binario que queremos como vértice.
     * @return el vértice recibido visto como vértice.
     * @throws ClassCastException si el vértice no es instancia de {@link Vertice}.
     */
    protected Vertice vertice(VerticeArbolBinario<T> vertice) {
        return (Vertice) vertice;
    }
}
