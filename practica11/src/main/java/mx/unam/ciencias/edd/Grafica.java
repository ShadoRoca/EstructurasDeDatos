package mx.unam.ciencias.edd;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Clase para gráficas. Una gráfica es un conjunto de vértices y aristas, tales
 * que las aristas son un subconjunto del producto cruz de los vértices.
 */
public class Grafica<T> implements Coleccion<T> {

    /* Clase interna privada para iteradores. */
    private class Iterador implements Iterator<T> {

        /* Iterador auxiliar. */
        private Iterator<Vertice> iterador;

        /* Construye un nuevo iterador, auxiliándose de la lista de vértices. */
        public Iterador() {
            iterador = vertices.iterator();
        }

        /* Nos dice si hay un siguiente elemento. */
        @Override
        public boolean hasNext() {
            return iterador.hasNext();
        }

        /* Regresa el siguiente elemento. */
        @Override
        public T next() {
            return iterador.next().elemento;
        }
    }

    /* Clase interna privada para vértices. */
    private class Vertice implements VerticeGrafica<T>, ComparableIndexable<Vertice> {

        /* El elemento del vértice. */
        public T elemento;
        /* El color del vértice. */
        public Color color;
        /* La distancia del vértice. */
        public double distancia;
        /* El índice del vértice. */
        public int indice;
        /* El diccionario de vecinos del vértice. */
        public Diccionario<T, Vecino> vecinos;

        /* Crea un nuevo vértice a partir de un elemento. */
        public Vertice(T elemento) {
            this.elemento = elemento;
            color = Color.NINGUNO;
            vecinos = new Diccionario<>();
        }

        /* Regresa el elemento del vértice. */
        @Override
        public T get() {
            return elemento;
        }

        /* Regresa el grado del vértice. */
        @Override
        public int getGrado() {
            return vecinos.getElementos();
        }

        /* Regresa el color del vértice. */
        @Override
        public Color getColor() {
            return color;
        }

        /* Regresa un iterable para los vecinos. */
        @Override
        public Iterable<? extends VerticeGrafica<T>> vecinos() {
            return vecinos;
        }

        /* Define el índice del vértice. */
        @Override
        public void setIndice(int indice) {
            this.indice = indice;
        }

        /* Regresa el índice del vértice. */
        @Override
        public int getIndice() {
            return indice;
        }

        /* Compara dos vértices por distancia. */
        @Override
        public int compareTo(Vertice vertice) {
            return (int) (this.distancia - vertice.distancia);
        }
    }

    /* Clase interna privada para vértices vecinos. */
    private class Vecino implements VerticeGrafica<T> {

        /* El vértice vecino. */
        public Vertice vecino;
        /* El peso de la arista conectando al vértice con su vértice vecino. */
        public double peso;

        /*
         * Construye un nuevo vecino con el vértice recibido como vecino y el peso
         * especificado.
         */
        public Vecino(Vertice vecino, double peso) {
            this.vecino = vecino;
            this.peso = peso;
        }

        /* Regresa el elemento del vecino. */
        @Override
        public T get() {
            return vecino.elemento;
        }

        /* Regresa el grado del vecino. */
        @Override
        public int getGrado() {
            return vecino.vecinos.getElementos();
        }

        /* Regresa el color del vecino. */
        @Override
        public Color getColor() {
            return vecino.color;
        }

        /* Regresa un iterable para los vecinos del vecino. */
        @Override
        public Iterable<? extends VerticeGrafica<T>> vecinos() {
            return vecino.vecinos();
        }
    }

    /*
     * Interface para poder usar lambdas al buscar el elemento que sigue al
     * reconstruir un camino.
     */
    @FunctionalInterface
    private interface BuscadorCamino {
        /* Regresa true si el vértice se sigue del vecino. */
        public boolean seSiguen(Grafica.Vertice v, Grafica.Vecino a);
    }

    /* Vértices. */
    private Diccionario<T, Vertice> vertices;
    /* Número de aristas. */
    private int aristas;

