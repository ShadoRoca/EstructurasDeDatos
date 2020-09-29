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
            // Aquí va su código.
        }

        /* Nos dice si hay un siguiente elemento. */
        @Override
        public boolean hasNext() {
            return iterador.hasNext();
            // Aquí va su código.
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
        /* La lista de vecinos del vértice. */
        public Lista<Vecino> vecinos;

        /* Crea un nuevo vértice a partir de un elemento. */
        public Vertice(T elemento) {
            this.color = Color.NINGUNO;
            this.elemento = elemento;
            vecinos = new Lista<>();
            // Aquí va su código.
        }

        /* Regresa el elemento del vértice. */
        @Override
        public T get() {
            // Aquí va su código.
            return elemento;
        }

        /* Regresa el grado del vértice. */
        @Override
        public int getGrado() {
            // Aquí va su código.
            return vecinos.getLongitud();
        }

        /* Regresa el color del vértice. */
        @Override
        public Color getColor() {
            // Aquí va su código.
            return this.color;
        }

        /* Regresa un iterable para los vecinos. */
        @Override
        public Iterable<? extends VerticeGrafica<T>> vecinos() {
            // Aquí va su código.
            return vecinos;
        }

        /* Define el índice del vértice. */
        @Override
        public void setIndice(int indice) {
            // Aquí va su código.
            this.indice = indice;
        }

        /* Regresa el índice del vértice. */
        @Override
        public int getIndice() {
            // Aquí va su código.
            return this.indice;
        }

        /* Compara dos vértices por distancia. */
        @Override
        public int compareTo(Vertice vertice) {
            // Aquí va su código.
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
            return vecino.getGrado();
        }

        /* Regresa el color del vecino. */
        @Override
        public Color getColor() {
            return vecino.color;
        }

        /* Regresa un iterable para los vecinos del vecino. */
        @Override
        public Iterable<? extends VerticeGrafica<T>> vecinos() {
            return vecino.vecinos;
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
    private Lista<Vertice> vertices;
    /* Número de aristas. */
    private int aristas;

    /**
     * Constructor único.
     */
    public Grafica() {
        // Aquí va su código.
        vertices = new Lista<>();
    }

    /**
     * Regresa el número de elementos en la gráfica. El número de elementos es igual
     * al número de vértices.
     * 
     * @return el número de elementos en la gráfica.
     */
    @Override
    public int getElementos() {
        // Aquí va su código.
        return vertices.getLongitud();
    }

    /**
     * Regresa el número de aristas.
     * 
     * @return el número de aristas.
     */
    public int getAristas() {
        // Aquí va su código.
        return aristas;
    }

    /**
     * Agrega un nuevo elemento a la gráfica.
     * 
     * @param elemento el elemento a agregar.
     * @throws IllegalArgumentException si el elemento es <code>null</code> o ya
     *                                  había sido agregado a la gráfica.
     */
    @Override
    public void agrega(T elemento) {
        if (elemento == null || contiene(elemento)) {
            throw new IllegalArgumentException();
        } else {
            Vertice nuevo = new Vertice(elemento);
            vertices.agrega(nuevo);
        }
        // Aquí va su código.
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
        // Aquí va su código.
        if (!contiene(a) || !contiene(b)) {
            throw new NoSuchElementException("Los vertices que se intentan conectar no se encuentran en la gráfica");
        }
        Vertice x = (Vertice) vertice(a);
        Vertice y = (Vertice) vertice(b);
        if (x == y) {
            throw new IllegalArgumentException("Los vertices son iguales");
        }
        if (sonVecinos(a, b)) {
            throw new IllegalArgumentException("Los vértices ya son vecinos");
        }
        x.vecinos.agrega(new Vecino(y, 1));
        y.vecinos.agrega(new Vecino(x, 1));
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
            throw new IllegalArgumentException("El peso de la arsita no puede ser negativo");
        }
        if (!contiene(a) || !contiene(b)) {
            throw new NoSuchElementException("Los vertices que se intentan conectar no se encuentran en la gráfica");
        }
        Vertice x = (Vertice) vertice(a);
        Vertice y = (Vertice) vertice(b);
        if (x == y) {
            throw new IllegalArgumentException("Los vertices son iguales");
        }
        if (sonVecinos(a, b)) {
            throw new IllegalArgumentException("Los vértices ya son vecinos");
        }
        x.vecinos.agrega(new Vecino(y, peso));
        y.vecinos.agrega(new Vecino(x, peso));
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
        // Aquí va su código.
        if (!contiene(a) || !contiene(b)) {
            throw new NoSuchElementException("Alguno de los vértices no está en la gráfica");
        }
        if (!sonVecinos(a, b)) {
            throw new IllegalArgumentException("Los vertices ya están desconectados");
        }
        Vertice x = (Vertice) vertice(a);
        Vertice y = (Vertice) vertice(b);
        for (Vecino v : x.vecinos) {
            if (v.vecino == y) {
                x.vecinos.elimina(v);
            }
        }
        for (Vecino v : y.vecinos) {
            if (v.vecino == x) {
                y.vecinos.elimina(v);
            }
        }
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
        for (Vertice v : vertices) {
            if (v.elemento.equals(elemento)) {
                return true;
            }
        }
        return false;
        // Aquí va su código.
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
        // Aquí va su código.
        if (!contiene(elemento)) {
            throw new NoSuchElementException("El vértice no está en la gráfica");
        } else {
            Vertice x = (Vertice) vertice(elemento);
            for (Vecino v : x.vecinos) {
                for (Vecino vertice : v.vecino.vecinos) {
                    if (vertice.vecino == x) {
                        v.vecino.vecinos.elimina(vertice);
                        aristas--;
                    }
                }
            }
            vertices.elimina(x);
        }
    }

    /**
     * Nos dice si dos elementos de la gráfica están conectados. Los elementos deben
     * estar en la gráfica.
     * 
     * @param a el primer elemento.
     * @param b el segundo elemento.
     * @return <code>true</code> si a y b son vecinos, <code>false</code> en otro
     *         caso.onces la distancia de v al origen es mayor que la distancia de u
     * @throws NoSuchElementException si a o b no son elementos de la gráfica.
     */
    public boolean sonVecinos(T a, T b) {
        // Aquí va su código.
        if (!contiene(a) || !contiene(b)) {
            throw new NoSuchElementException("Alguno de los elementos no está en la gráfica");
        } else {
            Vertice x = (Vertice) vertice(a);
            Vertice y = (Vertice) vertice(b);
            boolean xContieney = false;
            boolean yContienex = false;
            for (Vecino v : x.vecinos) {
                if (v.vecino == y) {
                    xContieney = true;
                }
            }
            for (Vecino v : y.vecinos) {
                if (v.vecino == x) {
                    yContienex = true;
                }
            }
            return xContieney && yContienex;
        }
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
            throw new NoSuchElementException("Alguno de los vértices no está en la gráfica");
        }
        if (!sonVecinos(a, b)) {
            throw new IllegalArgumentException("Los vertices no están conectados");
        }
        double r = 0;
        Vertice x = (Vertice) vertice(a);
        Vertice y = (Vertice) vertice(b);
        for (Vecino v : x.vecinos) {
            if (v.vecino == y) {
                r = v.peso;
            }
        }
        return r;
        // Aquí va su código.
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
        if (!contiene(a) || !contiene(b)) {
            throw new NoSuchElementException("Vertices no están en gráfica");
        }
        if (!sonVecinos(a, b)) {
            throw new IllegalArgumentException("Vertices no conectados");
        }
        if (peso < 0) {
            throw new IllegalArgumentException("Peso inválido, es negativo");
        }
        Vertice x = (Vertice) vertice(a);
        Vertice y = (Vertice) vertice(b);
        for (Vecino vertice : x.vecinos) {
            if (vertice.vecino == y) {
                vertice.peso = peso;
            }
        }
        for (Vecino vertice : y.vecinos) {
            if (vertice.vecino == x) {
                vertice.peso = peso;
            }
        }
    }

    /**
     * Regresa el vértice correspondiente el elemento recibido.
     * 
     * @param elemento el elemento del que queremos el vértice.
     * @throws NoSuchElementException si elemento no es elemento de la gráfica.
     * @return el vértice correspondiente el elemento recibido.
     */
    public VerticeGrafica<T> vertice(T elemento) {
        for (Vertice v : vertices) {
            if (v.elemento.equals(elemento)) {
                return v;
            }
        }
        throw new NoSuchElementException("El elemento no está en la gráfica");
    }

    /**
     * Define el color del vértice recibido.
     * 
     * @param vertice el vértice al que queremos definirle el color.
     * @param color   el nuevo color del vértice.
     * @throws IllegalArgumentException si el vértice no es válido.
     */
    public void setColor(VerticeGrafica<T> vertice, Color color) {
        // Aquí va su código.
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
        // Aquí va su código.
        if (vertices.esVacia()) {
            return true;
        }
        Vertice w = vertices.getPrimero();
        for (Vertice v : vertices) {
            v.color = Color.ROJO;
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
        for (Vertice v : vertices) {
            accion.actua(v);
        }
    }

    private void descolorea() {
        for (Vertice vertice : vertices) {
            vertice.color = Color.NINGUNO;
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
        // Aquí va su código.
        if (!contiene(elemento)) {
            throw new NoSuchElementException("El vértice no está en la gráfica");
        } else {
            Vertice w = (Vertice) vertice(elemento);
            for (Vertice v : vertices) {
                v.color = Color.ROJO;
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
        // Aquí va su código.
        return vertices.esVacia();
    }

    /**
     * Limpia la gráfica de vértices y aristas, dejándola vacía.
     */
    @Override
    public void limpia() {
        // Aquí va su código.
        while (!vertices.esVacia()) {
            vertices.eliminaUltimo();
        }
        aristas = 0;
    }

    /**
     * Regresa una representación en cadena de la gráfica.
     * 
     * @return una representación en cadena de la gráfica.
     */
    @Override
    public String toString() {
        // Aquí va su código.
        return obtenVertices() + ", " + obtenAristas();
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
        if (vertices.getLongitud() == 0 && grafica.vertices.getLongitud() == 0) {
            return true;
        }
        if (vertices.getLongitud() != grafica.vertices.getLongitud()) {
            return false;
        }
        if (aristas != grafica.aristas) {
            return false;
        }
        for (Vertice v1 : vertices) {
            if (grafica.contiene(v1.elemento)) {
                Vertice v2 = (Vertice) grafica.vertice(v1.elemento);
                if (v2.vecinos.getLongitud() == v1.vecinos.getLongitud()) {
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
        } else {
            Vertice s = (Vertice) vertice(origen);
            Vertice t = (Vertice) vertice(destino);
            Lista<VerticeGrafica<T>> regreso = new Lista<>();
            for (Vertice v : vertices) {
                v.distancia = Double.POSITIVE_INFINITY;
            }
            s.distancia = 0;
            MonticuloArreglo<Vertice> monti = new MonticuloArreglo<>(vertices);
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
}
