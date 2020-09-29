package mx.unam.ciencias.edd.proyecto3;

import mx.unam.ciencias.edd.VerticeArbolBinario;

/**
 * Esta clase nos ayuda a tener varios métodos que tienen en común algunas estructuras
 */
public class SVGFunciones {

    /**
     * Hace el código de un rectángulo en SVG
     * @param x coordenada x
     * @param y coordenada y
     * @param w el ancho
     * @param h el alto
     * @return el codigo
     */
    public String rectuangulo(int x, int y, int w, int h) {
        String rect = "<rect x=\"" + x + "\" " + "y=\"" + y + "\" " + "width=\"" + w + "\" " + "height=\"" + h + "\" "
                + "stroke=\"#000000\" " + "stroke-width=\"5\"" + " fill=\"#ffffff\"" + "/>";
        return rect + "\n";
    }

    /**
     * Hace el codigo de un circulo en SVG
     * @param x coordena
     * @param y coordena
     * @param r radio
     * @param color color del circulo
     * @return el codigo
     */
    public String circulo(int x, int y, int r, String color) {
        return " <circle cx='" + x + "' " + "cy='" + y + "' " + "r='" + r + "' "
                + "stroke='black' stroke-width='3' fill='" + color + "' />" + "\n";
    }

    /**
     * Hace una linea en codigo de SVG
     * @param x1 coordenada del primer punto
     * @param y1 coordenada del primer punto
     * @param x2 coordenada del segundo punto
     * @param y2 coordenada del segundo punto
     * @return el codigo
     */
    public String pendiente(int x1, int y1, int x2, int y2) {
        return "<line x1='" + x1 + "' " + "y1='" + y1 + "' " + "x2='" + x2 + "' " + "y2='" + y2 + "' "
                + "stroke='blue' stroke-width='5' />" + "\n";
    }

    /**
     * Escribe una palabra en svg
     * @param x coordenada
     * @param y coordenada
     * @param t la palabra
     * @param color color de la letra
     * @return el codigo
     */
    public String escribePalabra(int x, int y, String t, String color) {
        String texto = "<text class=\"textos\" x=\"" + x + "\" " + "y=\"" + y + "\" " + "text-anchor=\"middle\" "
                + "font-family=\"fantasy\" " + "font-size=\"25\" " + "fill=\"" + color + "\" " + "stroke=\"#000000\" "
                + ">" + t + "</text>";
        return texto + "\n";
    }

    /**
     * Escribe texto es svg, util para los avl
     * @param x coordenada
     * @param y coordenada
     * @param t el texto
     * @param color color del texto
     * @return el codigo
     */
    public String escribeText(int x, int y, String t, String color) {
        String texto = "<text x=\"" + x + "\" " + "y=\"" + y + "\" " + "text-anchor=\"middle\" "
                + "font-family=\"serif\" " + "font-size=\"15\" " + "fill=\"" + color + "\" " + "stroke=\"#000000\" "
                + ">" + t + "</text>";
        return texto + "\n";
    }

    /**
     * Hace una flecha apuntando a la derecha
     * @param x coordenada
     * @param y coordenada
     * @return el codigo
     */
    public String flechaDerecha(int x, int y) {
        String arrow = "<text x=\"" + x + "\" " + "y=\"" + y + "\" " + "font-family=\"serif\" " + "font-size=\"50\" "
                + "fill=\"#000000\" " + "stroke=\"#000000\" " + ">→</text>";
        return arrow + "\n";
    }

    /**
     * Hace una flecha apuntando a la izquierda
     * @param x coordenada
     * @param y coordenada
     * @return codigo
     */
    public String flechaDoble(int x, int y) {
        String arrow = "<text x=\"" + x + "\" " + "y=\"" + y + "\" " + "font-family=\"serif\" " + "font-size=\"50\" "
                + "fill=\"#000000\" " + "stroke=\"#000000\" " + ">↔</text>";
        return arrow + "\n";
    }

    /**
     * Dibuja los vertices de un arbol
     * @param v el vertice raiz
     * @param xCoordenada coordenada de la raiz
     * @param yCoordenada coordenada de la raiz
     * @param proporcion proporcion con la que se abren las ramas
     * @return el codigo
     */
    public String dibujaVertices(VerticeArbolBinario<Palabra> v, int xCoordenada, int yCoordenada, int proporcion) {
        if (v == null) {
            return "";
        }
        String c = circulo(xCoordenada, yCoordenada, 50, "white")
                + escribePalabra(xCoordenada, yCoordenada, v.get().toString(), "black");
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
     * Hace las ramas de un arbol binario
     * @param v el vertice raiz
     * @param xCoordenada coordenada raiz
     * @param yCoordenada coordenada raiz
     * @param proporcion porporcion con la que abren
     * @return el codigo
     */
    public String dibujaLineas(VerticeArbolBinario<Palabra> v, int xCoordenada, int yCoordenada, int proporcion) {
        if (v == null) {
            return "";
        }
        String l = "";
        if (v.hayIzquierdo() && v.hayDerecho()) {
            l += pendiente(xCoordenada, yCoordenada, xCoordenada - (proporcion / 2), yCoordenada + 200);
            l += pendiente(xCoordenada, yCoordenada, xCoordenada + (proporcion / 2), yCoordenada + 200);
            l += dibujaLineas(v.izquierdo(), xCoordenada - (proporcion / 2), yCoordenada + 200, proporcion / 2);
            l += dibujaLineas(v.derecho(), xCoordenada + (proporcion / 2), yCoordenada + 200, proporcion / 2);
        }
        if (v.hayIzquierdo() && !v.hayDerecho()) {
            l += pendiente(xCoordenada, yCoordenada, xCoordenada - (proporcion / 2), yCoordenada + 200);
            l += dibujaLineas(v.izquierdo(), xCoordenada - (proporcion / 2), yCoordenada + 200, proporcion / 2);
        }
        if (v.hayDerecho() && !v.hayIzquierdo()) {
            l += pendiente(xCoordenada, yCoordenada, xCoordenada + (proporcion / 2), yCoordenada + 200);
            l += dibujaLineas(v.derecho(), xCoordenada + (proporcion / 2), yCoordenada + 200, proporcion / 2);
        }
        return l;
    }
}