    /**
     * Constructor único.
     */
    public Grafica() {
        vertices = new Diccionario<>();
    }

    /**
     * Regresa el número de elementos en la gráfica. El número de elementos es igual
     * al número de vértices.
     * 
     * @return el número de elementos en la gráfica.
     */
    @Override
    public int getElementos() {
        return vertices.getElementos();
    }

    /**
     * Regresa el número de aristas.
     * 
     * @return el número de aristas.
     */
    public int getAristas() {
        return aristas;
    }

    /**
     * Agrega un nuevo elemento a la gráfica.
     * 
     * @param elemento el elemento a agregar.
     * @throws IllegalArgumentException si el elemento ya había sido agregado a la
     *                                  gráfica.
     */
    @Override
    public void agrega(T elemento) {
        if (contiene(elemento)) {
            throw new IllegalArgumentException("El vértice ya está en la gráfica");
        }
        Vertice nuevo = new Vertice(elemento);
        vertices.agrega(nuevo.elemento, nuevo);
    }

    /**
     * Conecta dos elementos de la gráfica. Los elementos deben estar en la gráfica.
     * El peso de la arista que conecte a los elementos será 1.
     * 
     * @param a el primer elemento a conectar.
     * @param b el segundo elemento a conectar.
     * @throws NoSuchElementException   si a o b no son elementos de la gráfica.
     * @throws IllegalArgumentException si a o b ya están conectados, o si a es
     *                                  igual a b.
     */
    public void conecta(T a, T b) {
        if (!this.contiene(a) || !this.contiene(b)) {
            throw new NoSuchElementException("Al menos uno de los elementos no está en la gráfica");
        }
        if (sonVecinos(a, b) || a.equals(b)) {
            throw new IllegalArgumentException("Los elementos ya están conectados o son iguales");
        }
        Vertice x = (Grafica<T>.Vertice) vertice(a);
        Vertice y = (Grafica<T>.Vertice) vertice(b);
        x.vecinos.agrega(b, new Vecino(y, 1));
        y.vecinos.agrega(a, new Vecino(x, 1));
        aristas++;
    }

    /**
     * Conecta dos elementos de la gráfica. Los elementos deben estar en la gráfica.
     * 
     * @param a    el primer elemento a conectar.
     * @param b    el segundo elemento a conectar.
     * @param peso el peso de la nueva vecino.
     * @throws NoSuchElementException   si a o b no son elementos de la gráfica.
     * @throws IllegalArgumentException si a o b ya están conectados, si a es igual
     *                                  a b, o si el peso es no positivo.
     */
    public void conecta(T a, T b, double peso) {
        if (peso < 0) {
            throw new IllegalArgumentException("No se admiten pesos negativos");
        }
        if (!this.contiene(a) || !this.contiene(b)) {
            throw new NoSuchElementException("Al menos uno de los elementos no está en la gráfica");
        }
        if (sonVecinos(a, b) || a.equals(b)) {
            throw new IllegalArgumentException("Los elementos ya están conectados o son iguales");
        }
        Vertice x = (Grafica<T>.Vertice) vertice(a);
        Vertice y = (Grafica<T>.Vertice) vertice(b);
        x.vecinos.agrega(b, new Vecino(y, peso));
        y.vecinos.agrega(a, new Vecino(x, peso));
        aristas++;
    }

    /**
     * Desconecta dos elementos de la gráfica. Los elementos deben estar en la
     * gráfica y estar conectados entre ellos.
     * 
     * @param a el primer elemento a desconectar.
     * @param b el segundo elemento a desconectar.
     * @throws NoSuchElementException   si a o b no son elementos de la gráfica.
     * @throws IllegalArgumentException si a o b no están conectados.
     */
    public void desconecta(T a, T b) {
        if (!contiene(a) || !contiene(b)) {
            throw new NoSuchElementException("Al menos uno de los vértices no está en la gráfica");
        }
        if (!sonVecinos(a, b)) {
            throw new IllegalArgumentException("Los elementos no están conectados");
        }
        Vertice x = (Vertice) vertice(a);
        Vertice y = (Vertice) vertice(b);
        x.vecinos.elimina(y.elemento);
        y.vecinos.elimina(x.elemento);
        aristas--;
    }

