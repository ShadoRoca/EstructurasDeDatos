package mx.unam.ciencias.edd.proyecto2;

import mx.unam.ciencias.edd.ArbolRojinegro;
import mx.unam.ciencias.edd.Lista;
import mx.unam.ciencias.edd.VerticeArbolBinario;
import mx.unam.ciencias.edd.Color;

/**
 * Clase para hacer el codigo svg de un arbol rojonegro
 */
public class RojinegroSVG extends SVGFunciones {

    private ArbolRojinegro<Integer> arbol = new ArbolRojinegro<>();
    private int altura;
    private String svg;
    private int alto;
    private int ancho;

    /**
     * Crea el arbol rojinegro
     * @param elementos
     */
    public RojinegroSVG(Lista<Integer> elementos) {
        while (!elementos.esVacia()) {
            int i = elementos.eliminaPrimero();
            arbol.agrega(i);
        }
        altura = arbol.altura();
        ancho = 100 * ((int) Math.pow(2, altura)) + (100 * ((int) Math.pow(2, altura) - 1)) + 300;
        alto = 100 * altura + 100 * (altura - 1) + 300;
    }

    /**
     * auxiliar que incia el svg
     */
    private void iniciaSVG() {
        svg = "<?xml version='1.0' encoding='UTF-8' ?>" + "\n";
    }

    /**
     * auxiliar, determina el tamaño
     */
    private void setSize() {
        svg = svg + "<svg width='" + ancho + "'" + " height='" + alto + "'>" + "\n" + "<g>" + "\n";
    }

    /**
     * auxiliar, termina el svg
     */
    private void terminaSVG() {
        svg = svg + "\n" + "</g>" + "\n" + "</svg>";
    }

    /**
     * Metodo que regresa el codigo para hacer los vértices de un árbol Rojinegro
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
        if (arbol.getColor(v) == Color.ROJO) {
            c += circulo(xCoordenada, yCoordenada, 50, "red") + escribeNum(xCoordenada, yCoordenada, v.get(), "white");
        } else {
            c += circulo(xCoordenada, yCoordenada, 50, "black")
                    + escribeNum(xCoordenada, yCoordenada, v.get(), "white");
        }
        if (v.hayIzquierdo() && v.hayDerecho()) {
            c += dibujaVertices(v.izquierdo(), xCoordenada - (proporcion / 2), yCoordenada + 200, proporcion / 2);
            c += dibujaVertices(v.derecho(), xCoordenada + (proporcion / 2), yCoordenada + 200, proporcion / 2);
        } else if (v.hayIzquierdo() && !v.hayDerecho()) {
            c += dibujaVertices(v.izquierdo(), xCoordenada - (proporcion / 2), yCoordenada + 200, proporcion / 2);
        } else if (v.hayDerecho() && !v.hayIzquierdo()) {
            c += dibujaVertices(v.derecho(), xCoordenada + (proporcion / 2), yCoordenada + 200, proporcion / 2);
        }
        return c;
    }

    /**
     * Regresa el codigo svg
     * @return el svg
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