package mx.unam.ciencias.edd.proyecto3;

import mx.unam.ciencias.edd.Lista;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Clase que hace el código en svg de una gráfica de barras de las palabras más comunes en un archivo.
 */
public class BarChartSVG {

    private String chart;
    private int elementos;
    private Lista<Palabra> ordenada;
    private final int altura = 500;
    private String[] colores = { "#a5e10f", "#e6286a", "#e057ec", "#43b497", "#f5ef36", "#f6a03e" };

    /**
     * Crea el código svg de una gráfica de barras a partir de un Contador
     * 
     * @param c el Contador
     */
    public BarChartSVG(Contador c) {
        ordenada = c.top5();
        elementos = c.getElementos();
        iniciaSVG();
        dibujaBarras();
        finalizaSVG();
    }

    /**
     * Auxiliar, inicia el código
     */
    private void iniciaSVG() {
        chart = "<svg class=\"barGraph\" viewBox=\"0 0 800 700\" xmlns=\"http://www.w3.org/2000/svg\">" + "\n"
                + " <g class=\"grid y-grid\">" + "\n"
                + "  <line x1=\"97\" x2=\"97\" y1=\"100\" y2=\"500\" style=\"stroke:red;stroke-width:6\"/>" + "\n"
                + " </g>" + "\n" + "<g class=\"grid x-grid\">" + "\n"
                + "  <line x1=\"100\" x2=\"700\" y1=\"503\" y2=\"503\" style=\"stroke:red;stroke-width:6\"/>" + "\n"
                + " </g>" + "\n" + " <g class=\"textos\">" + "\n"
                + "  <text text-anchor=\"end\" x=\"97\" y=\"100\" font-family=\"Verdana\" font-size=\"20\">100%</text>"
                + "\n" + " </g>" + "\n" + " <g class=\"textos\">" + "\n"
                + "  <text text-anchor=\"end\" x=\"97\" y=\"200\" font-family=\"Verdana\" font-size=\"20\">75%</text>"
                + "\n" + " </g>" + "\n" + " <g class=\"textos\">" + "\n"
                + "  <text text-anchor=\"end\" x=\"97\" y=\"300\" font-family=\"Verdana\" font-size=\"20\">50%</text>"
                + "\n" + " </g>" + "\n" + " <g class=\"textos\">" + "\n"
                + "  <text text-anchor=\"end\" x=\"97\" y=\"400\" font-family=\"Verdana\" font-size=\"20\">25%</text>"
                + "\n" + " </g>" + "\n" + " <g class=\"textos\">" + "\n"
                + "  <text text-anchor=\"end\" x=\"97\" y=\"500\" font-family=\"Verdana\" font-size=\"20\">0%</text>"
                + "\n" + " </g>";
    }

    /**
     * Auxiliar, finaliza el código
     */
    private void finalizaSVG() {
        chart = chart + "\n" + "</svg>";
    }

    /**
     * Hace el proceso para hacer el código de las barras.
     */
    private void dibujaBarras() {
        int x = 100;
        int i = 0;
        double ultimoP = 100;
        for (Palabra palabra : ordenada) {
            double height = calculaAltura(palabra);
            double porcentaje = calculaPorcentaje(palabra);
            double y = altura - height;
            chart = chart + "\n" + " <g class=\"bar\">" + "\n" + "  <rect class=\"barra\" width=\"100\" height=\"" + height + "\" x=\""
                    + x + "\" y=\"" + y + "\" fill=\"" + colores[i] + "\"/>";
            chart = chart + "\n" + "  <text x=\"" + (x + 50) + "\" y=\"" + (y + (height / 2))
                    + "\" text-anchor=\"middle\" font-size=\"15\">" + porcentaje + "%" + "</text>" + "\n" + " </g>";
            chart = chart + "\n" + " <g class=\"textos\">" + "\n" + "  <text transform=\"translate(" + (x + 50)
                    + " 600) rotate(-90)\" text-anchor=\"middle\" font-size=\"20\">" + palabra.toString() + "</text>"
                    + "\n" + " </g>";
            x += 100;
            i++;
            ultimoP -= porcentaje;
        }
        double p = new BigDecimal(ultimoP).setScale(2, RoundingMode.HALF_UP).doubleValue();
        int ultimoH = ((elementos - palabrasEnLista()) * 400) / elementos;
        int ultimoY = altura - ultimoH;
        chart = chart + "\n" + " <g class=\"bar\">" + "\n" + "  <rect width=\"100\" height=\"" + ultimoH + "\" x=\"" + x
                + "\" y=\"" + ultimoY + "\" fill=\"" + colores[5] + "\"/>";
        chart = chart + "\n" + "  <text x=\"" + (x + 50) + "\" y=\"" + (ultimoY + (ultimoH / 2))
                + "\" text-anchor=\"middle\" font-size=\"15\">" + p + "%" + "</text>" + "\n" + " </g>";
        chart = chart + "\n" + " <g class=\"textos\">" + "\n"
                + "  <text transform=\"translate(650 600) rotate(-90)\" text-anchor=\"middle\" font-size=\"20\">Otros</text>"
                + "\n" + " </g>";
    }

    /**
     * Nos regresa el total de palabras
     * @return Total de palabras en el archivo
     */
    private int palabrasEnLista() {
        int value = 0;
        for (Palabra palabra : ordenada) {
            value += palabra.valor();
        }
        return value;
    }

    /**
     * Auxiliar, calcula la altura de la barra
     * 
     * @param word Palabra que corresponde a la barra
     * @return La altura correspondiente
     */
    private double calculaAltura(Palabra word) {
        double alto = (word.valor() * 400) / (double) elementos;
        return new BigDecimal(alto).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }

    /**
     * Auxiliar, calcula el porcentaje de la palabra
     * 
     * @param word la Palabra
     * @return el porcentaje de la palabra
     */
    private double calculaPorcentaje(Palabra word) {
        double p = (word.valor() * 100) / (double) elementos;
        return new BigDecimal(p).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }

    /**
     * Regresa el código svg de la gráfica
     */
    public String toString() {
        return chart;
    }


}