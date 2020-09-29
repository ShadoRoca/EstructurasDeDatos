package mx.unam.ciencias.edd.proyecto2;

import mx.unam.ciencias.edd.ArbolAVL;
import mx.unam.ciencias.edd.Lista;
import mx.unam.ciencias.edd.VerticeArbolBinario;
/**
 * Clase que representa un arbol AVL en codigo de SVG
 */
public class AvlSVG extends SVGFunciones {
    private ArbolAVL<Integer> arbol = new ArbolAVL<>();
    private int altura;
    private String svg;
    private int alto;
    private int ancho;

    /**
     * Constructor, crea el árbol AVL
     * @param elementos La lista de los elementos
     */
    public AvlSVG(Lista<Integer> elementos) {
        while (!elementos.esVacia()) {
            int i = elementos.eliminaPrimero();
            arbol.agrega(i);
        }
        altura = arbol.altura();
        ancho = 100 * ((int) Math.pow(2, altura)) + (100 * ((int) Math.pow(2, altura) - 1)) + 300;
        alto = 100 * altura + 100 * (altura - 1) + 300;
    }

    /**
     * Auxiliar que incia el SVG
     */
    private void iniciaSVG() {
        svg = "<?xml version='1.0' encoding='UTF-8' ?>" + "\n";
    }

    /**
     * Auxiliar que determina el tamaño del canvas
     */
    private void setSize() {
        svg = svg + "<svg width='" + ancho + "'" + " height='" + alto + "'>" + "\n" + "<g>" + "\n";
    }

    /**
     * Auxiliar que termina el SVG
     */
    private void terminaSVG() {
        svg = svg + "\n" + "</g>" + "\n" + "</svg>";
    }

    /**
     * Metodo que regresa el codigo para hacer los vértices de un árbol AVL
     * @param v Vertice desde donde se empieza a hacer el código
     * @param xCoordenada La coordenada x del vertice
     * @param yCoordenada La cordenada y del vertice 
     * @param proporcion Proporcion con la que se van abriendo los vertices
     */
    @Override
    public String dibujaVertices(VerticeArbolBinario<Integer> v, int xCoordenada, int yCoordenada, int proporcion) {
        if (v == null) {
            return "";
        }
        String c = "";
        c += circulo(xCoordenada, yCoordenada, 50, "white") + escribeNum(xCoordenada, yCoordenada, v.get(), "black");
        if (v.hayIzquierdo() && v.hayDerecho()) {
            int balance = v.izquierdo().altura() - v.derecho().altura();
            int altura = v.altura();
            String texto = "[" + altura + "/" + balance + "]";
            c += escribeText(xCoordenada + 75, yCoordenada, texto, "black");
            c += dibujaVertices(v.izquierdo(), xCoordenada - (proporcion / 2), yCoordenada + 200, proporcion / 2);
            c += dibujaVertices(v.derecho(), xCoordenada + (proporcion / 2), yCoordenada + 200, proporcion / 2);
        } else if (v.hayIzquierdo() && !v.hayDerecho()) {
            int balance = v.izquierdo().altura() - (-1);
            int altura = v.altura();
            String texto = "[" + altura + "/" + balance + "]";
            c += escribeText(xCoordenada + 75, yCoordenada, texto, "black");
            c += dibujaVertices(v.izquierdo(), xCoordenada - (proporcion / 2), yCoordenada + 200, proporcion / 2);
        } else if (v.hayDerecho() && !v.hayIzquierdo()) {
            int balance = -1 - v.derecho().altura();
            int altura = v.altura();
            String texto = "[" + altura + "/" + balance + "]";
            c += escribeText(xCoordenada + 75, yCoordenada, texto, "black");
            c += dibujaVertices(v.derecho(), xCoordenada + (proporcion / 2), yCoordenada + 200, proporcion / 2);
        } else {
            int balance = 0;
            altura = 0;
            String texto = "[" + altura + "/" + balance + "]";
            c += escribeText(xCoordenada + 75, yCoordenada, texto, "black");
        }
        return c;
    }

    /**
     * Hace el codigo svg del arbol AVL
     * @return El codigo svg
     */
    public String SVG() {
        iniciaSVG();
        setSize();
        int x = ancho / 2;
        svg = svg + dibujaLineas(arbol.raiz(), x, 100, x);
        svg = svg + dibujaVertices(arbol.raiz(), x, 100, x);
        terminaSVG();
        return svg;
    }
}