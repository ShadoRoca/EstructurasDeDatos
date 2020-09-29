package mx.unam.ciencias.edd.proyecto2;

import mx.unam.ciencias.edd.Lista;

/**
 * Clase para hacer codigo svg de Listas
 */
public class ListaSVG extends SVGFunciones {

    private Lista<Integer> lista = new Lista<>();
    private String svg;
    private int elem = 0;

    public ListaSVG() {
    }

    /**
     * Agrega elementos a la lista
     * @param elementos Lista de los elementos
     */
    public void agregaElementos(Lista<Integer> elementos) {
        elem = elementos.getLongitud();
        while(!elementos.esVacia()) {
            lista.agregaFinal(elementos.eliminaPrimero());
        }
    }

    /**
     * inicia el svg
     */
    private void iniciaSVG() {
        svg = "<?xml version='1.0' encoding='UTF-8' ?>" + "\n";
    }

    /**
     * determina el tamaño svg
     */
    private void setSize() {
        int ancho = (elem * 100) + ((elem - 1) * 40);
        svg = svg + "<svg width='" + ancho + "'" + " height='300'>" + "\n" + "<g>" + "\n";
    }

    /**
     * hace el codigo svg
     */
    private void estructuraSVG() {
        int x = 0;
        int y = 100;
        for (int i = 0; i < elem; i++) {
            int j = lista.get(i);
            svg = svg + rectuangulo(x, y, 100, 100);
            if (!(i == elem - 1)) {
                svg = svg + flechaDoble((x + 100), 165);
            }
            svg = svg + escribeNum(x + 38, 157, j, "black");
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
     * Regresa el codigo svg de la lista
     * @return el codigo svg
     */
    public String SVG() {
        iniciaSVG();
        setSize();
        estructuraSVG();
        terminaSVG();
        return svg;
    }
}