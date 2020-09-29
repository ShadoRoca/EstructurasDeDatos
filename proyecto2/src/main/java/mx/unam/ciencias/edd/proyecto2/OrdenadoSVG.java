package mx.unam.ciencias.edd.proyecto2;

import mx.unam.ciencias.edd.Lista;
import mx.unam.ciencias.edd.ArbolBinarioOrdenado;


/**
 * Clase para codigo svg de un arbol ordenado
 */
public class OrdenadoSVG extends SVGFunciones {

    private ArbolBinarioOrdenado<Integer> arbol = new ArbolBinarioOrdenado<>();
    private int altura;
    private String svg;
    private int alto;
    private int ancho;

    /**
     * Crea el arbol ordenado 
     * @param elementos los elementos del arbol
     */
    public OrdenadoSVG(Lista<Integer> elementos) {
        while (!elementos.esVacia()) {
            int i = elementos.eliminaPrimero();
            arbol.agrega(i);
        }
        altura = arbol.altura();
        ancho = 100 * ((int) Math.pow(2, altura)) + (100 * ((int) Math.pow(2, altura) - 1)) + 400;
        alto = 100 * altura + 100 * (altura - 1) + 300;
    }

    /**
     * Inicia el svg
     */
    private void iniciaSVG() {
        svg = "<?xml version='1.0' encoding='UTF-8' ?>" + "\n";
    }

    /**
     * Determina el tamaño
     */
    private void setSize() {
        svg = svg + "<svg width='" + ancho + "'" + " height='" + alto + "'>" + "\n" + "<g>" + "\n";
    }

    /**
     * termina el svg
     */
    private void terminaSVG() {
        svg = svg + "\n" + "</g>" + "\n" + "</svg>";
    }

    /**
     * Regresa el codigo svg
     * @return cogido svg
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