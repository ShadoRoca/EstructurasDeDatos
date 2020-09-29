package mx.unam.ciencias.edd;

import java.util.NoSuchElementException;

/**
 * Clase para árboles rojinegros. Un árbol rojinegro cumple las siguientes
 * propiedades:
 *
 * <ol>
 * <li>Todos los vértices son NEGROS o ROJOS.</li>
 * <li>La raíz es NEGRA.</li>
 * <li>Todas las hojas (<code>null</code>) son NEGRAS (al igual que la
 * raíz).</li>
 * <li>Un vértice ROJO siempre tiene dos hijos NEGROS.</li>
 * <li>Todo camino de un vértice a alguna de sus hojas descendientes tiene el
 * mismo número de vértices NEGROS.</li>
 * </ol>
 *
 * Los árboles rojinegros se autobalancean.
 */
public class ArbolRojinegro<T extends Comparable<T>> extends ArbolBinarioOrdenado<T> {

    /**
     * Clase interna protegida para vértices.
     */
    protected class VerticeRojinegro extends Vertice {

        /** El color del vértice. */
        public Color color;

        /**
         * Constructor único que recibe un elemento.
         * 
         * @param elemento el elemento del vértice.
         */
        public VerticeRojinegro(T elemento) {
            super(elemento);
            color = Color.NINGUNO;
        }

        /**
         * Regresa una representación en cadena del vértice rojinegro.
         * 
         * @return una representación en cadena del vértice rojinegro.
         */
        public String toString() {
            return color == Color.ROJO ? "R{" + elemento.toString() + "}" : "N{" + elemento.toString() + "}";
        }

        /**
         * Compara el vértice con otro objeto. La comparación es <em>recursiva</em>.
         * 
         * @param objeto el objeto con el cual se comparará el vértice.
         * @return <code>true</code> si el objeto es instancia de la clase
         *         {@link VerticeRojinegro}, su elemento es igual al elemento de éste
         *         vértice, los descendientes de ambos son recursivamente iguales, y los
         *         colores son iguales; <code>false</code> en otro caso.
         */
        @Override
        public boolean equals(Object objeto) {
            if (objeto == null || getClass() != objeto.getClass())
                return false;
            @SuppressWarnings("unchecked")
            VerticeRojinegro vertice = (VerticeRojinegro) objeto;
            return (color == vertice.color && super.equals(objeto));
            // Aquí va su código.
        }
    }

    /**
     * Constructor sin parámetros. Para no perder el constructor sin parámetros de
     * {@link ArbolBinarioOrdenado}.
     */
    public ArbolRojinegro() {
        super();
    }

    /**
     * Construye un árbol rojinegro a partir de una colección. El árbol rojinegro
     * tiene los mismos elementos que la colección recibida.
     * 
     * @param coleccion la colección a partir de la cual creamos el árbol rojinegro.
     */
    public ArbolRojinegro(Coleccion<T> coleccion) {
        super(coleccion);
    }

    /**
     * Construye un nuevo vértice, usando una instancia de {@link VerticeRojinegro}.
     * 
     * @param elemento el elemento dentro del vértice.
     * @return un nuevo vértice rojinegro con el elemento recibido dentro del mismo.
     */
    @Override
    protected Vertice nuevoVertice(T elemento) {
        return new VerticeRojinegro(elemento);
    }

    /**
     * Regresa el color del vértice rojinegro.
     * 
     * @param vertice el vértice del que queremos el color.
     * @return el color del vértice rojinegro.
     * @throws ClassCastException si el vértice no es instancia de
     *                            {@link VerticeRojinegro}.
     */
    public Color getColor(VerticeArbolBinario<T> vertice) {
        VerticeRojinegro v = (VerticeRojinegro) vertice;
        return v.color;
    }

    /**
     * Agrega un nuevo elemento al árbol. El método invoca al método
     * {@link ArbolBinarioOrdenado#agrega}, y después balancea el árbol recoloreando
     * vértices y girando el árbol como sea necesario.
     * 
     * @param elemento el elemento a agregar.
     */
    @Override
    public void agrega(T elemento) {
        super.agrega(elemento);
        VerticeRojinegro agregado = (VerticeRojinegro) getUltimoVerticeAgregado();
        agregado.color = Color.ROJO;
        rebalanceAgrega(agregado);
    }