    /**
     * Nos dice si el elemento está contenido en la gráfica.
     * 
     * @return <code>true</code> si el elemento está contenido en la gráfica,
     *         <code>false</code> en otro caso.
     */
    @Override
    public boolean contiene(T elemento) {
        return vertices.contiene(elemento) ? true : false;
    }

    /**
     * Elimina un elemento de la gráfica. El elemento tiene que estar contenido en
     * la gráfica.
     * 
     * @param elemento el elemento a eliminar.
     * @throws NoSuchElementException si el elemento no está contenido en la
     *                                gráfica.
     */
    @Override
    public void elimina(T elemento) {
        if (!vertices.contiene(elemento)) {
            throw new NoSuchElementException("El elemento no está en la gráfica");
        }
        Vertice x = (Vertice) vertice(elemento);
        Diccionario<T, Vecino> vecinos = x.vecinos;
        for (Vecino vecino : vecinos) {
            vecino.vecino.vecinos.elimina(elemento);
            aristas--;
        }
        vertices.elimina(elemento);
    }

    /**
     * Nos dice si dos elementos de la gráfica están conectados. Los elementos deben
     * estar en la gráfica.
     * 
     * @param a el primer elemento.
     * @param b el segundo elemento.
     * @return <code>true</code> si a y b son vecinos, <code>false</code> en otro
     *         caso.
     * @throws NoSuchElementException si a o b no son elementos de la gráfica.
     */
    public boolean sonVecinos(T a, T b) {
        if (!contiene(a) || !contiene(b)) {
            throw new NoSuchElementException("Al menos uno de los elementos no está en la gráfica");
        }
        Vertice x = (Vertice) vertice(a);
        Vertice y = (Vertice) vertice(b);
        return y.vecinos.contiene(a) && x.vecinos.contiene(b);
    }

    /**
     * Regresa el peso de la arista que comparten los vértices que contienen a los
     * elementos recibidos.
     * 
     * @param a el primer elemento.
     * @param b el segundo elemento.
     * @return el peso de la arista que comparten los vértices que contienen a los
     *         elementos recibidos.
     * @throws NoSuchElementException   si a o b no son elementos de la gráfica.
     * @throws IllegalArgumentException si a o b no están conectados.
     */
    public double getPeso(T a, T b) {
        if (!contiene(a) || !contiene(b)) {
            throw new NoSuchElementException("Al menos uno de los elementos no está en la gráfica");
        }
        if (!sonVecinos(a, b)) {
            throw new IllegalArgumentException("Los elementos no están conectados");
        }
        Vertice x = (Vertice) vertice(a);
        return x.vecinos.get(b).peso;
    }

    /**
     * Define el peso de la arista que comparten los vértices que contienen a los
     * elementos recibidos.
     * 
     * @param a    el primer elemento.
     * @param b    el segundo elemento.
     * @param peso el nuevo peso de la arista que comparten los vértices que
     *             contienen a los elementos recibidos.
     * @throws NoSuchElementException   si a o b no son elementos de la gráfica.
     * @throws IllegalArgumentException si a o b no están conectados, o si peso es
     *                                  menor o igual que cero.
     */
    public void setPeso(T a, T b, double peso) {
        if (peso < 0) {
            throw new IllegalArgumentException("No se admiten pesos negativos");
        }
        if (!contiene(a) || !contiene(b)) {
            throw new NoSuchElementException("Al menos uno de los elementos no está en la gráfica");
        }
        if (!sonVecinos(a, b)) {
            throw new IllegalArgumentException("Los elementos no están conectados");
        }
        Vertice x = (Vertice) vertice(a);
        Vertice y = (Vertice) vertice(b);
        x.vecinos.get(b).peso = peso;
        y.vecinos.get(a).peso = peso;
    }

