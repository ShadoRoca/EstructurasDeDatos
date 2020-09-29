package mx.unam.ciencias.edd.proyecto2;

import mx.unam.ciencias.edd.Lista;
import mx.unam.ciencias.edd.ArbolBinarioCompleto;

public class MonticuloSVG extends SVGFunciones {

    private ArbolBinarioCompleto<Integer> arbol = new ArbolBinarioCompleto<>();
    private Lista<Integer> ordenada;
    private int altura;
    private String svg;
    private int alto;
    private int ancho;

    /**
     * Crea una lista ordenada con los elementos del arbol
     * @param elementos lista con los elementos
     */
    public MonticuloSVG(Lista<Integer> elementos) {
        ordenada = Lista.mergeSort(elementos);
        while (!ordenada.esVacia()) {
            arbol.agrega(ordenada.eliminaPrimero());
        }
        altura = arbol.altura();
        ancho = 100 * ((int) Math.pow(2, altura)) + (100 * ((int) Math.pow(2, altura) - 1)) + 300;
        alto = 100 * altura + 100 * (altura - 1) + 300;
    }

    public void iniciaSVG() {
        svg = "<?xml version='1.0' encoding='UTF-8' ?>" + "\n";
    }

    public void setSize() {
        svg = svg + "<svg width='" + ancho + "'" + " height='" + alto + "'>" + "\n" + "<g>" + "\n";
    }

    private void terminaSVG() {
        svg = svg + "\n" + "</g>" + "\n" + "</svg>";
    }

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