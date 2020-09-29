package mx.unam.ciencias.edd.proyecto3;

import mx.unam.ciencias.edd.ArbolRojinegro;
import mx.unam.ciencias.edd.Lista;
import mx.unam.ciencias.edd.VerticeArbolBinario;
import mx.unam.ciencias.edd.Color;

/**
 * Clase para hacer el codigo svg de un arbol rojonegro
 */
public class RojinegroSVG extends SVGFunciones {

    private ArbolRojinegro<Palabra> arbol = new ArbolRojinegro<>();
    private int altura;
    private String svg;
    private int alto;
    private int ancho;

    /**
     * Crea el arbol rojinegro
     * @param elementos
     */
    public RojinegroSVG(Lista<Palabra> elementos) {
        while (!elementos.esVacia()) {
            Palabra i = elementos.eliminaPrimero();
            arbol.agrega(i);
        }
        altura = arbol.altura();
        ancho = 100 * ((int) Math.pow(2, altura)) + (100 * ((int) Math.pow(2, altura) - 1)) + 300;
        alto = 100 * altura + 100 * (altura - 1) + 300;
    }

    /**
     * auxiliar, determina el tamaño
     */
    private void setSize() {
        svg = "<svg class=\"rj\" xmlns=\"http://www.w3.org/2000/svg\" viewBox=\"0 0 " + ancho + " " + alto + "\">" + "\n" + "<g>" + "\n";
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
    public String dibujaVertices(VerticeArbolBinario<Palabra> v, int xCoordenada, int yCoordenada, int proporcion) {
        if (v == null) {
            return "";
        }
        String c = "";
        if (arbol.getColor(v) == Color.ROJO) {
            c += circulo(xCoordenada, yCoordenada, 50, "red") + escribePalabra(xCoordenada, yCoordenada, v.get().toString(), "black");
        } else {
            c += circulo(xCoordenada, yCoordenada, 50, "black")
                    + escribePalabra(xCoordenada, yCoordenada, v.get().toString(), "white");
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
        setSize();
        int x = ancho / 2;
        svg = svg + dibujaLineas(arbol.raiz(), x, 100, x);
        svg = svg + dibujaVertices(arbol.raiz(), x, 100, x);
        terminaSVG();
        return svg;
    }

    public static void main(String[] args) {
        String archivo = args[0];
        Contador uno = new Contador(archivo);
        RojinegroSVG rj = new RojinegroSVG(uno.top15());
        System.out.println(rj.SVG());
    }
}