    /**
     * Regresa el vértice correspondiente el elemento recibido.
     * 
     * @param elemento el elemento del que queremos el vértice.
     * @throws NoSuchElementException si elemento no es elemento de la gráfica.
     * @return el vértice correspondiente el elemento recibido.
     */
    public VerticeGrafica<T> vertice(T elemento) {
        return vertices.get(elemento);
    }

    /**
     * Define el color del vértice recibido.
     * 
     * @param vertice el vértice al que queremos definirle el color.
     * @param color   el nuevo color del vértice.
     * @throws IllegalArgumentException si el vértice no es válido.
     */
    public void setColor(VerticeGrafica<T> vertice, Color color) {
        if (vertice == null || (vertice.getClass() != Vertice.class && vertice.getClass() != Vecino.class)) {
            throw new IllegalArgumentException("Vertice inválido");
        }
        if (vertice.getClass() == Vertice.class) {
            Vertice v = (Vertice) vertice;
            v.color = color;
        }
        if (vertice.getClass() == Vecino.class) {
            Vecino v = (Vecino) vertice;
            v.vecino.color = color;
        }
    }

    private boolean sonNegros() {
        for (Vertice v : vertices) {
            if (v.color == Color.ROJO || v.color == Color.NINGUNO) {
                return false;
            }
        }
        return true;
    }

    /**
     * Nos dice si la gráfica es conexa.
     * 
     * @return <code>true</code> si la gráfica es conexa, <code>false</code> en otro
     *         caso.
     */
    public boolean esConexa() {
        if (vertices.esVacia()) {
            return true;
        }
        Vertice w = null;
        for (Vertice v : vertices) {
            v.color = Color.ROJO;
            w = v;
        }
        Cola<Vertice> nodos = new Cola<>();
        w.color = Color.NEGRO;
        nodos.mete(w);
        while (!nodos.esVacia()) {
            Vertice u = nodos.saca();
            for (Vecino v : u.vecinos) {
                if (v.vecino.color == Color.ROJO) {
                    v.vecino.color = Color.NEGRO;
                    nodos.mete(v.vecino);
                }
            }
        }
        return sonNegros();
    }

    /**
     * Realiza la acción recibida en cada uno de los vértices de la gráfica, en el
     * orden en que fueron agregados.
     * 
     * @param accion la acción a realizar.
     */
    public void paraCadaVertice(AccionVerticeGrafica<T> accion) {
        for (Vertice vertice : vertices) {
            accion.actua(vertice);
        }
    }

    /**
     * Realiza la acción recibida en todos los vértices de la gráfica, en el orden
     * determinado por BFS, comenzando por el vértice correspondiente al elemento
     * recibido. Al terminar el método, todos los vértices tendrán color
     * {@link Color#NINGUNO}.
     * 
     * @param elemento el elemento sobre cuyo vértice queremos comenzar el
     *                 recorrido.
     * @param accion   la acción a realizar.
     * @throws NoSuchElementException si el elemento no está en la gráfica.
     */
    public void bfs(T elemento, AccionVerticeGrafica<T> accion) {
        if (!contiene(elemento)) {
            throw new NoSuchElementException("El elemento no está en la gráfica");
        }
        Vertice w = (Vertice) vertice(elemento);
        for (Vertice vertice : vertices) {
            vertice.color = Color.ROJO;
        }
        Cola<Vertice> nodos = new Cola<>();
        w.color = Color.NEGRO;
        nodos.mete(w);
        while (!nodos.esVacia()) {
            Vertice u = nodos.saca();
            accion.actua(u);
            for (Vecino v : u.vecinos) {
                if (v.vecino.color == Color.ROJO) {
                    v.vecino.color = Color.NEGRO;
                    nodos.mete(v.vecino);
                }
            }
        }
        descolorea();
    }

    private void descolorea() {
        for (Vertice vertice : vertices) {
            vertice.color = Color.NINGUNO;
        }
    }

