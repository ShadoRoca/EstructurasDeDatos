package mx.unam.ciencias.edd.proyecto2;

import mx.unam.ciencias.edd.ArbolBinarioCompleto;
import mx.unam.ciencias.edd.Lista;

/**
 * Clase para hacer codigo de arboles completos en svg
 */
public class CompletoSVG extends SVGFunciones {

    private ArbolBinarioCompleto<Integer> arbol = new ArbolBinarioCompleto<>();
    private int altura;
    private String svg;
    private int alto;
    private int ancho;

    /**
     * Constructor, hace el arbol completo
     * @param elementos los elementos del arbol
     */
    public CompletoSVG(Lista<Integer> elementos) {
        while (!elementos.esVacia()) {
            arbol.agrega(elementos.eliminaPrimero());
        }
        altura = arbol.altura();
        ancho = 100 * ((int) Math.pow(2, altura)) + (100 * ((int) Math.pow(2, altura) - 1)) + 300;
        alto = 100 * altura + 100 * (altura - 1) + 300;
    }

    /**
     * inicia el svg
     */
    private void iniciaSVG() {
        svg = "<?xml version='1.0' encoding='UTF-8' ?>" + "\n";
    }

    /**
     * determina el tamaño del svg
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
     * Regresa el codigo svg del arbol
     * @return el codigo 
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