package mx.unam.ciencias.edd.proyecto2;

import mx.unam.ciencias.edd.Lista;

import java.util.NoSuchElementException; 

import mx.unam.ciencias.edd.Cola;

/**
 * Clase para hacer colas en svg
 */
public class ColaSVG extends SVGFunciones {

    private Cola<Integer> cola = new Cola<>();
    private String svg;
    private int elem = 0;

    public ColaSVG(){}

    /**
     * Agrega los elementos a la cola
     * @param elementos lista de los elementos de la cola
     */
    public void meteElementos(Lista<Integer> elementos) {
        elem = elementos.getLongitud();
        while (!elementos.esVacia()) {
            cola.mete(elementos.eliminaPrimero());
        }
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
        int ancho = (elem * 100) + ((elem - 1) * 40);
        svg = svg + "<svg width='" + ancho + "'" + " height='300'>" + "\n" + "<g>" + "\n";
    }

    /**
     * hace el codigo svg de la cola
     */
    private void estructuraSVG() {
        int x = 0;
        int y = 100;
        while (!cola.esVacia()) {
            int i = cola.saca();
            svg = svg + rectuangulo(x, y, 100, 100);
            if (haySiguiente()) {
                svg = svg + flechaDerecha((x + 100), 165);
            }
            svg = svg + escribeNum(x + 38, 157, i, "black");
            x += 140;
        }
    }

    /**
     * termina el svg
     */
    private void terminaSVG() {
        svg = svg + "\n" + "</g>" + "\n" + "</svg>";
    }

    /**
     * Devuelve el codigo svg de la cola
     * @return codigo de la cola
     */
    public String SVG() {
        iniciaSVG();
        setSize();
        estructuraSVG();
        terminaSVG();
        return svg;
    }

    /**
     * auxiliar, determina si hay un siguiente en la cola
     * @return Si hay siguiente
     */
    private boolean haySiguiente() {
        try {
            cola.mira();
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }
}