    private boolean esIzquierdo(VerticeRojinegro v) {
        if (v.padre != null) {
            if (v == v.padre.izquierdo) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    private boolean esRojo(VerticeRojinegro vertice) {
        return (vertice != null && vertice.color == Color.ROJO);
    }

    private VerticeRojinegro abuelo(VerticeRojinegro vertice) {
        VerticeRojinegro abuelo = padre(padre(vertice));
        return abuelo;
    }

    private VerticeRojinegro padre(VerticeRojinegro vertice) {
        if (vertice.padre == null) {
            throw new NoSuchElementException();
        } else {
            return (VerticeRojinegro) vertice.padre();
        }
    }

    private VerticeRojinegro tio(VerticeRojinegro vertice) {
        if (abuelo(vertice).izquierdo == padre(vertice)) {
            return (VerticeRojinegro) abuelo(vertice).derecho;
        } else {
            return (VerticeRojinegro) abuelo(vertice).izquierdo;
        }
    }

    private void caso1(VerticeRojinegro vertice) {
        vertice.color = Color.NEGRO;
    }

    private void giraD(VerticeRojinegro vertice) {
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

    private void giraI(VerticeRojinegro vertice) {
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

    private void rebalanceAgrega(VerticeRojinegro vertice) {
        if (vertice.padre == null) {
            caso1(vertice);
            return;
        } else {
            VerticeRojinegro p = padre(vertice);
            if (!esRojo(p)) {
                return;
            } else {
                VerticeRojinegro a = abuelo(vertice);
                VerticeRojinegro t = tio(vertice);
                if (esRojo(t)) {
                    p.color = Color.NEGRO;
                    t.color = Color.NEGRO;
                    a.color = Color.ROJO;
                    rebalanceAgrega(a);
                    return;
                } else {
                    if (esIzquierdo(p) && !esIzquierdo(vertice)) {
                        super.giraIzquierda(p);
                        VerticeRojinegro temp = p;
                        p = vertice;
                        vertice = temp;
                    } else if (!esIzquierdo(p) && esIzquierdo(vertice)) {
                        super.giraDerecha(p);
                        VerticeRojinegro temp = p;
                        p = vertice;
                        vertice = temp;
                    }
                    p.color = Color.NEGRO;
                    a.color = Color.ROJO;
                    if (esIzquierdo(p) && esIzquierdo(vertice)) {
                        super.giraDerecha(a);
                    } else if (!esIzquierdo(p) && !esIzquierdo(vertice)) {
                        super.giraIzquierda(a);
                    }
                }
            }
        }
    }

    /**
     * Elimina un elemento del árbol. El método elimina el vértice que contiene el
     * elemento, y recolorea y gira el árbol como sea necesario para rebalancearlo.
     * 
     * @param elemento el elemento a eliminar del árbol.
     */
    @Override
    public void elimina(T elemento) {
        VerticeRojinegro aEliminar = (VerticeRojinegro) busca(elemento);
        if (aEliminar == null) {
            return;
        } else {
            elementos --;
            VerticeRojinegro max;
            if (aEliminar.hayDerecho() && aEliminar.hayIzquierdo()) {
                max = (VerticeRojinegro) intercambiaEliminable(aEliminar);
                aEliminar = max;
            }
            if (!aEliminar.hayDerecho() && !aEliminar.hayIzquierdo()) {
                VerticeRojinegro fantasma = (VerticeRojinegro) nuevoVertice(null);
                aEliminar.izquierdo = fantasma;
                fantasma.padre = aEliminar;
            }
            VerticeRojinegro reemplazo = obtenerUnicoHijo(aEliminar);
            eliminaVertice(aEliminar);
            if (esRojo(reemplazo) && !esRojo(aEliminar)) {
                reemplazo.color = Color.NEGRO;
                return;
            } else if (esRojo(aEliminar) && !esRojo(reemplazo)) {

            } else if (!esRojo(reemplazo) && !esRojo(aEliminar)) {
                rebalanceElimina(reemplazo);
            }
            if (reemplazo.elemento == null) {
                eliminaVertice(reemplazo);
            }
            return;
        }
    }

    private VerticeRojinegro obtenerUnicoHijo(VerticeRojinegro vertice) {
        if (vertice.hayDerecho()) {
            return (VerticeRojinegro) vertice.derecho;
        } else {
            return (VerticeRojinegro) vertice.izquierdo;
        }
    }

    private VerticeRojinegro hermano(VerticeRojinegro vertice) {
        if (esIzquierdo(vertice)) {
            return (VerticeRojinegro) padre(vertice).derecho();
        } else {
            return (VerticeRojinegro) padre(vertice).izquierdo();
        }
    }

    private void rebalanceElimina(VerticeRojinegro v) {
        //CASO 1
        if (!v.hayPadre()) {
            return;
        } else {
            VerticeRojinegro p = (VerticeRojinegro) v.padre();
            VerticeRojinegro h = hermano(v);
            //CASO 2
            if (esRojo(h)) {
                p.color = Color.ROJO;
                h.color = Color.NEGRO;
                if (esIzquierdo(v)) {
                    super.giraIzquierda(p);
                } else {
                    super.giraDerecha(p);
                }
                p = (VerticeRojinegro) v.padre;
                h = esIzquierdo(v) ? (VerticeRojinegro) p.derecho : (VerticeRojinegro) p.izquierdo;
                h.padre = v.padre;
            }
            VerticeRojinegro hi = (VerticeRojinegro) h.izquierdo;
            VerticeRojinegro hd = (VerticeRojinegro) h.derecho;
            //CASO 3 Y 4
            if (!esRojo(h) && !esRojo(hi) && !esRojo(hd)) {
                if (!esRojo(p)) {
                    h.color = Color.ROJO;
                    rebalanceElimina(p);
                    return;
                } else if (esRojo(p)) {
                    p.color = Color.NEGRO;
                    h.color = Color.ROJO;
                    return;
                }
            }
            //CASO 5
            if ((esIzquierdo(v) && esRojo(hi) && !esRojo(hd)) || !esIzquierdo(v) && !esRojo(hi) && esRojo(hd)) {
                if (esRojo(hi)) {
                    hi.color = Color.NEGRO;
                } else {
                    hd.color = Color.NEGRO;
                }
                h.color = Color.ROJO;
                if (esIzquierdo(v)) {
                    super.giraDerecha(h);
                } else {
                    super.giraIzquierda(h);
                }

                if (esIzquierdo(v)) {
                    h = (VerticeRojinegro) v.padre.derecho;
                } else {
                    h = (VerticeRojinegro) v.padre.izquierdo;
                }
                hi = (VerticeRojinegro) h.izquierdo;
                hd = (VerticeRojinegro) h.derecho;
            }
            //CASO 6
            h.color = p.color;
            p.color = Color.NEGRO;

            if (esIzquierdo(v)) {
                hd.color = Color.NEGRO;
            } else {
                hi.color = Color.NEGRO;
            }
            if (esIzquierdo(v)) {
                super.giraIzquierda(p);
            } else {
                super.giraDerecha(p);
            }
        }

    }

    private void caso2Elimina(VerticeRojinegro v, VerticeRojinegro p, VerticeRojinegro h) {
        p.color = Color.ROJO;
        h.color = Color.NEGRO;
        if (esIzquierdo(v)) {
            giraI(p);
            h = (VerticeRojinegro) p.derecho;
            h.padre = v.padre;
        } else {
            giraD(p);
            h = (VerticeRojinegro) p.izquierdo;
            h.padre = v.padre;
        }
    }

    private void caso3Elimina(VerticeRojinegro h, VerticeRojinegro p) {
        h.color = Color.ROJO;
        rebalanceElimina(p);
    }

    private void caso4Elimina(VerticeRojinegro h, VerticeRojinegro p) {
        h.color = Color.ROJO;
        p.color = Color.NEGRO;
    }

    private void caso5Elimina(VerticeRojinegro h, VerticeRojinegro hr, VerticeRojinegro v) {
        h.color = Color.ROJO;
        hr.color = Color.NEGRO;
        if (esIzquierdo(v)) {
            giraD(h);
            h = (VerticeRojinegro) v.padre.derecho;
        } else {
            giraI(h);
            h = (VerticeRojinegro) v.padre.izquierdo;
        }
    }

    private void caso6Elimina(VerticeRojinegro h, VerticeRojinegro p, VerticeRojinegro hc, VerticeRojinegro v) {
        h.color = p.color;
        p.color = Color.NEGRO;
        hc.color = Color.NEGRO;
        if (esIzquierdo(v)) {
            giraI(p);
        } else {
            giraD(p);
        }
    }

    /**
     * Lanza la excepción {@link UnsupportedOperationException}: los árboles
     * rojinegros no pueden ser girados a la izquierda por los usuarios de la clase,
     * porque se desbalancean.
     * 
     * @param vertice el vértice sobre el que se quiere girar.
     * @throws UnsupportedOperationException siempre.
     */
    @Override
    public void giraIzquierda(VerticeArbolBinario<T> vertice) {
        throw new UnsupportedOperationException(
                "Los árboles rojinegros no " + "pueden girar a la izquierda " + "por el usuario.");
    }

    /**
     * Lanza la excepción {@link UnsupportedOperationException}: los árboles
     * rojinegros no pueden ser girados a la derecha por los usuarios de la clase,
     * porque se desbalancean.
     * 
     * @param vertice el vértice sobre el que se quiere girar.
     * @throws UnsupportedOperationException siempre.
     */
    @Override
    public void giraDerecha(VerticeArbolBinario<T> vertice) {
        throw new UnsupportedOperationException(
                "Los árboles rojinegros no " + "pueden girar a la derecha " + "por el usuario.");
    }
}
