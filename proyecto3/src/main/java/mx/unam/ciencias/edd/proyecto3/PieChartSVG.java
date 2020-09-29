package mx.unam.ciencias.edd.proyecto3;

import java.math.BigDecimal;
import java.math.RoundingMode;

import mx.unam.ciencias.edd.Lista;

/**
 * Clase que genera el código svg de una gráfica de pastel de las palabras más comunes de una archivo
 */
public class PieChartSVG extends SVGFunciones {

    private String chart;
    private int elementos;
    private Lista<Palabra> ordenada;
    private String[] colores = { "#a5e10f", "#e6286a", "#e057ec", "#43b497", "#f5ef36", "#f6a03e" };

    /**
     * Crea el codigo de la gráfica
     * @param c
     */
    public PieChartSVG(Contador c) {
        elementos = c.getElementos();
        ordenada = c.top5();
        iniciaSVG();
        dibujaRebanadas();
        finalizaSVG();
    }

    /**
     * Auxiliar
     */
    private void iniciaSVG() {
        chart = "<svg class=\"pie\" xmlns=\"http://www.w3.org/2000/svg\" viewBox=\"-250 -250 800 500\">"
                + "\n"
                + " <circle class=\"Otroc\" r=\"250\" cx=\"0\" cy=\"0\" fill=\"crimson\" stroke=\"black\" stroke-width=\"2\"/>";
    }

    /**
     * Auxiliar
     */
    private void finalizaSVG() {
        chart = chart + "\n" + "</svg>";
    }

    /**
     * Auxiliar
     */
    private void dibujaRebanadas() {
        double sumaPorcentaje = 0;
        int i = 0;
        int tpx = 390;
        int tpy = -200;
        double total = 100;
        for (Palabra palabra : ordenada) {
            total -= porcentajeTexto(palabra);
            double inicioX = xCoordinate(sumaPorcentaje, 250);
            double inicioY = yCoordinate(sumaPorcentaje, 250);
            sumaPorcentaje += calculaPorcentaje(palabra);
            double finalX = xCoordinate(sumaPorcentaje, 250);
            double finalY = yCoordinate(sumaPorcentaje, 250);
            int arco = calculaPorcentaje(palabra) > .5 ? 1 : 0;
            chart = chart + "\n" + " <path class=\"rebanadas\" d=\"M " + inicioX + " " + inicioY + " A 250 250 0 "
                    + arco + " 1 " + finalX + " " + finalY + " L 0 0\" fill=\"" + colores[i] + "\"></path>";
            chart = chart + "\n" + " " + escribePalabra(tpx, tpy,
                    palabra + " - " + String.valueOf(porcentajeTexto(palabra)) + "%", colores[i]);
            tpy += 50;
            i++;
        }
        chart = chart + "\n" + " "
                + escribePalabra(tpx, tpy, "Otros" + " - " + String.valueOf(new BigDecimal(total).setScale(2, RoundingMode.HALF_UP).doubleValue()) + "%", "crimson");

    }

    /**
     * Regresa el código svg de la gráfica
     */
    public String toString() {
        return chart;
    }

    /**
     * Auxiliar
     */
    private double xCoordinate(double porcentaje, double radio) {
        double x = radio * Math.cos(2 * Math.PI * porcentaje);
        return x;
    }

    /**
     * Auxiliar
     */
    private double yCoordinate(double porcentaje, double radio) {
        double y = radio * Math.sin(2 * Math.PI * porcentaje);
        return y;
    }

    /**
     * Auxiliar
     */
    private double calculaPorcentaje(Palabra word) {
        double p = (word.valor() * 100) / (double) elementos / 100;
        return p;
    }

    /**
     * Auxiliar
     */
    private double porcentajeTexto(Palabra word) {
        double p = (word.valor() * 100) / (double) elementos;
        return new BigDecimal(p).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }
}