    /**
     * Realiza la acción recibida en todos los vértices de la gráfica, en el orden
     * determinado por DFS, comenzando por el vértice correspondiente al elemento
     * recibido. Al terminar el método, todos los vértices tendrán color
     * {@link Color#NINGUNO}.
     * 
     * @param elemento el elemento sobre cuyo vértice queremos comenzar el
     *                 recorrido.
     * @param accion   la acción a realizar.
     * @throws NoSuchElementException si el elemento no está en la gráfica.
     */
    public void dfs(T elemento, AccionVerticeGrafica<T> accion) {
        // Aquí va su código.
        if (!contiene(elemento)) {
            throw new NoSuchElementException("El vértice no está en la gráfica");
        } else {
            Vertice w = (Vertice) vertice(elemento);
            for (Vertice v : vertices) {
                v.color = Color.ROJO;
            }
            Pila<Vertice> nodos = new Pila<>();
            w.color = Color.NEGRO;
            nodos.mete(w);
            while (!nodos.esVacia()) {
                Vertice u = nodos.saca();
                accion.actua(u);
                for (Vecino v : u.vecinos) {
                    if (v.vecino.color == Color.ROJO) {
                        v.vecino.color = Color.NEGRO;
                        nodos.mete(v.vecino);
                    }
                }
            }
            descolorea();
        }
    }

    /**
     * Nos dice si la gráfica es vacía.
     * 
     * @return <code>true</code> si la gráfica es vacía, <code>false</code> en otro
     *         caso.
     */
    @Override
    public boolean esVacia() {
        return vertices.esVacia();
    }

    /**
     * Limpia la gráfica de vértices y aristas, dejándola vacía.
     */
    @Override
    public void limpia() {
        vertices.limpia();
        aristas = 0;
    }

    private String obtenVertices() {
        String vertices = "{";
        for (Vertice vertice : this.vertices) {
            vertices = vertices + vertice.elemento + ", ";
        }
        return vertices + "}";
    }

    private String obtenAristas() {
        String aristas = "{";
        for (Vertice u : vertices) {
            u.color = Color.NEGRO;
            for (Vecino v : u.vecinos) {
                if (v.vecino.color != Color.NEGRO) {
                    aristas = aristas + "(" + u.elemento + ", " + v.vecino.elemento + ")" + ", ";
                }
            }
        }
        return aristas + "}";
    }

    /**
     * Regresa una representación en cadena de la gráfica.
     * 
     * @return una representación en cadena de la gráfica.
     */
    @Override
    public String toString() {
        return obtenVertices() + ", " + obtenAristas();
    }

    /**
     * Nos dice si la gráfica es igual al objeto recibido.
     * 
     * @param objeto el objeto con el que hay que comparar.
     * @return <code>true</code> si la gráfica es igual al objeto recibido;
     *         <code>false</code> en otro caso.
     */
    @Override
    public boolean equals(Object objeto) {
        if (objeto == null || getClass() != objeto.getClass())
            return false;
        @SuppressWarnings("unchecked")
        Grafica<T> grafica = (Grafica<T>) objeto;
        // Aquí va su código.
        if (vertices.getElementos() == 0 && grafica.vertices.getElementos() == 0) {
            return true;
        }
        if (vertices.getElementos() != grafica.vertices.getElementos()) {
            return false;
        }
        if (aristas != grafica.aristas) {
            return false;
        }
        for (Vertice v1 : vertices) {
            if (grafica.contiene(v1.elemento)) {
                Vertice v2 = (Vertice) grafica.vertice(v1.elemento);
                if (v2.vecinos.getElementos() == v1.vecinos.getElementos()) {
                    for (Vecino ve1 : v1.vecinos) {
                        if (!grafica.sonVecinos(ve1.vecino.elemento, v2.elemento)) {
                            return false;
                        }
                    }
                } else {
                    return false;
                }
            } else {
                return false;
            }
        }
        return true;
    }

    /**
     * Regresa un iterador para iterar la gráfica. La gráfica se itera en el orden
     * en que fueron agregados sus elementos.
     * 
     * @return un iterador para iterar la gráfica.
     */
    @Override
    public Iterator<T> iterator() {
        return new Iterador();
    }

    /**
     * Calcula una trayectoria de distancia mínima entre dos vértices.
     * 
     * @param origen  el vértice de origen.
     * @param destino el vértice de destino.
     * @return Una lista con vértices de la gráfica, tal que forman una trayectoria
     *         de distancia mínima entre los vértices <code>a</code> y
     *         <code>b</code>. Si los elementos se encuentran en componentes conexos
     *         distintos, el algoritmo regresa una lista vacía.
     * @throws NoSuchElementException si alguno de los dos elementos no está en la
     *                                gráfica.
     */
    public Lista<VerticeGrafica<T>> trayectoriaMinima(T origen, T destino) {
        // Aquí va su código.
        if (!contiene(origen) || !contiene(destino)) {
            throw new NoSuchElementException("Alguno de los vértices no está en la gráfica");
        }
        Vertice s = (Vertice) vertice(origen);
        Vertice t = (Vertice) vertice(destino);
        Lista<VerticeGrafica<T>> regreso = new Lista<>();
        if (s == t) {
            regreso.agrega(s);
            return regreso;
        } else {
            for (Vertice v : vertices) {
                v.distancia = Double.POSITIVE_INFINITY;
            }
            s.distancia = 0;
            Cola<Vertice> cola = new Cola<>();
            cola.mete(s);
            while (!cola.esVacia()) {
                Vertice u = cola.saca();
                for (Vecino v : u.vecinos) {
                    if (v.vecino.distancia == Double.POSITIVE_INFINITY) {
                        v.vecino.distancia = u.distancia + 1;
                        cola.mete(v.vecino);
                    }
                }
            }
            if (t.distancia == Double.POSITIVE_INFINITY) {
                return regreso;
            } else {
                regreso.agrega(t);
                while (t != s) {// #################
                    for (Vecino ve : t.vecinos) {
                        if (ve.vecino.distancia == t.distancia - 1) {
                            regreso.agrega(ve.vecino);
                            t = ve.vecino;
                            continue; // ##################
                        }
                    }
                }
            }
            return regreso.reversa();
        }
    }

    /**
     * Calcula la ruta de peso mínimo entre el elemento de origen y el elemento de
     * destino.
     * 
     * @param origen  el vértice origen.
     * @param destino el vértice destino.
     * @return una trayectoria de peso mínimo entre el vértice <code>origen</code> y
     *         el vértice <code>destino</code>. Si los vértices están en componentes
     *         conexas distintas, regresa una lista vacía.
     * @throws NoSuchElementException si alguno de los dos elementos no está en la
     *                                gráfica.
     */
    public Lista<VerticeGrafica<T>> dijkstra(T origen, T destino) {
        if (!contiene(origen) || !contiene(destino)) {
            throw new NoSuchElementException("Alguno de los vértices no está en la gráfica");
        }
        Vertice s = (Vertice) vertice(origen);
        Vertice t = (Vertice) vertice(destino);
        Lista<VerticeGrafica<T>> regreso = new Lista<>();
        for (Vertice v : vertices) {
            v.distancia = Double.POSITIVE_INFINITY;
        }
        s.distancia = 0;
        MonticuloArreglo<Vertice> monti = new MonticuloArreglo<>(vertices,
                (int) (vertices.getElementos() / vertices.carga()));
        Vertice u = null;
        while (!monti.esVacia()) {
            u = monti.elimina();
            for (Vecino v : u.vecinos) {
                if (v.vecino.distancia > u.distancia + v.peso) {
                    v.vecino.distancia = u.distancia + v.peso;
                    monti.reordena(v.vecino);
                }
            }
        }
        if (t.distancia == Double.POSITIVE_INFINITY) {
            return regreso;
        } else {
            regreso.agrega(t);
            while (!t.elemento.equals(origen)) {
                for (Vecino ve : t.vecinos) {
                    if (t.distancia == ve.vecino.distancia + ve.peso) {
                        regreso.agrega(ve.vecino);
                        t = ve.vecino;
                    }
                }
            }
        }
        return regreso.reversa();
    }
}
